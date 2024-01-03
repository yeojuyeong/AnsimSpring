package com.ansim.mapper;

import com.ansim.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InfoMapper {

    //cctv 목록 보기
    List<CctvDTO> selectCctvInBoundary(Map<String, Double> map);

    //emergbell 목록 보기
    List<EmergbellDTO> selectEmergbellInBoundary(Map<String, Double> map);

    //delibox 목록 보기
    List<DeliboxDTO> selectDeliboxInBoundary(Map<String, Double> map);

    //police 목록 보기
    List<PoliceDTO> selectPoliceInBoundary(Map<String, Double> map);

    //store 목록 보기
    List<StoreDTO> selectStoreInBoundary(Map<String, Double> map);

    //store 목록 보기
    List<Map> selectBrokenTypeByType(String group_cd);

    void insertBrokenReport(BrokenReportDTO dto);
}
