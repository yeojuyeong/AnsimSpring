package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class BoardDTO {

    private int seqno;
    private String user_id;
    private LocalDate regdate;
    private String title;
    private String content;
    private int mem_cnt;
    private String gender;
    private String sound;
    private double departure_latitude;
    private double departure_longitude;
    private double destination_latitude;
    private double destination_longitude;
    private String meeting_time;
    private int hitno;
    private String departure;
    private String destination;
    private String stopover;

}
