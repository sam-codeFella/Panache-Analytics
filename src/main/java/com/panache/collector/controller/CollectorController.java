package com.panache.collector.controller;

import com.google.gson.Gson;
import com.panache.collector.dto.AddContract;
import com.panache.collector.service.CollectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collector")
public class CollectorController {

    private static final Logger log = LoggerFactory.getLogger(CollectorController.class);
    private Gson gson = new Gson();

    @Resource(name = "collectorService")
    private CollectorService collectorService;

    /**
     * Post request to create customer information int the system.
     * @param addContract
     * @return
     */
    @PostMapping("/addContract")
    public void saveContract(final @RequestBody AddContract addContract){
        log.info("Saving data for contract -> " + gson.toJson(addContract));
        collectorService.addContract(addContract.contractAddress);
    }

    /**
     * Post request to create customer information int the system.
     * @param addStats
     * @return
     */
    @PostMapping("/addStats")
    public void addStats(final @RequestBody AddContract addStats){
        log.info("Saving stats for contract -> " + gson.toJson(addStats));
        collectorService.addStats(addStats.contractAddress);
    }




}
