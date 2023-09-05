ALTER TABLE video ADD FULLTEXT INDEX video_title (titulo);
ALTER TABLE video ADD FULLTEXT INDEX video_category (categoria);
ALTER TABLE tag ADD FULLTEXT INDEX tag_tags (tag);