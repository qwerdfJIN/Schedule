package com.example.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {

    private Long id; //일정의 고유 ID
    private String writer; //작성자명
    private String password; //비밀번호
    private String content; //일정 내용
    private LocalDateTime createdDate = LocalDateTime.now(); //작성일: 일정을 처음 작성한 시간, default: 현재 시간
    private LocalDateTime updatedDate = LocalDateTime.now(); //작성일: 일정을 마지각으로 수정한 시간, default: 현재 시간

    // 일정 생성자 (일정 작성 시 사용)
    public Schedule(String writer, String password, String content) {
        this.writer = writer;
        this.password = password;
        this.content = content;
    }

    // 일정 생성자 (일정 조회 및 수정 시 사용)
    public Schedule(Long id, String writer, String password, String content, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}