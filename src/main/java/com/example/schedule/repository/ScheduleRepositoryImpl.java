package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    //일정 저장 (생성)
    @Override
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //작성일과 수정일이 null이면 기본값 설정
        if (schedule.getCreatedDate() == null) {
            schedule.setCreatedDate(LocalDateTime.now());
        }
        if (schedule.getUpdatedDate() == null) {
            schedule.setUpdatedDate(LocalDateTime.now());
        }
        
        //DB에 일정 저장
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into schedule(writer, password, content, created_date, updated_date) values(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, schedule.getWriter());
            ps.setString(2, schedule.getPassword());
            ps.setString(3, schedule.getContent());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getCreatedDate()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(schedule.getUpdatedDate()));
            return ps;
        }, keyHolder);
        
        //생성된 ID를 객체에 설정
        schedule.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return schedule;
    }
    
    //선택 일정 조회
    @Override
    public Optional<Schedule> findById(Long id) {
        List<Schedule> schedules = jdbcTemplate.query(
                "select * from schedule where id = ?",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getString("content"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("updated_date").toLocalDateTime()
                ),
                id
        );

        return schedules.stream().findAny();
    }
    
    //전체 일정 조회
    @Override
    public List<Schedule> findAll() {
        return jdbcTemplate.query(
                "select * from schedule",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getString("content"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("updated_date").toLocalDateTime()
                )
        );
    }
    
    //작성자명으로 일정 조회
    @Override
    public List<Schedule> findByWriter(String writer) {
        return jdbcTemplate.query(
                "select * from schedule where writer = ? order by updated_date desc",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getString("content"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("updated_date").toLocalDateTime()
                ),
                writer
        );
    }
    
    //수정일로 일정 조회
    @Override
    public List<Schedule> findByUpdatedDate(String updatedDate) {
        return jdbcTemplate.query(
                "select * from schedule where updated_date like ? order by updated_date desc",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getString("content"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("updated_date").toLocalDateTime()
                ),
                updatedDate + "%"
        );
    }
    
    //일정 수정
    @Override
    public Schedule updateContent(Long id, String content) {
        jdbcTemplate.update(
                "update schedule set content = ?, updated_date = ? where id = ?",
                content,
                java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()), //일정 수정 후 수정일 갱신
                id
        );
        
        //수정된 일정 정보 반환
        return jdbcTemplate.queryForObject(
                "select * from schedule where id = ?",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getString("content"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("updated_date").toLocalDateTime()
                ),
                id
        );
    }
    
    //일정 삭제
    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                "delete from schedule where id = ?",
                id //해당 id의 일정 삭제
        );
    }
}

