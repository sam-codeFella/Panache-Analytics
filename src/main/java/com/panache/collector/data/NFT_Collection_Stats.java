package com.panache.collector.data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class NFT_Collection_Stats {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public String contract;
    public String collection;
    public float one_day_volume;
    public float one_day_change;
    public float one_day_sales;
    public float one_day_average_price;
    public float seven_day_volume;
    public float seven_day_change;
    public float seven_day_sales;
    public float seven_day_average_price;
    public float thirty_day_volume;
    public float thirty_day_change;
    public float thirty_day_sales;
    public float thirty_day_average_price;
    public float total_volume;
    public float total_sales;
    public float total_supply;
    public float count;
    public float num_owners;
    public float average_price;
    public float num_reports;
    public float market_cap;
    public float floor_price;
}
