package com.example.serializerpublisher.controller;

import com.example.serializerpublisher.entities.Customer;
import com.example.serializerpublisher.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class CustomerController {

    public CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> publishCustomer(@RequestBody Customer customer){

        customerService.publishCustomer(customer);
        return ResponseEntity.ok("Published Customer: "+customer.toString());

    }
}
