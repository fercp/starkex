package com.fersoft.types;

public class OrderWithClientIdWithPrice extends OrderWithClientId {
    private final String humanPrice;

    public OrderWithClientIdWithPrice(Order order, String clientId, String humanPrice) {
        super(order, clientId);
        this.humanPrice = humanPrice;
    }

    public String getHumanPrice() {
        return humanPrice;
    }
}
