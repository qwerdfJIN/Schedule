CREATE DATABASE IF NOT EXISTS schedule_db;
USE schedule_db;

CREATE TABLE SCHEDULE (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          WRITER VARCHAR(10) NOT NULL,
                          PASSWORD VARCHAR(8) NOT NULL,
                          TITLE VARCHAR(20) NOT NULL,
                          CONTENT TEXT NOT NULL,
                          CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
                          UPDATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO SCHEDULE (WRITER, PASSWORD, TITLE, CONTENT)
VALUES
    ('박인간', '1234', '전시회', '반고흐 전시회 방문'),
    ('박사람', '1234', '피부과', '눈썹 문신 리터치');