package com.fortunebill.providerticket.controller;

import com.fortunebill.providerticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kavin
 * @date 2021年11月24日 21:15
 */
@RestController
public class TickerController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticket")
    public String getTicket() {
        System.out.println(8002);
        return ticketService.getTicket();
    }

}
