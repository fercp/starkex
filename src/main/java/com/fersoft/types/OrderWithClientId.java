package com.fersoft.types;

public class OrderWithClientId {
    private final Order order;
    private final String clientId;

    public OrderWithClientId(Order order, String clientId) {
        this.order = order;
        this.clientId = clientId;
    }

    public Order getOrder() {
        return order;
    }

    public String getClientId() {
        return clientId;
    }
}
