package com.ansim.service;

import com.ansim.dto.*;
import com.ansim.mapper.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    InfoMapper mapper;

    //cctv 목록 보기
    @Override
    public List<CctvDTO> findCctvInBoundary(Map<String, Double> map) {
        return mapper.selectCctvInBoundary(map);
    }

    //emergbell 목록 보기
    @Override
    public List<EmergbellDTO> findEmergbellInBoundary(Map<String, Double> map) {
        return mapper.selectEmergbellInBoundary(map);
    }

    //delibox 목록 보기
    @Override
    public List<DeliboxDTO> findDeliboxInBoundary(Map<String, Double> map) {
        return mapper.selectDeliboxInBoundary(map);
    }

    //police 목록 보기
    @Override
    public List<PoliceDTO> findPoliceInBoundary(Map<String, Double> map) {
        return mapper.selectPoliceInBoundary(map);
    }

    //store 목록 보기
    @Override
    public List<StoreDTO> findStoreInBoundary(Map<String, Double> map) {
        return mapper.selectStoreInBoundary(map);
    }

    @Override
    public List<Map> findBrokenTypeByType(String type) {
        String group_cd = "3";
        switch (type) {
            case "C":
                group_cd = "3";
                break;
            case "E":
                group_cd = "4";
                break;
            case "D":
                group_cd = "5";
                break;
            case "P":
                group_cd = "6";
                break;
            case "S":
                group_cd = "7";
                break;
        }
        return mapper.selectBrokenTypeByType(group_cd);
    }

    @Override
    public void addBrokenReport(BrokenReportDTO dto) {
        mapper.insertBrokenReport(dto);
    }
}
