package com.fersoft.types;

public class OrderWithClientIdAndQuoteAmount extends OrderWithClientId {
    private final String humanQuoteAmount;

    public OrderWithClientIdAndQuoteAmount(Order order, String clientId,String humanQuoteAmount) {
        super(order, clientId);
        this.humanQuoteAmount = humanQuoteAmount;
    }

    public String getHumanQuoteAmount() {
        return humanQuoteAmount;
    }

}
