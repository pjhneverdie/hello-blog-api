-- post 테이블 생성
CREATE TABLE post(
    id          INT AUTO_INCREMENT,
    title       VARCHAR(50) NOT NULL,
    content     TEXT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fixed_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    category_id INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE RESTRICT
);

CREATE TRIGGER update_fixed_at
    BEFORE UPDATE ON post
    FOR EACH ROW
BEGIN
    SET NEW.fixed_at = CURRENT_TIMESTAMP;
END;