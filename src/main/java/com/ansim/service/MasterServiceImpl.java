package com.ansim.service;

import com.ansim.mapper.MasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    MasterMapper mapper;

    // cctv 고장신고 수
    @Override
    public int findCCTVCount() throws Exception {
        return mapper.selectCCTVCount();
    }

    // emergbell 고장신고 수
    @Override
    public int findEmergCount() throws Exception {
        return mapper.selectEmergCount();
    }

    // delibox 고장신고 수
    @Override
    public int findDeliCount() throws Exception {
        return mapper.selectDeliCount();
    }

    @Override
    public int findPoliceCount() throws Exception {
        return mapper.selectPoliceCount();
    }

    @Override
    public int findStoreCount() throws Exception {
        return mapper.selectStoreCount();
    }

    // 최근 7일간 고장신고 등록수
    @Override
    public List<Map<String, Object>> findTotalCntInthelast7days() throws Exception {
        return mapper.selectTotalCntInthelast7days();
    }

    @Override
    public List<Map<String, Object>> findCntBytypeBrokenoptcd() throws Exception {
        return mapper.selectCntBytypeBrokenoptcd();
    }

}
