package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PoliceDTO {

    private String id;
    private String type;
    private String addr;
    private String police_station_nm;
    private String police_office_nm;
    private String division;
    private String telno;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
