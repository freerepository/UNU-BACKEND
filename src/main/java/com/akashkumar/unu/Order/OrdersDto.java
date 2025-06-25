package com.akashkumar.unu.Order;

import com.akashkumar.unu.Utilities.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private String orderId;
    private String userId;
    private String dealerId;
    private String paymentId;
    private String courierId;
    private double totalPrice;
    private OrderStatus orderStatus;
    private List<String> carts = new ArrayList<>();
}
