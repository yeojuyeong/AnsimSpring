package com.ansim.service;

import com.ansim.dto.*;

import java.util.List;
import java.util.Map;

public interface InfoService {

    //cctv 목록 보기
    List<CctvDTO> findCctvInBoundary(Map<String, Double> map);

    //emergbell 목록 보기
    List<EmergbellDTO> findEmergbellInBoundary(Map<String, Double> map);

    //delibox 목록 보기
    List<DeliboxDTO> findDeliboxInBoundary(Map<String, Double> map);

    //police 목록 보기
    List<PoliceDTO> findPoliceInBoundary(Map<String, Double> map);

    //store 목록 보기
    List<StoreDTO> findStoreInBoundary(Map<String, Double> map);

    List<Map> findBrokenTypeByType(String type);

    void addBrokenReport(BrokenReportDTO dto);

}
