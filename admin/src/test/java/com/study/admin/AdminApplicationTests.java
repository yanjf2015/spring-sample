package com.study.admin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void contextLoads() {

    }

    @Test
    public void post() {
        ResponseEntity<String> result = testRestTemplate.getForEntity("http://172.16.113.87:8080/actuator/env",
                String.class);
        System.out.println(result);
//        result = testRestTemplate.postForEntity("http://172.16.113.87:8080/actuator/bus-refresh",
//                null, String.class);
//        System.out.println(result);
        Map<String, String> requestMap = new HashMap(1);
        requestMap.put("name", "server.ports");
        requestMap.put("value", "8089");
        result = testRestTemplate.postForEntity("http://172.16.113.87:8080/actuator/bus-env",
                requestMap, String.class);
        System.out.println(result);
    }
}
