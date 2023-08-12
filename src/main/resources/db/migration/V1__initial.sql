CREATE DATABASE  IF NOT EXISTS `db_capiplay_video` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_capiplay_video`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: db_capiplay_video
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `engajamento`
--

DROP TABLE IF EXISTS `engajamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `engajamento` (
  `uuid` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engajamento`
--

LOCK TABLES `engajamento` WRITE;
/*!40000 ALTER TABLE `engajamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `engajamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filtro`
--

DROP TABLE IF EXISTS `filtro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filtro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `filtro_ano` bit(1) NOT NULL,
  `filtro_dia` bit(1) NOT NULL,
  `filtro_entre5e20min` bit(1) NOT NULL,
  `filtro_mais_de20min` bit(1) NOT NULL,
  `filtro_menos_de5min` bit(1) NOT NULL,
  `filtro_mes` bit(1) NOT NULL,
  `filtro_semana` bit(1) NOT NULL,
  `filtro_shorts` bit(1) NOT NULL,
  `filtro_video` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filtro`
--

LOCK TABLES `filtro` WRITE;
/*!40000 ALTER TABLE `filtro` DISABLE KEYS */;
/*!40000 ALTER TABLE `filtro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesquisa`
--

DROP TABLE IF EXISTS `pesquisa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesquisa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_insercao` datetime(6) DEFAULT NULL,
  `pesquisa` varchar(255) DEFAULT NULL,
  `usuario_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbnrcx5bprfyck98dmrnqgsejf` (`usuario_id`),
  CONSTRAINT `FKbnrcx5bprfyck98dmrnqgsejf` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesquisa`
--

LOCK TABLES `pesquisa` WRITE;
/*!40000 ALTER TABLE `pesquisa` DISABLE KEYS */;
/*!40000 ALTER TABLE `pesquisa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `tag` varchar(255) NOT NULL,
  PRIMARY KEY (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `uuid` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_visualiza_video`
--

DROP TABLE IF EXISTS `usuario_visualiza_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_visualiza_video` (
  `uuid` bigint NOT NULL AUTO_INCREMENT,
  `data_visualizacao` datetime(6) DEFAULT NULL,
  `usuario_uuid` varchar(255) DEFAULT NULL,
  `video_uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKkydtnkh0lr1o0214tcie2lmux` (`usuario_uuid`),
  KEY `FKhkgmdd3p7b4iib55iuakyoed7` (`video_uuid`),
  CONSTRAINT `FKhkgmdd3p7b4iib55iuakyoed7` FOREIGN KEY (`video_uuid`) REFERENCES `video` (`uuid`),
  CONSTRAINT `FKkydtnkh0lr1o0214tcie2lmux` FOREIGN KEY (`usuario_uuid`) REFERENCES `usuario` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_visualiza_video`
--

LOCK TABLES `usuario_visualiza_video` WRITE;
/*!40000 ALTER TABLE `usuario_visualiza_video` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_visualiza_video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `uuid` varchar(255) NOT NULL,
  `caminho` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `curtidas` bigint DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `duracao` bigint DEFAULT NULL,
  `inativo` bit(1) DEFAULT NULL,
  `pontuacao` double DEFAULT NULL,
  `publicacao` date DEFAULT NULL,
  `restrito` bit(1) DEFAULT NULL,
  `shorts` bit(1) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `visualizacoes` bigint DEFAULT NULL,
  `usuario_uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKgcg2cwhi2ojuqb8f13sx27wgn` (`usuario_uuid`),
  CONSTRAINT `FKgcg2cwhi2ojuqb8f13sx27wgn` FOREIGN KEY (`usuario_uuid`) REFERENCES `usuario` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_tags`
--

DROP TABLE IF EXISTS `video_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_tags` (
  `video_uuid` varchar(255) NOT NULL,
  `tags_tag` varchar(255) NOT NULL,
  KEY `FKmu0k3ya1nhvduacu248vs4ale` (`tags_tag`),
  KEY `FKiyuh0lvjqy1lux52jccqfweap` (`video_uuid`),
  CONSTRAINT `FKiyuh0lvjqy1lux52jccqfweap` FOREIGN KEY (`video_uuid`) REFERENCES `video` (`uuid`),
  CONSTRAINT `FKmu0k3ya1nhvduacu248vs4ale` FOREIGN KEY (`tags_tag`) REFERENCES `tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_tags`
--

LOCK TABLES `video_tags` WRITE;
/*!40000 ALTER TABLE `video_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `video_tags` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-11 19:20:21
