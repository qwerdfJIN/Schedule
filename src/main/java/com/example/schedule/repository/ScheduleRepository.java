package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule save(Schedule memo); //저장

    Optional<Schedule> findById(Long id); //선택 일정 조회

    List<Schedule> findByWriter(String writer); //작성자명으로 필터링하여 조회

    List<Schedule> findByUpdatedDate(String updatedDate); //수정일로 필터링하여 조회

    List<Schedule> findAll(); //전체 일정 조회

    Schedule updateContent(Long id, String content); //일정 수정

    void deleteById(Long id); //선택 일정 삭제
}
