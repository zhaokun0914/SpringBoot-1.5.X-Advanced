package com.fortunebill.ticket.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fortunebill.ticket.service.TicketService;
import org.springframework.stereotype.Component;

/**
 * @author Kavin
 * @date 2021年11月24日 19:41
 */
@Component  // 将其加入到spring容器中
@Service    // 将服务发布出去
public class TicketServiceImpl implements TicketService {

    @Override
    public String getTicket() {
        return "《厉害了，我的国》";
    }
}
