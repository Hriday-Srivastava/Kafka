package com.example.retriesanddlt.service;

import com.example.retriesanddlt.entities.Customer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publishCustomer(Customer customer) {

        try {
            // Customer Object → JSON String
            String jsonString = objectMapper.writeValueAsString(customer);

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send("container", "heart", jsonString);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent JSON Message = " + jsonString);
                    System.out.println("Offset = " + result.getRecordMetadata().offset());
                    System.out.println("Partition = " + result.getRecordMetadata().partition());
                } else {
                    System.out.println("Error while sending : " + ex.getMessage());
                }
            });

        } catch (Exception e) {
            System.out.println("JSON Conversion Error : " + e.getMessage());
        }
    }


    public List<Customer> parseCsv(MultipartFile file) throws Exception {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            CsvToBean<Customer> csvToBean = new CsvToBeanBuilder<Customer>(reader)
                    .withType(Customer.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }

}
