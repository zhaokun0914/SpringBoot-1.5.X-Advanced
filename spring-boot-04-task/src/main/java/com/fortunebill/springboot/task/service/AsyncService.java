package com.fortunebill.springboot.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Kavin
 * @date 2021年12月6日 09:59
 */
@Service
public class AsyncService {

    @Async // 告诉spring 这是一个异步的方法
    public void hello() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中。。。");
    }
}
