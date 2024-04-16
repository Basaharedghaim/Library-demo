package com.managment.Librarydemo.services;

import com.managment.Librarydemo.exception.CustomerErrorResponse;
import com.managment.Librarydemo.repository.BookRepository;
import com.managment.Librarydemo.repository.CustomerRepository;
import com.models.demo.models.entity.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public String proceedPayment(Long bookId, Long customerId) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            Optional<Customer> customerOptional = customerRepository.findById(customerId);

            if (bookOptional.isPresent() && customerOptional.isPresent()) {
                Book book = bookOptional.get();
                Customer customer = customerOptional.get();

                customer.getBooks().add(book);
                book.getCustomers().add(customer);

                customerRepository.save(customer);
                bookRepository.save(book);

                return "Successful";
            } else {
                throw new EntityNotFoundException("Book or Customer not found");
            }
        } catch (EntityNotFoundException e) {
            // Log the exception or handle it as needed
            throw e;
        } catch (Exception e) {
            // Log the exception or handle it as needed
            throw new CustomerErrorResponse("Transaction failed: " + e.getMessage());
        }
    }



}
