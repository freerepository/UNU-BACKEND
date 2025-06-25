package com.akashkumar.unu.Order;

public class OrderMapper {
    public static OrdersDto toOrderDto(Orders orders) {
        OrdersDto dto = new OrdersDto();
        dto.setOrderId(orders.getOrderId());
        dto.setUserId(orders.getUserId());
        dto.setPaymentId(orders.getPaymentId());
        dto.setCourierId(orders.getCourierId());
        dto.setTotalPrice(orders.getTotalPrice());
        dto.setOrderStatus(orders.getOrderStatus());
        dto.setCarts(orders.getCarts());
        dto.setDealerId(orders.getDealerId());
        return dto;
    }

    public static Orders toOrderEntity(OrdersDto ordersDto){
        Orders orders = new Orders();
        orders.setOrderId(ordersDto.getOrderId());
        orders.setUserId(ordersDto.getUserId());
        orders.setPaymentId(ordersDto.getPaymentId());
        orders.setCourierId(ordersDto.getPaymentId());
        orders.setTotalPrice(ordersDto.getTotalPrice());
        orders.setOrderStatus(ordersDto.getOrderStatus());
        orders.setCarts(ordersDto.getCarts());
        orders.setDealerId(orders.getDealerId());
        return orders;
    }
}
