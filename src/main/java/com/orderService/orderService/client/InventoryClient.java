package com.orderService.orderService.client;
import groovy.util.logging.Slf4j;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

@Slf4j
@FeignClient(value = "inventory", url = "http://localhost:8082") //root url for inventory service
public interface InventoryClient {

   @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    boolean isInstock(@RequestParam String skuCode, @RequestParam Integer quantity);
}