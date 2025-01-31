package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//일정 관련 API 제공
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    //In-Memory 데이터 저장소 (실제 DB 대신 Hashmap 사용)
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    //일정 생성 API
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        //ID 자동 생성
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        //Schedule 객체 생성
        Schedule schedule = new Schedule(
                scheduleId,
                requestDto.getWriter(),
                requestDto.getPassword(),
                requestDto.getContents()
        );

        //생성된 일정 저장
        scheduleList.put(scheduleId, schedule);

        //생성된 일정 응답 반환 (201 Created)
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.CREATED);
    }

    //전체 일정 조회 API
    @GetMapping
    public List<ScheduleResponseDto> findAllMemos(
            @RequestParam(required = false) String updateDate,
            @RequestParam(required = false) String writer
    ) {

        //결과 리스트 생성
        List<ScheduleResponseDto> responseList = new ArrayList<>();

        //모든 일정 확인 후 필터링 수행
        for (Schedule schedule : scheduleList.values()) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

            //사용자가 수정일을 입력하지 않았으면 모든 일정 유지
            boolean isUpdateDateMatch = (updateDate == null) ||
                    responseDto.getUpdatedDate().toLocalDate().toString().equals(updateDate);

            //사용자가 작성자를 입력하지 않았으면 모든 일정 유지
            boolean isWriterMatch = (writer == null) ||
                    responseDto.getWriter().equals(writer);

            //두 조건을 모두 만족하는 일정만 리스트에 추가
            if (isUpdateDateMatch && isWriterMatch) {
                responseList.add(responseDto);
            }
        }

        //최신 수정일 기준으로 내림차순 정렬
        responseList.sort((o1, o2) -> o2.getUpdatedDate().compareTo(o1.getUpdatedDate()));

        //필터링 된 일정 목록 반환
        return responseList;
    }

    //선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findMemoById(@PathVariable Long id) {

        //일정 조회
        Schedule schedule = scheduleList.get(id);

        //일정이 존재하지 않으면 404 응답 반환
        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //일정 정보 반환 (200 OK)
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    //특정 일정 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateTitle(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {

        //일정 조회
        Schedule schedule = scheduleList.get(id);

        //일정이 존재하지 않으면 404 응답 반환
        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //필수값 검증 (작성자는 필수, 비밀번호와 내용은 변경 가능)
        if (requestDto.getWriter() == null || requestDto.getPassword() != null || requestDto.getContents() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //일정 수정
        schedule.update(requestDto);

        //수정된 일정 정보 반환 (200 OK)
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    //선택 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {

        //삭제할 일정이 존재하는 경우
        if (scheduleList.containsKey(id)) {
            scheduleList.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        //삭제할 일정이 존재하지 않는 경우 (404 응답 반환)
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

