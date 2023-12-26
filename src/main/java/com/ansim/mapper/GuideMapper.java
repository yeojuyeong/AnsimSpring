package com.ansim.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuideMapper {

    List<Object> selectCctvInBoundary(Map<String, Object> map);
    List<Object> selectDeliboxInBoundary(Map<String, Object> map);
    List<Object> selectEmergbellInBoundary(Map<String, Object> map);
    List<Object> selectPoliceInBoundary(Map<String, Object> map);
    List<Object> selectStoreInBoundary(Map<String, Object> map);

}
