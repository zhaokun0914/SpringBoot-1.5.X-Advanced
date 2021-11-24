package com.fortunebill.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kavin
 * @date 2021年11月24日 21:42
 */
@Controller
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

}
