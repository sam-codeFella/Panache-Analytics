package com.panache.collector.service;

import com.panache.collector.controller.CollectorController;
import com.panache.collector.data.NFT_Collection_Stats;
import com.panache.collector.data.NFT_Successful_Purchase;
import com.panache.collector.repository.NFT_Collection_Repository;
import com.panache.collector.repository.NFT_Query_Collection_Repository;
import com.panache.collector.repository.NFT_Successful_Purchase_Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("collectorService")
public class CollectorServiceImpl implements CollectorService{

    private static final Logger log = LoggerFactory.getLogger(CollectorServiceImpl.class);

    @Autowired
    private NFT_Successful_Purchase_Repository nft_successful_purchase_repository;

    @Autowired
    private NFT_Collection_Repository nft_collection_repository;

    @Autowired
    private NFT_Query_Collection_Repository nft_query_collection_repository;

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

    //this will be called in case of detailed view of a page.
    @Override
    public boolean findStatsperContract(String contractAddress){
        Collection<NFT_Collection_Stats> stats =  nft_collection_repository.findStatsperContract(contractAddress);
        log.info("found the entry");
        return true;
    }

    //home page.
    @Override
    public boolean fetchAllContracts(int offset, int limit){
        String range =  offset + "," + limit;
        Collection<NFT_Collection_Stats> stats =  nft_collection_repository.getAllContracts(offset, limit);
        //will need to use mapper to reduce payload.
        log.info("found all entries in this offset range.");
        return true;
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
