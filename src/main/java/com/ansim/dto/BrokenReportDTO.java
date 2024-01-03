package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BrokenReportDTO {

    private int id;
    private String fac_id;
    private String fac_type;
    private String broken_opt_cd;
    private String content;
    private String user_id;
    private LocalDate regdate;
    private String etc;

    @Override
    public String toString() {
        return "BrokenReportDTO{" +
                "id=" + id +
                ", fac_id=" + fac_id +
                ", fac_type='" + fac_type + '\'' +
                ", broken_opt_cd='" + broken_opt_cd + '\'' +
                ", content='" + content + '\'' +
                ", user_id='" + user_id + '\'' +
                ", regdate=" + regdate +
                ", etc='" + etc + '\'' +
                '}';
    }
}
