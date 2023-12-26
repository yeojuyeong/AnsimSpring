package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmergbellDTO {

    private String id;
    private String type;
    private String mng_id;
    private String dtl_addr;
    private String road_addr;
    private String addr;
    private char police_conn_yn;
    private String instl_year;
    private String mng_org;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
