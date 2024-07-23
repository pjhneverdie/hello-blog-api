-- comment 테이블 생성
CREATE TABLE `comment`(
    id         INT AUTO_INCREMENT,
    content    TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    member_id  INT,
    post_id    INT NOT NULL,
    parent_id  INT DEFAULT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE SET NULL,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES `comment` (id) ON DELETE CASCADE
);