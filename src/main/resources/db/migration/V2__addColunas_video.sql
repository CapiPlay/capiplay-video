ALTER TABLE `db_capiplay_video`.`video`
    ADD COLUMN `visualizacoes` BIGINT NULL AFTER `categoria_id`,
    ADD COLUMN `curtidas` BIGINT NULL AFTER `visualizacoes`;