package com.one.filesearch.controller;

import com.one.filesearch.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@Slf4j
public class MyController {

//    Autowiring service class
    @Autowired
    private MyService myService;

//    Get request with file name as a PathVariable
    @GetMapping("{fileName}")
    public ResponseEntity<?> getDuplicates(@PathVariable String fileName) throws IOException {

//        logging for a controller method invokation
        log.info("GetDuplicates method is called");

//        Get data from a service class method
        List<Map.Entry<String, Integer>> duplicateWordsList = myService.getDuplicateWords(fileName);


        log.info("Data extracted successfully from service class.");

//        returning the ResponseEntity with duplicate word list and Http response ok
        return new ResponseEntity<>(duplicateWordsList, HttpStatus.OK);

    }
}
