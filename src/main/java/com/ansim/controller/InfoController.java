package com.ansim.controller;

import com.ansim.dto.*;
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

    //시설타입에 따른 고장옵션 목록가져오기
    @GetMapping("/info/brokenType")
    public List<Map> getBrokenType( @RequestParam(name = "type") String type ) {
        System.out.println("getBrokenType 실행: "+type);
        return service.findBrokenTypeByType(type);
    }

    @PostMapping("/info/brokenReportAdd")
    public String brokenReportAdd(@RequestBody BrokenReportDTO dto) {
        System.out.println("BrokenReportDTO"+dto.toString());
        service.addBrokenReport(dto);
        return "{\"message\":\"Success\"}";
    }

}
