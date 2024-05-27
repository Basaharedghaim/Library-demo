package com.managment.Librarydemo.RestController;

import com.managment.Librarydemo.FeignClient.PaymentService;
import com.managment.Librarydemo.services.ElasticService;
import com.managment.Librarydemo.services.Reserve;
import com.managment.Librarydemo.services.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import com.models.demo.models.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.managment.Librarydemo.services.CRUD;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bookStore")
@Slf4j
public class Rest {

    private final CRUD crud;
   private final PaymentService paymentService;
   private final ExcelService excelService;
   private final Reserve reserve;
   private final ElasticService elasticService;
    @Autowired
    public Rest(CRUD crud, PaymentService paymentService, ExcelService excelService, Reserve reserve, ElasticService elasticService){

        this.crud=crud;
        this.paymentService = paymentService;
        this.excelService=excelService;
        this.reserve = reserve;
        this.elasticService=elasticService;
    }
    @PostMapping("/addBook")
    @Operation(
            tags = {"Add New Book"}
    )
    public Book  AddBook(@RequestBody Book book){
        log.info("Adding NewBook");
        return crud.addNewBook(book);
    }
    @DeleteMapping("/deleteBook")
    @Operation(
            tags = {"Delete Book"}
    )
    public String deleteBook(@RequestParam int id){
        log.info("Deleting Book with id="+id);
        return crud.deleteBookBasedOnId(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getAll")
    @Operation(tags = {"retrieve All Books"})
    public List<Book> getAll(){
       log.info("Books retrieving ");
       return crud.showAll();
    }
    @PutMapping("/updateTitle/{id}")
    @Operation(
            tags = {"Update Book Title"}
    )
    public String updateTitle(@PathVariable("id") int id, @RequestParam String title){
        log.info("Title Updating");
        return crud.updateTitle(id,title);
    }
    @PostMapping("/buyBook")
    @Operation(tags = {"Add Book to the Buyer List"})
    public Response buyBook(@RequestBody Request request, @RequestParam Long bookId){

        ElasticLog elasticLog = new ElasticLog();
        elasticLog.setRequest(request);
        elasticLog.setTimeStamp(new Date());
        reserve.mapBooksToCustomers(bookId,request.getAccount().getCustomer().getId());
        Response response =paymentService.buyBook(request);
        elasticLog.setResponse(response);
        elasticService.saveElasticLogs(elasticLog);
        return response;
    }




    @PostMapping("/getBuyerList")
    @Operation(tags = {"Return Buyer's List"})
    public Set<Book> getBuyerList(@RequestBody Customer customer){
        log.info("Retrieving Customer's List");
        return crud.getUserBooksList(customer);
    }
    @GetMapping("/books")
    public void getBooksFromExcel() {
        // Specify the path to your Excel file
        String filePath = "E:/Fintech Path/book2.xlsx";
        excelService.readBooksFromExcel(filePath);
        log.info("Reading From Excel");
    }


}
