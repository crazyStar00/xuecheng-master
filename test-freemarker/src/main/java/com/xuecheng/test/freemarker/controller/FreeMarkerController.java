package com.xuecheng.test.freemarker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequestMapping("/freemarker")
@Controller
public class FreeMarkerController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test1")
    public String freemarker(Map<String,Object> map){
        map.put("name", "star");
        return "test1";
    }
}
