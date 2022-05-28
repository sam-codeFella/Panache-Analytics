package com.panache.collector.repository;

import com.panache.collector.data.NFT_Successful_Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NFT_Successful_Purchase_Repository extends JpaRepository<NFT_Successful_Purchase, Long> {


    /*@Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    User findUserByStatusAndNameNamedParams(
            @Param("status") Integer status,
            @Param("name") String name);*/

/*    @Query("SELECT u FROM NFT_Successful_Purchase u WHERE u.contractAddress = :contractAddress")
    Collection<NFT_Successful_Purchase> findStats(@Param("contractAddress") String contractAddress);*/

}
