package com.study.server.controller;

import com.study.server.validator.examples.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class BookController {

    @RequestMapping(value = "/recommended")
    public String readingList(){
        log.info("request readingList");
        return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
    }

    @RequestMapping(value = "/test")
    public String test(String str, Map<String, String> map, Address address){
        return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
    }

}
