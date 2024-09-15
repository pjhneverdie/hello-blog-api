-- 변경 사항 --
-- 1. thumb_url 컬럼 추가
-- 2. 컬럼 순서 변경

ALTER TABLE post
    ADD COLUMN thumb_url VARCHAR(255) DEFAULT NULL;

ALTER TABLE post
    MODIFY COLUMN thumb_url VARCHAR (255) DEFAULT NULL AFTER content;

