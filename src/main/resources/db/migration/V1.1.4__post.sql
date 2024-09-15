-- 변경 사항 --
-- 1. category_id 컬럼 순서 변경

-- 외래 키 제약조건 삭제
ALTER TABLE post DROP FOREIGN KEY post_ibfk_1;

-- category_id 삭제
ALTER TABLE post DROP COLUMN category_id;

-- category_id 순서 변경해서 다시 생성
ALTER TABLE post ADD COLUMN category_id INT NOT NULL AFTER thumb_url;

-- 외래 키 제약조건 다시 생성
ALTER TABLE post ADD CONSTRAINT post_ibfk_1 FOREIGN KEY (category_id) REFERENCES category(id);