-- category 테이블 생성
CREATE TABLE category(
    id        INT AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    parent_id INT DEFAULT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE RESTRICT
);