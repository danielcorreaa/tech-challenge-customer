package com.techchallenge.domain.entity;

public class NotifyCustomer {

    private Customer customer;
    private String description;
    private Boolean notifySent;
    private String orderId;

    public NotifyCustomer(Customer customer, String description, String orderId) {
        this.customer = customer;
        this.description = description;
        this.orderId = orderId;
        this.notifySent = Boolean.FALSE;
    }

    public NotifyCustomer() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getNotifySent() {
        return notifySent;
    }

    public void setNotifySent(Boolean notifySent) {
        this.notifySent = notifySent;
    }

    public String getOrderId() {
        return orderId;
    }


    public  NotifyCustomer notifyCustomer(Customer customer, String description, String orderId, Boolean notifySent){
        this.customer = customer;
        this.description = description;
        this.orderId = orderId;
        this.notifySent = notifySent;
        return this;
    }
}
