package com.example.schedule.dto;

import lombok.Getter;

//일정 생성 및 수정 요청을 처리하는 DTO
@Getter
public class ScheduleRequestDto {

    private String writer; //작성자명 (필수)
    private String password; //일정 수정 및 삭제 시 필요한 비밀번호
    private String contents; // 일정 내용

}
