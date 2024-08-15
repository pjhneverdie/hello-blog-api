-- 변경 사항 --
-- 1. 서로 다른 카테고리 내에서는 카테고리 이름 중복 허용
-- 2. 생성일 컬럼 추가

ALTER TABLE category
DROP INDEX unique_name;

ALTER TABLE category
    ADD UNIQUE KEY unique_name (name, parent_id);

ALTER TABLE category
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;