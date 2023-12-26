package com.ansim.controller;

import com.ansim.service.GuideService;
import com.ansim.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin( origins = {"*"})
@Controller
public class GuideController {

    @Autowired
    GuideService service;

    @GetMapping ("/guide/route")
    public void getRoute() {}

    @ResponseBody
    @PostMapping  ("/guide/route")
    public List<Object> orderListFacInBoundary(@RequestBody Map<String, Object> map) {

        Double distance = Util.haversineDistance((double) map.get("midLat"), (double)map.get("midLon"),
                (double)map.get("startPointLat"), (double)map.get("startPointLon"));
        System.out.println("haversineDistance : "+ distance);

        //map.put("geoDistance",distance/100000); // 지오메트리(도, degree)의 단위로 변환, 0.001 = 100m
        map.put("geoDistance",Math.round((distance/100000)*10000)/10000.0 );
        System.out.println("geoDistance : "+map.get("geoDistance"));

        //System.out.println("dd");
        return service.findFacInBoundary(map);

    }

}

