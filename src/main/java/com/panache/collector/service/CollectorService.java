package com.panache.collector.service;

import com.panache.collector.data.NFT_Collection_Stats;
import com.panache.collector.data.NFT_Successful_Purchase;

import java.util.List;

public interface CollectorService {

    public boolean addContract(final String contractAddress);

    public void savePurchases(List<NFT_Successful_Purchase> nft_successful_purchase);
    public void saveStats(NFT_Collection_Stats nft_collection_stats);


    public boolean addStats(final String contractAddress);
}
