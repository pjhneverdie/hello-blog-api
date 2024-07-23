-- 변경 사항 --
-- 1. 댓글 좋아요
-- 2. 회원탈퇴 시에도 좋아요는 계속 남아 있도록 스키마를 변경

-- comment_id 컬럼 추가
ALTER TABLE `like`
ADD COLUMN comment_id INT;

-- member_id를 nullable로 변경
ALTER TABLE `like`
MODIFY COLUMN member_id INT;

-- 기존 외래 키 제약 조건 삭제
ALTER TABLE `like`
DROP FOREIGN KEY like_ibfk_1,
DROP FOREIGN KEY like_ibfk_2;

-- 기존 UNIQUE 제약 조건 삭제
ALTER TABLE `like`
DROP INDEX unique_like;

-- 새로운 외래 키 제약 조건 추가
ALTER TABLE `like`
ADD CONSTRAINT fk_like_member_id
FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE SET NULL,
ADD CONSTRAINT fk_like_post_id
FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
ADD CONSTRAINT fk_like_comment_id
FOREIGN KEY (comment_id) REFERENCES comment(id) ON DELETE CASCADE;

-- 새로운 UNIQUE 제약 조건 추가
ALTER TABLE `like`
ADD UNIQUE KEY unique_like (member_id, post_id, comment_id);