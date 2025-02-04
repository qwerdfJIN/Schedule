package com.example.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    private final Long id; //일정의 고유 ID
    private final String writer; //작성자명
    private final String password; //비밀번호
    private final String content; //일정 내용
    private final LocalDateTime createdDate; //작성일
    private final LocalDateTime updatedDate; //수정일
}

