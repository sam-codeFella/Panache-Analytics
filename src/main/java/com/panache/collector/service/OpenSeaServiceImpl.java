package com.panache.collector.service;

import com.google.gson.Gson;
import com.panache.collector.controller.CollectorController;
import com.panache.collector.dto.AssetsResponse;
import com.panache.collector.dto.Event;
import com.panache.collector.dto.Stats;
import com.panache.collector.dto.StatsResponse;
import com.panache.collector.mapper.Mapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


//service class to handle all openSea related service calls.
@Component
public class OpenSeaServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(OpenSeaServiceImpl.class);

    private String assetUrl = "https://api.opensea.io/api/v1/assets?asset_contract_address=";
    private String collectionUrl = "https://api.opensea.io/api/v1/collection/";

    @Autowired
    private Mapper mapper;

    private Set<String> cursorStore;

    //pass the url and the call will be made from here.
    //I can shift this to another class as well, like a feign controller.
    public Response execute(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("X-API-KEY","cf0d1253459943a49bd4d2c50def5367") //my key.
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    //i will need to make repeated calls to get all the purchases since results are limited.
    //the results are in reverse order.
    public boolean getAllPurchases(String contractAddress){
        //pass url to the executor service and break down the response.
        String initialUrl = "https://api.opensea.io/api/v1/events?asset_contract_address="+contractAddress+"&event_type=successful&limit=50";
        String cursorUrl = initialUrl + "&cursor=";
        //System.out.println(execute(url));
        Gson gson = new Gson();
        ResponseBody responseBody = null;
        Event event = null;
        //mapper = new Mapper();
        boolean isSuccess = false;
        boolean isNextEnabled = false;
        boolean result = true;
        String queryUrl = null;
        cursorStore = new HashSet<>();
        try {
            while(!isSuccess){
                if(!isNextEnabled){
                    responseBody = execute(initialUrl).body();
                }
                else{
                    if(queryUrl!=null){
                        responseBody = execute(queryUrl).body();
                    }
                }
                event = gson.fromJson(responseBody.string(), Event.class);
                if(event.next == null){
                    log.info("done processing this");
                    isSuccess = true;
                }
                else{
                   if(cursorStore.contains(event.next)){
                       log.error("repeated next value man");
                   } else {
                       cursorStore.add(event.next);
                   }
                   log.info("next cursor is --> " + event.next);
                   isNextEnabled = true;
                   queryUrl = cursorUrl + event.next;
                }
                mapper.NFTPurchaseMapper(event);
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }

    public Stats collectionStats(String collectionSlug){
        Stats result = null;
        StatsResponse statsResponse = null;
        Gson gson = new Gson();
        String url = collectionUrl + collectionSlug + "/stats";
        ResponseBody responseBody = null;
        try {
            responseBody = execute(url).body();
            statsResponse = gson.fromJson(responseBody.string(), StatsResponse.class);
            result = statsResponse.stats;
        } catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }

    public boolean addStats(String contractAddress){
        //get collection slug first.
        boolean result = false;
        String url = assetUrl + contractAddress;
        String limit = "&limit=1";
        url = url + limit;
        Gson gson = new Gson();

        ResponseBody responseBody = null;
        AssetsResponse assetsResponse = null;
        try{
            responseBody = execute(url).body();
            assetsResponse = gson.fromJson(responseBody.string(), AssetsResponse.class);
            log.info(assetsResponse.assets.get(0).collection.slug);
            String slug = assetsResponse.assets.get(0).collection.slug;
            Stats stats = collectionStats(slug);
            mapper.NFTCollectionStatsMapper(contractAddress, slug, stats);
            // now after this i need to make another call.
            // --> which will need to be abstracted out.

            result = true;
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }


}
