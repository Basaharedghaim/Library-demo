package com.managment.Librarydemo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.*;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Library API",version = "1.7"),
servers ={@Server(url = "http://localhost:8080"),@Server(url = "http://oneoone.com")} )
@EnableFeignClients
@EnableDiscoveryClient
@EntityScan("com.models.demo.models")
public class LibraryDemoApplication
{
	private static final Logger logger = (Logger) LoggerFactory.getLogger(LibraryDemoApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(LibraryDemoApplication.class, args);

	}
}
