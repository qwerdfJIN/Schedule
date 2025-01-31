package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

//일정 조회 응답을 처리하는 DTO
@Getter
public class ScheduleResponseDto {

    private Long id; //일정의 고유 ID
    private String writer; //작성자명
    private String contents; //일정 내용
    private LocalDateTime createdDate; //작성일
    private LocalDateTime updatedDate; //수정일

    //Schedule Entity를 ScheduleResponseDto로 변환하는 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.writer = schedule.getWriter();
        this.contents = schedule.getContents();
        this.createdDate = schedule.getCreatedDate();
        this.updatedDate = schedule.getUpdatedDate();
    }
}
