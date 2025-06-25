package com.akashkumar.unu.Order;

import com.akashkumar.unu.Utilities.Enums.OrderStatus;
import com.akashkumar.unu.Utilities.Urls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Urls.OrdersCollection)
public class Orders {
    @Id
    private String orderId;
    private String userId;

    private String dealerId;

    private String paymentId;
    private String courierId;
    private double totalPrice;
    private OrderStatus orderStatus;
    private List<String> carts = new ArrayList<>();
}
