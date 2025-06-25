package com.akashkumar.unu.Courier.dto;

import com.akashkumar.unu.Utilities.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ChangeOrderStatus {
    private String dealerId;
    private String courierId;
    private String userId;
    private String orderId;
    private OrderStatus status;
}
