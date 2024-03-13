package com.managment.Librarydemo.FeignClient;

import com.managment.Librarydemo.LibraryDemoApplication;
import models.Request;
import models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PaymentFeignClient",url="http://localhost:8081/paymentsClient")
public interface PaymentFeignClient {

    @PostMapping("/buyBook")
     Response buyBook(@RequestBody Request request);
}