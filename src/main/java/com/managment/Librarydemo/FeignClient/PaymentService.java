package com.managment.Librarydemo.FeignClient;

import com.managment.Librarydemo.exception.CustomerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import com.models.demo.models.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

@Service
@Slf4j

public class PaymentService {
    private final PaymentFeignClient paymentFeignClient;
    @Autowired
    public PaymentService(PaymentFeignClient paymentFeignClient){
        this.paymentFeignClient=paymentFeignClient;
    }
    public String buyBook(Request request) {
        try {
            log.info("Processing In Payment Service");
            return paymentFeignClient.buyBook(request);
        } catch (CustomerErrorResponse e)
        {
            if ("Insufficient Balance".equals(e.getErrorType())) {
                throw new CustomerErrorResponse("Insufficient Balance");
            }
            if ("Customer Not Found".equals(e.getErrorType()))
            {
                throw new CustomerErrorResponse("Customer Not Found");
            }
            }
        throw new CustomerErrorResponse();
        }
    }



