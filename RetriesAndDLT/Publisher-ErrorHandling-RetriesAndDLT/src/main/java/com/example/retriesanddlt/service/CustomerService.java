package com.example.retriesanddlt.service;

import com.example.retriesanddlt.entities.Customer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCustomer(Customer customer){

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("container","heart", customer);

            future.whenComplete((result, ex) ->{

                System.out.println("Sent message=[" +customer + "] with offset=[" +result.getRecordMetadata().offset() + "]" );
                System.out.println("Sent to partition: " +result.getRecordMetadata().partition());

            });

        } catch (Exception e) {

            System.out.println("Unable to send message=["+ customer +"] due to : "+e.getMessage());

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
