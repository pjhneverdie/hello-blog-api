-- like 테이블 생성
CREATE TABLE `like`(
    id         INT AUTO_INCREMENT,
    member_id  INT NOT NULL,
    post_id    INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (post_id) REFERENCES post (id),
    UNIQUE KEY unique_like (member_id, post_id)
);
