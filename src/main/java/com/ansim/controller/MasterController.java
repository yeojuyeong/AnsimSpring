package com.ansim.controller;

import com.ansim.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
public class MasterController {

    // 의존성 주입
    private final MasterService service;

    //마스터 페이지
    @GetMapping("/restapi/master")
    public Map<String, Object> getMasterDetails() throws Exception {

        Map<String, Object> response = new HashMap<>();

        response.put("cctv_report", service.findCCTVCount());
        response.put("emergbell_report", service.findEmergCount());
        response.put("delibox_report", service.findDeliCount());
        response.put("police_report", service.findPoliceCount());
        response.put("store_report", service.findStoreCount());
        response.put("7days_report", service.findTotalCntInthelast7days());
        response.put("cntBytypeBrokenoptcd", service.findCntBytypeBrokenoptcd());

        //System.out.println("findCntBytypeBrokenoptcd"+service.findCntBytypeBrokenoptcd());

        return response;

    }

}
