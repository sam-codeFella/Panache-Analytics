package com.panache.collector.repository;

import com.panache.collector.data.NFT_Collection_Stats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NFT_Collection_Repository extends JpaRepository<NFT_Collection_Stats, Long> {
}
