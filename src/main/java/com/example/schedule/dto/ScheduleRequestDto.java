package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private String writer; //작성자명
    private String password; //비밀번호
    private String content; //일정 내용
}
