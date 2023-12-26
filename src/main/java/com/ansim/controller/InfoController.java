package com.ansim.controller;

import com.ansim.dto.*;
import com.ansim.mapper.InfoMapper;
import com.ansim.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
public class InfoController {
    private final InfoService service;

    //지도페이지 보기
    @GetMapping("/map/view")
    public void getView() { }

    // CCTV 목록 가져오기
    @PostMapping("/info/cctvList")
    public List<CctvDTO> postCctvInBoundary(@RequestBody Map<String, Double> polygonPoint) {
        List<CctvDTO> cctvDTOList = service.findCctvInBoundary(polygonPoint);

        System.out.println("cctvDTOList.size()"+cctvDTOList.size());

        return cctvDTOList;
    }

    // emergbell 목록 가져오기
    @PostMapping("/info/emergbellList")
    public List<EmergbellDTO> postEmergbellInBoundary(@RequestBody Map<String, Double> polygonPoint) {
        List<EmergbellDTO> emergbellDTOList = service.findEmergbellInBoundary(polygonPoint);

        System.out.println("emergbellDTOList.size()"+emergbellDTOList.size());

        return emergbellDTOList;
    }

    // delibox 목록 가져오기
    @PostMapping("/info/deliboxList")
    public List<DeliboxDTO> postDeliboxInBoundary(@RequestBody Map<String, Double> polygonPoint) {
        List<DeliboxDTO> deliboxDTOList = service.findDeliboxInBoundary(polygonPoint);
        System.out.println("deliboxDTOList.size()"+deliboxDTOList.size());

        return deliboxDTOList;
    }

    // police 목록 가져오기
    @PostMapping("/info/policeList")
    public List<PoliceDTO> postPoliceInBoundary(@RequestBody Map<String, Double> polygonPoint) {
        List<PoliceDTO> policeDTOList = service.findPoliceInBoundary(polygonPoint);

        System.out.println("policeDTOList.size()"+policeDTOList.size());

        return policeDTOList;
    }

    // store 목록 가져오기
    @PostMapping("/info/storeList")
    public List<StoreDTO> postStoreInBoundary(@RequestBody Map<String, Double> polygonPoint) {
        List<StoreDTO> storeDTOList = service.findStoreInBoundary(polygonPoint);

        System.out.println("storeDTOList.size()"+storeDTOList.size());

        return storeDTOList;
    }

    // 채팅
    @GetMapping("/test")
    public Map<String, Object> testHandler() {
        System.out.println("TEST RestAPI / Test 핸들러 실행");

        Map<String, Object> res = new HashMap<>();
        res.put("SUCCESS", true);
        res.put("SUCCESS_TEXT", "Hello SpringBoot/React");

        return res;
    }

}
