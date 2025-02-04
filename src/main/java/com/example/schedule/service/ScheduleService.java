package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {

        //ScheduleResponseDto에서 받아온 정보로 Schedule 객체 생성
        Schedule schedule = new Schedule(dto.getWriter(), dto.getPassword(), dto.getContent()); // 저장 전: id가 없는 메모

        //Schedule객체를 DB에 저장하고 저장된 Schedule 객체 반환
        Schedule savedSchedule = scheduleRepository.save(schedule); // 저장 후: id가 있는 메모

        //저장된 일정 정보를 ScheduleResponseDto 형태로 변환하여 반환
        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getWriter(),
                savedSchedule.getPassword(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedDate(),
                savedSchedule.getUpdatedDate()
        );
    }

    //전체 일정 조회 (수정일 및 작성자명으로 필터링 가능)
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll(String writer, String updatedDate) {
        List<Schedule> schedules;

        //작성자명이 주어진 경우 해당 작성자 일정만 조회
        if (writer != null && !writer.isEmpty()) {
            schedules = scheduleRepository.findByWriter(writer);

        //수정일이 주어진 경우 해당 수정일에 해당하는 일정만 조회
        } else if (updatedDate != null && !updatedDate.isEmpty()) {
            schedules = scheduleRepository.findByUpdatedDate(updatedDate);

        //필터링 조건이 없으면 전체 일정 조회
        } else {
            schedules = scheduleRepository.findAll();
        }

        //수정일 기준 내림차순 정렬
        schedules.sort((s1, s2) -> s2.getUpdatedDate().compareTo(s1.getUpdatedDate()));

        //Schedule 객체들을 ScheduleResponseDto로 변환
        List<ScheduleResponseDto> dtoList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleResponseDto dto = new ScheduleResponseDto(
                    schedule.getId(),
                    schedule.getWriter(),
                    schedule.getPassword(),
                    schedule.getContent(),
                    schedule.getCreatedDate(),
                    schedule.getUpdatedDate()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }

    //선택 일정 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long scheduleId) {

        //id로 일정 조회, 일정이 없으면 예외 발생
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 메모가 존재하지 않습니다.")
        );

        //일정 정보가 담긴 ScheduleResponseDto 반환
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getWriter(),
                schedule.getPassword(),
                schedule.getContent(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        );
    }

    //일정 수정
    @Transactional
    public ScheduleResponseDto updateContent(Long scheduleId, ScheduleRequestDto dto, String password) {

        //수정할 일정 조회, 일정이 없으면 예외 발생
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 메모가 존재하지 않습니다.")
        );

        //비밀번호 검증 (수정할 때 비밀번호가 맞는지 확인)
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //일정 내용 업데이트
        Schedule updatedSchedule = scheduleRepository.updateContent(scheduleId, dto.getContent());

        //수정된 일정 정보 반환
        return new ScheduleResponseDto(
                updatedSchedule.getId(),
                updatedSchedule.getWriter(),
                updatedSchedule.getPassword(),
                updatedSchedule.getContent(),  // content 추가
                updatedSchedule.getCreatedDate(),
                updatedSchedule.getUpdatedDate()
        );
    }

    //일정 삭제
    @Transactional
    public void deleteById(Long scheduleId, String password) {

        //삭제할 일정 조회, 일정이 없으면 예외 발생
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 메모가 존재하지 않습니다.")
        );

        // 비밀번호 검증 (삭제할 때 비밀번호가 맞는지 확인)
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //일정 삭제
        scheduleRepository.deleteById(scheduleId);
    }
}
