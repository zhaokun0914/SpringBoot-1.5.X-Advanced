package com.fortunebill.springboot.task.service;

import org.springframework.stereotype.Service;

/**
 * @author Kavin
 * @date 2021年12月6日 09:59
 */
@Service
public class ScheduledService {

    // @Scheduled(cron = "0/2 * * * * ?")
    public void cronTest() {
        System.out.println("cronTest() ....");
    }
}
