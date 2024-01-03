package com.ansim.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MasterMapper {

    // cctv 고장신고 수
    public int selectCCTVCount() throws Exception;

    // emergbell 고장신고 수
    public int selectEmergCount() throws Exception;

    // delibox 고장신고 수
    public int selectDeliCount() throws Exception;

    // 최근 7일간 고장신고 등록수
    public List<Map<String, Object>> reportRegdateCnt() throws Exception;


}
