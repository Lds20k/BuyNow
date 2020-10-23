package br.com.zup.bootcamp.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "invoice", url = "http://localhost:8080")
public interface InvoiceClient {

    @PostMapping(value = "/invoices")
    void execute(@RequestParam("purchaseId") String purchase, @RequestParam("buyerId") String buyer);
}
