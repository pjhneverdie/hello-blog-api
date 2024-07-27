-- 변경 사항 --
-- 1. 이름 중복 방지

ALTER TABLE category
ADD UNIQUE KEY unique_name (name);