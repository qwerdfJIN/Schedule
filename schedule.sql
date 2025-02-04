CREATE DATABASE schedule;
USE schedule;

CREATE TABLE schedule (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          writer VARCHAR(10) NOT NULL,
                          password VARCHAR(8) NOT NULL,
                          content TEXT NOT NULL,
                          created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


