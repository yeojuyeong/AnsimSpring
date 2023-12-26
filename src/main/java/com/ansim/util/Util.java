package com.ansim.util;

public class Util {

    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        // 지구 반지름 (미터)
        final double R = 6371000;

        // 위도와 경도를 라디안으로 변환
        double radLat1 = Math.toRadians(lat1);
        double radLon1 = Math.toRadians(lon1);
        double radLat2 = Math.toRadians(lat2);
        double radLon2 = Math.toRadians(lon2);

        // 두 지점 간의 위도 및 경도 차이 계산
        double dLat = radLat2 - radLat1;
        double dLon = radLon2 - radLon1;

        // Haversine 공식 적용
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 거리 계산 (미터)
        return R * c;
    }

}
