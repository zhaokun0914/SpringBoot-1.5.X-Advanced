package com.fortunebill.user.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.fortunebill.ticket.service.TicketService;
import org.springframework.stereotype.Service;

/**
 * 我们需要使用dubbo将我们的服务提供者注册到zookeeper中
 * 消费者需要从zookeeper中订阅服务，找到服务的提供者地址列表之后，消费者就能直接调用服务提供者的服务了
 *
 * @author Kavin
 * @date 2021年11月24日 19:45
 */
@Service
public class UserService {

    /**
     * 按照全类名匹配，看注册中心中谁注册了这个服务
     */
    @Reference
    private TicketService ticketService;

    public void hello() {
        // 我们只是调用方法，但是dubbo底层就会调用远程提供者的方法
        System.out.println("买到票了" + ticketService.getTicket());
    }

}
