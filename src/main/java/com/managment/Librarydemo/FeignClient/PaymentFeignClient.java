package com.managment.Librarydemo.FeignClient;

import com.managment.Librarydemo.LibraryDemoApplication;
import com.models.demo.models.entity.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Payments")
public interface PaymentFeignClient {

    @PostMapping("/paymentsClient/buyBook")
     Response buyBook(@RequestBody Request request);
}