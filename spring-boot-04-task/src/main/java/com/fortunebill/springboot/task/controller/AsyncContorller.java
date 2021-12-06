package com.fortunebill.springboot.task.controller;

import com.fortunebill.springboot.task.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Kavin
 * @date 2021年12月6日 09:59
 */
@Controller
public class AsyncContorller {

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        asyncService.hello();
        return "success";
    }
}
