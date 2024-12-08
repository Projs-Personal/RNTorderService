package com.orderService.orderService.server;

import com.orderService.orderService.client.InventoryClient;
import com.orderService.orderService.dto.OrderRequest;
import com.orderService.orderService.model.Order;
import com.orderService.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder (OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInstock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {
            //map ord req to ord obj :
            Order order = new Order();
            order.setOrderNumber(String.valueOf(UUID.randomUUID()));
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            //save order to order repo :
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Product with skuCode : " + orderRequest.skuCode() + " not in stock");
        }
    }
}
