package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StoreDTO {

    private String id;
    private String type;
    private String addr;
    private String district;
    private String store_brand;
    private String store_nm;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
