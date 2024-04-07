package com.managment.Librarydemo.services;

import com.managment.Librarydemo.repository.BookRepository;
import com.managment.Librarydemo.repository.CustomerRepository;
import com.models.demo.models.entity.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfirmPayments {
    BookRepository bookRepository;
    CustomerRepository customerRepository;
    @Autowired
    public ConfirmPayments(BookRepository bookRepository,CustomerRepository customerRepository){
        this.bookRepository=bookRepository;
        this.customerRepository=customerRepository;
    }
    @Transactional
    public String ProceedPayment(Response response ,Long bookId,Long customerId ){
        if(response.getStatus()== Status.SUCCESS){
        Book book = bookRepository.findById(bookId)
               .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " +bookId));
            Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("user not found with id: " +customerId));

       customer.getBooks().add(book);
       book.getCustomers().add(customer);

        customerRepository.save(customer);
        bookRepository.save(book);
        return "Successful";

        } else if (response.getStatus()==Status.FAIL) {
            return "Failed";

        }
        else {
           return  "Still Processing";

        }
    }

}
