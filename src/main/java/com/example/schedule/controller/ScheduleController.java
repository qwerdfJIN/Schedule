package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    //일정 생성 API
    @PostMapping("/Schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return ResponseEntity.ok(scheduleService.createSchedule(dto)); //일정 생성 후 응답
    }

    //전체 일정 조회 API
    @GetMapping("/Schedules")
    public ResponseEntity<List<ScheduleResponseDto>> findAll(
            @RequestParam(required = false) String writer, //작성자명으로 필터링
            @RequestParam(required = false) String updatedDate) { //수정일로 필터링
        return ResponseEntity.ok(scheduleService.findAll(writer, updatedDate)); //조건에 맞는 전체 일정 조회
    }

    //선택 일정 조회 API
    @GetMapping("/Schedules/{ScheduleId}")
    public ScheduleResponseDto findById(@PathVariable Long ScheduleId) {
        return scheduleService.findById(ScheduleId); //ID로 특정 일정 조회
    }

    //일정 수정 API
    @PutMapping("/Schedules/{scheduleId}")
    public ScheduleResponseDto updateContent(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto dto, @RequestParam String password) {
        return scheduleService.updateContent(scheduleId, dto, password); //일정 수정 후 응답
    }

    //일정 삭제 API
    @DeleteMapping("/Schedules/{ScheduleId}")
    public void delete(@PathVariable Long ScheduleId, @RequestParam String password) {
        scheduleService.deleteById(ScheduleId, password); //일정 삭제 후 응담 없음
    }
}
