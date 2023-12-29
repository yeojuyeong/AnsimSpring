package com.ansim.controller;

import com.ansim.service.GuideService;
import com.ansim.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin( origins = {"*"})
@Controller
public class GuideController {

    @Autowired
    GuideService service;

    @GetMapping ("/guide/route")
    public void getRoute(Model model, HttpSession session) {
        model.addAttribute("accessToken", session.getAttribute("accessToken"));
    }

    @ResponseBody
    @PostMapping  ("/guide/route")
    public List<Object> orderListFacInBoundary(@RequestBody Map<String, Object> map) {

        Double distance = Util.haversineDistance((double) map.get("midLat"), (double)map.get("midLon"),
                (double)map.get("startPointLat"), (double)map.get("startPointLon"));
        System.out.println("haversineDistance : "+ distance);

        //map.put("geoDistance",distance/100000); // 지오메트리(도, degree)의 단위로 변환, 0.001 = 100m
        map.put("geoDistance",Math.round((distance/100000)*10000)/10000.0 );
        System.out.println("geoDistance : "+map.get("geoDistance"));

        return service.findFacInBoundary(map);

    }

    @PostMapping ("/guide/jwttest")
    @ResponseBody
    public String getRoute1(Model model, HttpSession session) {
        //model.addAttribute("accessToken", session.getAttribute("accessToken"));
        System.out.println("jwttest입성");
        return "jwttest입성";
    }


}


