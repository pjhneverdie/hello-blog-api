-- 변경 사항 --
-- 1. 썸네일 컬럼 추가
-- 2. 카테고리 계층화

ALTER TABLE category
    ADD COLUMN thumb_url VARCHAR(255) DEFAULT NULL;

ALTER TABLE category
    ADD COLUMN parent_id INT DEFAULT NULL;

ALTER TABLE category
    ADD CONSTRAINT fk_parent_category FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE CASCADE;
