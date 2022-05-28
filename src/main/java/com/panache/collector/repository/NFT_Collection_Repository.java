package com.panache.collector.repository;

import com.panache.collector.data.NFT_Collection_Stats;
import com.panache.collector.data.NFT_Successful_Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NFT_Collection_Repository extends JpaRepository<NFT_Collection_Stats, Long> {

    @Query("SELECT u FROM NFT_Collection_Stats u WHERE u.contract = :contract")
    Collection<NFT_Collection_Stats> findStatsperContract(@Param("contract") String contractAddress);


    @Query(nativeQuery = true, value  = "SELECT * FROM NFT_Collection_Stats limit :start , :range")
    Collection<NFT_Collection_Stats> getAllContracts(@Param("start") int start, @Param("range") int range);


}
