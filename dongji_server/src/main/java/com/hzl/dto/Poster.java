package com.hzl.dto;

import lombok.Data;

@Data
public class Poster {
    String sport;

    String day;

    String yearAndMonth;

    String target;

    String message;

    String head1;

    String head2;

    String time;

    //这是一个schedule的id，用来查对应的schedule
    Integer scheduleId;
}
