-- 변경 사항 --
-- 1. 이메일 중복 방지

ALTER TABLE member
    ADD UNIQUE KEY unique_email (email);