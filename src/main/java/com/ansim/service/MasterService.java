package com.ansim.service;

import java.util.List;
import java.util.Map;

public interface MasterService {

    // cctv 고장신고 수
    public int findCCTVCount() throws Exception;

    // emergbell 고장신고 수
    public int findEmergCount() throws Exception;

    // delibox 고장신고 수
    public int findDeliCount() throws Exception;

    // police 고장신고 수
    public int findPoliceCount() throws Exception;

    // store 고장신고 수
    public int findStoreCount() throws Exception;

    // 최근 7일간 고장신고 등록수
    public List<Map<String, Object>> findTotalCntInthelast7days() throws Exception;

    // 최근 7일간 고장신고 등록수
    public List<Map<String, Object>> findCntBytypeBrokenoptcd() throws Exception;

}
