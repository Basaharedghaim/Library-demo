package com.managment.Librarydemo.RestController;

import com.managment.Librarydemo.FeignClient.PaymentService;
import com.managment.Librarydemo.services.ConfirmPayments;
import com.managment.Librarydemo.services.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import com.models.demo.models.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.managment.Librarydemo.services.CRUD;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bookstore")
@Slf4j
public class Rest {

    private final CRUD crud;
   private final PaymentService paymentService;
   private final ExcelService excelService;
   private final ConfirmPayments confirmPayments;
    @Autowired
    public Rest(CRUD crud, PaymentService paymentService,ExcelService excelService,ConfirmPayments confirmPayments){

        this.crud=crud;
        this.paymentService = paymentService;
        this.excelService=excelService;
        this.confirmPayments=confirmPayments;
    }
    @PostMapping("/addBook")
    @Operation(
            tags = {"Add New Book"}
    )
    public String  AddBook(@RequestBody Book book){
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
    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello From Library";
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
    @Operation(
            tags = {"Add Book to the Buyer List"}
    )
    public String buyBook(@RequestBody Request request,@RequestParam Long bookId){
        log.info("Buying A Book is in progress");
         Response response= paymentService.buyBook(request);
         return confirmPayments.ProceedPayment(response,bookId, (long) request.getAccount().getCustomer().getId());
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
