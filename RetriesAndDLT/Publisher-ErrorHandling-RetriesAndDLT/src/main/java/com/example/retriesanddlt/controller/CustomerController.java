package com.example.retriesanddlt.controller;

import com.example.retriesanddlt.entities.Customer;

import com.example.retriesanddlt.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/publish")
public class CustomerController {

    public CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @PostMapping(value = "/customers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Customer> uploadCsv(@RequestParam("file") MultipartFile file) throws Exception {
        List<Customer> customerList = customerService.parseCsv(file);
        customerList.stream().forEach(customer -> customerService.publishCustomer(customer));
        return customerList;
    }


}