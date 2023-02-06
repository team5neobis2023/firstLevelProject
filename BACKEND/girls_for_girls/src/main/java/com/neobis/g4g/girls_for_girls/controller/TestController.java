package com.neobis.g4g.girls_for_girls.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Tag(name = "Test service", description = "Пример оформления сваггера")
public class TestController {

    @GetMapping
    public String helloWorld(){
        return "Hello world!";
    }
}
