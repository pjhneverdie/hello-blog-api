-- 기존 테이블 삭제
DROP TABLE member;

-- 새로운 테이블 생성
CREATE TABLE member(
    id INT AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
);
