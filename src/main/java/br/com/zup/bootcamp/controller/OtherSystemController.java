package br.com.zup.bootcamp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherSystemController {

    @PostMapping("/invoices")
    public void createInvoice(@RequestParam("purchaseId") String purchase, @RequestParam("buyerId") String buyer){
        System.out.println("Invoice for " + buyer + ", for purchase "+ purchase);
    }

    @PostMapping("/ranking")
    public void addReputation(@RequestParam("purchaseId") String purchase, @RequestParam("sellerId") String seller){
        System.out.println("Reputation for " + seller + " is incremented by purchase " + purchase);
    }
}
