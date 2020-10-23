package br.com.zup.bootcamp.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ranking", url = "http://localhost:8080")
public interface RankingClient {

    @PostMapping(value = "/ranking")
    void execute(@RequestParam("purchaseId") String purchase, @RequestParam("sellerId") String seller);
}
