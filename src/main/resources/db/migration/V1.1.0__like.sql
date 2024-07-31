ALTER TABLE `like` DROP FOREIGN KEY fk_like_member_id;
ALTER TABLE `like` DROP FOREIGN KEY fk_like_comment_id;
ALTER TABLE `like` DROP FOREIGN KEY fk_like_post_id;

ALTER TABLE `like` DROP INDEX unique_like;

ALTER TABLE `like` MODIFY COLUMN post_id INT DEFAULT NULL;

ALTER TABLE `like` ADD CONSTRAINT fk_like_member_id FOREIGN KEY (member_id) REFERENCES `member` (id) ON DELETE SET NULL;
ALTER TABLE `like` ADD CONSTRAINT fk_like_post_id FOREIGN KEY (post_id) REFERENCES `post` (id) ON DELETE CASCADE;
ALTER TABLE `like` ADD CONSTRAINT fk_like_comment_id FOREIGN KEY (comment_id) REFERENCES `comment` (id) ON DELETE CASCADE;

ALTER TABLE `like` ADD CONSTRAINT unique_post_like UNIQUE (member_id, post_id);
ALTER TABLE `like` ADD CONSTRAINT unique_comment_like UNIQUE (member_id, comment_id);