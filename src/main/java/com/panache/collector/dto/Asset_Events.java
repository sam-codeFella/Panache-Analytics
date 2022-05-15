package com.panache.collector.dto;

public class Asset_Events {
    public String total_price; //this is the actual prioce.
    public PaymentToken payment_token; //this will tell the price of ether at the time.
    public Asset asset;
    public Dev_Fee_Payment_Event dev_fee_payment_event; //not sure if i need this at all.
    public Transaction_Hash transaction;

    
}
