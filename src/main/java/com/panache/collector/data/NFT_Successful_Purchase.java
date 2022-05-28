package com.panache.collector.data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class NFT_Successful_Purchase {

    //@Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    //private Long id;

    @Id
    public String txnId;

    public String soldFrom;
    public String soldTo; //this is a soldTo issue ??
    public double price; // i need to make it double, shouldn't i ?
    public String priceETH;
    public String contractAddress;
    public String tokenId;
    public String contractName;

    @Column(name = "Timestamp", updatable = false)
    public long txnDate;

}
