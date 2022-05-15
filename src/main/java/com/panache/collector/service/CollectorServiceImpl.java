package com.panache.collector.service;

import com.panache.collector.data.NFT_Collection_Stats;
import com.panache.collector.data.NFT_Successful_Purchase;
import com.panache.collector.repository.NFT_Collection_Repository;
import com.panache.collector.repository.NFT_Successful_Purchase_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("collectorService")
public class CollectorServiceImpl implements CollectorService{

    @Autowired
    private NFT_Successful_Purchase_Repository nft_successful_purchase_repository;

    @Autowired
    private NFT_Collection_Repository nft_collection_repository;

    @Autowired
    private OpenSeaServiceImpl openSeaService;


    /**
     * Create a customer based on the data sent to the service class.
     * @param nft_successful_purchases
     * @return DTO representation of the customer
     */
    @Override
    public void savePurchases(List<NFT_Successful_Purchase> nft_successful_purchases) {
        nft_successful_purchase_repository.saveAll(nft_successful_purchases);
    }

    /**
     * Create a collection stat based on the data sent to the service class.
     * @param nft_collection_stats
     * @return DTO representation of the customer
     */
    @Override
    public void saveStats(NFT_Collection_Stats nft_collection_stats) {
        nft_collection_repository.save(nft_collection_stats);
    }

    @Override
    public boolean addContract(String contractAddress) {
        //OpenSeaServiceImpl openSeaService = new OpenSeaServiceImpl();
        return openSeaService.getAllPurchases(contractAddress);
    }

    @Override
    public boolean addStats(String contractAddress) {
        //OpenSeaServiceImpl openSeaService = new OpenSeaServiceImpl();
        return openSeaService.addStats(contractAddress);
    }

}
