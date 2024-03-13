package com.managment.Librarydemo.FeignClient;

import lombok.extern.slf4j.Slf4j;
import models.Book;
import models.Customer;
import models.Request;
import models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class PaymentService {
    private final PaymentFeignClient paymentFeignClient;
    @Autowired
    public PaymentService(PaymentFeignClient paymentFeignClient){
        this.paymentFeignClient=paymentFeignClient;
    }
    public Response buyBook(Request request){
        log.info("Processing In Payment Service");
        return paymentFeignClient.buyBook(request);
    }
}
