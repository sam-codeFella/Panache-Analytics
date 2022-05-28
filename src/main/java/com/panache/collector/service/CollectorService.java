package com.panache.collector.service;

import com.panache.collector.data.NFT_Collection_Stats;
import com.panache.collector.data.NFT_Successful_Purchase;

import java.util.List;

public interface CollectorService {

    //db call.
    public void savePurchases(List<NFT_Successful_Purchase> nft_successful_purchase);

    //db call
    public void saveStats(NFT_Collection_Stats nft_collection_stats);

    //Opensea service layer interaction.
    public boolean addContract(final String contractAddress);

    //Opensea service layer interaction.
    public boolean addStats(final String contractAddress);
}
