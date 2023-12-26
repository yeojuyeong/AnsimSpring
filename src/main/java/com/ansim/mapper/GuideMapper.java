package com.ansim.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuideMapper {

    public List<Object> selectCctvInBoundary(Map<String, Object> map);
    public List<Object> selectDeliboxInBoundary(Map<String, Object> map);
    public List<Object> selectEmergbellInBoundary(Map<String, Object> map);
    public List<Object> selectPoliceInBoundary(Map<String, Object> map);
    public List<Object> selectStoreInBoundary(Map<String, Object> map);

}
