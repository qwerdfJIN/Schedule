package com.example.schedule.entity;

import com.example.schedule.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

//일정 Entity 클래스
@Getter
@AllArgsConstructor
public class Schedule {

    private Long id; //일정의 고유 ID
    private String writer; //작성자명
    private String password; //비밀번호
    private String contents; //일정 내용
    private LocalDateTime createdDate = LocalDateTime.now(); //작성일
    private LocalDateTime updatedDate = createdDate; //수정일
    
    //일정 생성자
    public Schedule(Long id, String Writer, String password, String contents) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = this.createdDate;
    }
    
    //일정 수정 메서드
    public void update(ScheduleRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.contents = requestDto.getContents();
        this.updatedDate = LocalDateTime.now();
    }
}
