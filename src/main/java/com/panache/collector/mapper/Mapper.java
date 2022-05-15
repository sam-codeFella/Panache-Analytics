package com.panache.collector.mapper;

import com.google.gson.Gson;
import com.panache.collector.data.NFT_Collection_Stats;
import com.panache.collector.data.NFT_Successful_Purchase;
import com.panache.collector.dto.Asset_Events;
import com.panache.collector.dto.Event;
import com.panache.collector.dto.Stats;
import com.panache.collector.service.CollectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Mapper {

    private static final Logger log = LoggerFactory.getLogger(Mapper.class);

    @Autowired
    private CollectorService collectorService;

    private List<NFT_Successful_Purchase> purchaseList;
    private BigInteger base = new BigInteger("1000000000000000000");

    public long convertToDate(String timestamp){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
            date = dateFormat.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toInstant().getEpochSecond();
    }

    public void NFTPurchaseMapper(Event event){
        purchaseList = new ArrayList<>();
        //collectorService = new CollectorServiceImpl(); //only for runner.
        if(event.asset_events!=null){
            for(Asset_Events asset_events : event.asset_events){
                try{
                   NFT_Successful_Purchase purchase = new NFT_Successful_Purchase();
                   BigInteger total_price = new BigInteger(asset_events.total_price);
                   BigInteger price = total_price.divide(base);
                   purchase.price = price.longValue(); //no. of eth's spent.
                   purchase.priceETH = asset_events.payment_token.usd_price; //price of eth at the time.
                   purchase.contractAddress = asset_events.asset.asset_contract.address;
                   purchase.contractName = asset_events.asset.asset_contract.name;
                   purchase.txnId = asset_events.transaction.transaction_hash;
                   purchase.soldFrom = asset_events.transaction.from_account.address;
                   purchase.soldTo = asset_events.transaction.to_account.address;
                   purchase.tokenId = asset_events.asset.token_id;
                   purchase.txnDate = convertToDate(asset_events.transaction.timestamp);
                   purchaseList.add(purchase);
                  } catch (Exception e) {
                    log.error("error proccesing -> " + new Gson().toJson(asset_events));
                }
            }
        }
        collectorService.savePurchases(purchaseList);
    }

    public void NFTCollectionStatsMapper(String contractAddress, String collection, Stats stats){
        NFT_Collection_Stats nftCollectionStats = null;
        if(stats != null){
            try{
                nftCollectionStats = new NFT_Collection_Stats();
                nftCollectionStats.contract = contractAddress;
                nftCollectionStats.collection = collection;

                nftCollectionStats.average_price = stats.average_price;
                nftCollectionStats.floor_price = stats.floor_price;
                nftCollectionStats.count = stats.count;
                nftCollectionStats.market_cap = stats.market_cap;
                nftCollectionStats.num_owners = stats.num_owners;
                nftCollectionStats.num_reports = stats.num_reports;

                nftCollectionStats.one_day_average_price = stats.one_day_average_price;
                nftCollectionStats.one_day_change = stats.one_day_change;
                nftCollectionStats.one_day_sales = stats.one_day_sales;
                //nftCollectionStats.one_day_volume = stats.one_day_volume;

                nftCollectionStats.seven_day_volume = stats.seven_day_volume;
                nftCollectionStats.seven_day_change = stats.seven_day_change;
                nftCollectionStats.seven_day_sales = stats.seven_day_sales;
                nftCollectionStats.seven_day_average_price = stats.seven_day_average_price;

                nftCollectionStats.thirty_day_average_price = stats.thirty_day_average_price;
                nftCollectionStats.thirty_day_change = stats.thirty_day_change;
                nftCollectionStats.thirty_day_sales = stats.thirty_day_sales;
                nftCollectionStats.thirty_day_volume = stats.thirty_day_volume;

                nftCollectionStats.total_volume = stats.total_volume;
                nftCollectionStats.total_sales = stats.total_sales;
                nftCollectionStats.total_volume = stats.total_volume;
                nftCollectionStats.total_supply =  stats.total_supply;

            } catch (Exception e){
                log.error("error processing exception",e.getMessage());
                e.printStackTrace();
            }
            if(nftCollectionStats != null){
                collectorService.saveStats(nftCollectionStats);
            }
        }

    }

}
