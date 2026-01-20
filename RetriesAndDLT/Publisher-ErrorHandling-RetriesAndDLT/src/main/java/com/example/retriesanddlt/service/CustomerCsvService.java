package com.example.retriesanddlt.service;

import com.example.retriesanddlt.entities.Customer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class CustomerCsvService {

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