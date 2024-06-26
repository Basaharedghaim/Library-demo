package com.managment.Librarydemo.services;
import com.managment.Librarydemo.LibraryDemoApplication;
import com.models.demo.models.entity.*;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class CRUD {
        private List<Book> bookList=new ArrayList<Book>();
   // static final Logger logger = (Logger) LoggerFactory.getLogger(LibraryDemoApplication.class);


    //book
@Autowired
    public CRUD(List<Book> bookList) {

    this.bookList = bookList;

    }




    public Book addNewBook(Book book){
        log.info("Book Added with id"+book.getId());
        bookList.add(book);
        return book;


    }
    public String deleteBookBasedOnId(int id) {
        Iterator<Book> iterator = bookList.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove(); // Use iterator to safely remove the element
                log.info( "Book with id=" + id + " Deleted");
                return "Book with id=" + id + " Deleted";
            }
        }
        log.info( "No Book Found with id " + id);

        return "No Book Found with id " + id;
    }
    public List<Book> showAll(){
    log.info("Books Retrieved");
        return bookList;
    }
    public String updateTitle(int id, String title) {
        for (Book book : bookList) {
            if (book.getId() == id) {
                book.setTitle(title);
                log.info("Title updated for id"+id);
                return "Title updated Successfully";
            }
        }
log.info("No Book Found");
        return "No Book Found with id " + id;
    }
    public Set<Book> getUserBooksList (Customer customer){
    log.info("Customer's List Retrieved");
            return customer.getBooks();
    }

}




