package com.ansim.service;

import com.ansim.mapper.GuideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    GuideMapper mapper;

    @Override
    public List<Object> findFacInBoundary(Map<String, Object> map) {

        //System.out.println("map.toString(): "+map.toString());
        //System.out.println("selectedFacOption"+map.get("selectedFacOption").getClass().getName());

        List<Object> allFacList = new ArrayList<>();

        ArrayList<String> arrayList = (ArrayList<String>)map.get("selectedFacOption");

        for (String fac : arrayList) {

            System.out.println("선택된 시설명:"+fac);

            if("C".equals(fac)){ //CCTV가 선택되었으면
                allFacList.addAll(mapper.selectCctvInBoundary(map));
                System.out.println("mapper.selectCctvInBoundary(map)"+mapper.selectCctvInBoundary(map).size());
            }
            if("D".equals(fac)){ //안심택배가 선택되었으면
                allFacList.addAll(mapper.selectDeliboxInBoundary(map));
                System.out.println("mapper.selectDeliboxInBoundary(map)"+mapper.selectDeliboxInBoundary(map).size());
            }
            if("E".equals(fac)){ //안심벨이 선택되었으면
                allFacList.addAll(mapper.selectEmergbellInBoundary(map));
                System.out.println("mapper.selectEmergbellInBoundary(map)"+mapper.selectEmergbellInBoundary(map).size());
            }
            if("P".equals(fac)){ //경찰서 선택되었으면
                allFacList.addAll(mapper.selectPoliceInBoundary(map));
                System.out.println("mapper.selectPoliceInBoundary(map)"+mapper.selectPoliceInBoundary(map).size());
            }
            if("S".equals(fac)){ //편의점 선택되었으면
                allFacList.addAll(mapper.selectStoreInBoundary(map));
                System.out.println("mapper.selectStoreInBoundary(map)"+mapper.selectStoreInBoundary(map).size());
            }
        }

        return allFacList;
    }
}
