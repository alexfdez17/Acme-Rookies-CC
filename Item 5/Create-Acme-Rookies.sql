-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-rookies
-- ------------------------------------------------------
-- Server version	5.5.29

start transaction;

drop database if exists `Acme-Restaurante`;

create database `Acme-Restaurante`;
use `Acme-Restaurante`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%'identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete	on `Acme-Restaurante`.* 
	to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter,create temporary tables, lock tables, create view, create routine,alter routine, execute, trigger, show view on `Acme-Restaurante`.* 
	to 'acme-manager'@'%';

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `credit_card` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fl7pq8veyyxgdk1s4awu0c7mo` (`credit_card`),
  UNIQUE KEY `UK_i7xei45auwq1f6vu25985riuh` (`user_account`),
  KEY `UK_t1reg6b6liecmk2s1k901l4da` (`id`,`spammer`),
  CONSTRAINT `FK_i7xei45auwq1f6vu25985riuh` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_fl7pq8veyyxgdk1s4awu0c7mo` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `credit_card` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b2e562x98pje1n9vu0deulcev` (`credit_card`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  KEY `administratorUK_t1reg6b6liecmk2s1k901l4da` (`id`,`spammer`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_b2e562x98pje1n9vu0deulcev` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (140,0,'address','email@email.com','admin','123456789','http://photo.com','\0','very good admin',21,139,124);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `answer_code` varchar(255) DEFAULT NULL,
  `answer_explanation` varchar(255) DEFAULT NULL,
  `creation_moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submitted_moment` datetime DEFAULT NULL,
  `position_problem` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_61arf79rfw4ahdt6n06mb5ghq` (`position_problem`),
  KEY `FK_dq1om37bx4hgli24rbkjo2n7` (`rookie`),
  CONSTRAINT `FK_dq1om37bx4hgli24rbkjo2n7` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_61arf79rfw4ahdt6n06mb5ghq` FOREIGN KEY (`position_problem`) REFERENCES `position_problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (208,0,'https://github.com','explanation','2019-06-03 00:00:00','PENDING',NULL,192,144),(209,0,'https://github.com','explanation','2019-06-03 00:00:00','SUBMITTED','2020-05-03 00:00:00',192,146),(210,0,'https://github.com','explanation','2019-06-03 00:00:00','SUBMITTED','2020-05-03 00:00:00',192,150),(211,0,'https://github.com','explanation','2019-06-03 00:00:00','REJECTED','2020-05-03 00:00:00',193,144),(212,0,'https://github.com','explanation','2019-06-03 00:00:00','ACCEPTED','2020-05-03 00:00:00',194,148),(213,0,'https://github.com','explanation','2019-06-03 00:00:00','PENDING',NULL,196,144),(214,0,'https://github.com','explanation','2019-06-03 00:00:00','REJECTED','2020-05-03 00:00:00',196,150),(215,0,'https://github.com','explanation','2019-06-03 00:00:00','ACCEPTED','2020-05-03 00:00:00',197,144),(216,0,'https://github.com','explanation','2019-06-03 00:00:00','SUBMITTED','2020-05-03 00:00:00',198,146),(217,0,'https://github.com','explanation','2019-06-03 00:00:00','PENDING',NULL,199,146),(218,0,'https://github.com','explanation','2019-06-03 00:00:00','REJECTED','2020-05-03 00:00:00',200,148),(219,0,'https://github.com','explanation','2019-06-03 00:00:00','ACCEPTED','2020-05-03 00:00:00',201,150),(220,0,'https://github.com','explanation','2019-06-03 00:00:00','SUBMITTED','2020-05-03 00:00:00',202,144),(221,0,'https://github.com','explanation','2019-06-03 00:00:00','REJECTED','2020-05-03 00:00:00',203,146),(222,0,'https://github.com','explanation','2019-06-03 00:00:00','SUBMITTED','2020-05-03 00:00:00',203,146),(223,0,'https://github.com','explanation','2019-06-03 00:00:00','PENDING',NULL,204,144),(224,0,'https://github.com','explanation','2019-06-03 00:00:00','ACCEPTED','2020-05-03 00:00:00',205,150),(225,0,'https://github.com','explanation','2019-06-03 00:00:00','PENDING',NULL,206,148),(226,0,'https://github.com','explanation','2019-06-03 00:00:00','PENDING',NULL,207,144);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `draft_mode` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `score` int(11) NOT NULL,
  `auditor` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3m6p53wfvy7kcl4f4fvphkh9h` (`auditor`),
  KEY `FK_bumsxo4hc038y25pbfsinc38u` (`position`),
  CONSTRAINT `FK_bumsxo4hc038y25pbfsinc38u` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_3m6p53wfvy7kcl4f4fvphkh9h` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (173,0,'description1','\0','2019-01-01 10:00:00',9,168,159),(174,0,'description2','','2019-01-01 10:00:00',7,168,159),(175,0,'description3','\0','2019-01-01 12:00:00',8,168,162);
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `credit_card` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k5yihs4tvrnhe8rndei6ypc8w` (`credit_card`),
  UNIQUE KEY `UK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  KEY `auditorUK_t1reg6b6liecmk2s1k901l4da` (`id`,`spammer`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_k5yihs4tvrnhe8rndei6ypc8w` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (168,0,'address','email@email.com','auditor1','123456789','http://photo.com','\0','very good auditor',21,167,133),(170,0,'address','email@email.com','auditor2','123456789','http://photo.com','\0','very good auditor',21,169,134),(172,0,'address','email@email.com','auditor3','123456789','http://photo.com','\0','very good auditor',21,171,135);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `credit_card` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  `audit_score` double DEFAULT NULL,
  `comercial_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cp2qc9fdm9995xdhrrd06n86c` (`credit_card`),
  UNIQUE KEY `UK_pno7oguspp7fxv0y2twgplt0s` (`user_account`),
  KEY `companyUK_t1reg6b6liecmk2s1k901l4da` (`id`,`spammer`),
  CONSTRAINT `FK_pno7oguspp7fxv0y2twgplt0s` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_cp2qc9fdm9995xdhrrd06n86c` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (152,0,'address','email@email.com','company1','123456789','http://photo.com','\0','very good company',21,151,129,1,'Valve'),(154,0,'address','email@email.com','company2','123456789','http://photo.com','\0','very good company',21,153,130,1,'RITO JUEGOS'),(156,0,'address','email@email.com','admin','123456789','http://photo.com','\0','very good company',21,155,131,1,'Punteria juegos'),(158,0,'address','email@email.com','admin','123456789','http://photo.com','\0','very good company',21,157,132,1,'AIM Games');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cvv` int(11) NOT NULL,
  `expiration_month` int(11) NOT NULL,
  `expiration_year` int(11) NOT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES (139,0,153,2,22,'Admin','VISA','4043492660454935'),(143,0,123,10,22,'rookie1','VISA','4043492660454935'),(145,0,123,10,22,'rookie2','VISA','4043492660454935'),(147,0,123,10,22,'rookie3','VISA','4043492660454935'),(149,0,123,10,22,'rookie4','VISA','4043492660454935'),(151,0,123,10,22,'company1','VISA','4043492660454935'),(153,0,123,10,22,'company2','VISA','4043492660454935'),(155,0,123,10,22,'company3','VISA','4043492660454935'),(157,0,123,10,22,'company4','VISA','4043492660454935'),(167,0,123,10,22,'auditor1','VISA','4043492660454935'),(169,0,123,10,22,'auditor2','VISA','4043492660454935'),(171,0,123,10,22,'auditor3','VISA','4043492660454935'),(180,0,123,10,22,'provider1','VISA','4043492660454935'),(182,0,123,10,22,'provider2','VISA','4043492660454935'),(184,0,123,10,22,'provider3','VISA','4043492660454935'),(189,0,123,10,22,'provider1','VISA','4043492660454935');
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula`
--

DROP TABLE IF EXISTS `curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_copy` bit(1) DEFAULT NULL,
  `miscellaneous_data` int(11) DEFAULT NULL,
  `personal_data` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_67ossaqcrfgcegesi91m20iik` (`personal_data`),
  KEY `UK_n42685um9yhbe5flg03nwn6lx` (`is_copy`),
  KEY `FK_6y23mggh2cyj1tgg0jh9v415k` (`miscellaneous_data`),
  KEY `FK_lq4kfcvf5vufwsng4apko2wd` (`rookie`),
  CONSTRAINT `FK_lq4kfcvf5vufwsng4apko2wd` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_67ossaqcrfgcegesi91m20iik` FOREIGN KEY (`personal_data`) REFERENCES `personal_data` (`id`),
  CONSTRAINT `FK_6y23mggh2cyj1tgg0jh9v415k` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula`
--

LOCK TABLES `curricula` WRITE;
/*!40000 ALTER TABLE `curricula` DISABLE KEYS */;
INSERT INTO `curricula` VALUES (235,0,'\0',227,231,144),(236,0,'\0',NULL,232,144);
/*!40000 ALTER TABLE `curricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_education_data`
--

DROP TABLE IF EXISTS `curricula_education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_education_data` (
  `curricula` int(11) NOT NULL,
  `education_data` int(11) NOT NULL,
  UNIQUE KEY `UK_r552kg3pwybsy7igk77depn9l` (`education_data`),
  KEY `FK_a133bnrmd36opa9yi2dvx0rly` (`curricula`),
  CONSTRAINT `FK_a133bnrmd36opa9yi2dvx0rly` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_r552kg3pwybsy7igk77depn9l` FOREIGN KEY (`education_data`) REFERENCES `education_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_education_data`
--

LOCK TABLES `curricula_education_data` WRITE;
/*!40000 ALTER TABLE `curricula_education_data` DISABLE KEYS */;
INSERT INTO `curricula_education_data` VALUES (235,233),(235,234);
/*!40000 ALTER TABLE `curricula_education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_positions_data`
--

DROP TABLE IF EXISTS `curricula_positions_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_positions_data` (
  `curricula` int(11) NOT NULL,
  `positions_data` int(11) NOT NULL,
  UNIQUE KEY `UK_6kc8elssjem0rcj4h0bjxdhpb` (`positions_data`),
  KEY `FK_9ajv9qkalgsqliq5h8cl59ydc` (`curricula`),
  CONSTRAINT `FK_9ajv9qkalgsqliq5h8cl59ydc` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_6kc8elssjem0rcj4h0bjxdhpb` FOREIGN KEY (`positions_data`) REFERENCES `position_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_positions_data`
--

LOCK TABLES `curricula_positions_data` WRITE;
/*!40000 ALTER TABLE `curricula_positions_data` DISABLE KEYS */;
INSERT INTO `curricula_positions_data` VALUES (235,229),(235,230);
/*!40000 ALTER TABLE `curricula_positions_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_data`
--

DROP TABLE IF EXISTS `education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_data`
--

LOCK TABLES `education_data` WRITE;
/*!40000 ALTER TABLE `education_data` DISABLE KEYS */;
INSERT INTO `education_data` VALUES (233,0,'degree','2011-12-12 00:00:00','institution','8','2010-12-12 00:00:00'),(234,0,'degree','2011-12-12 00:00:00','institution','9','2010-12-12 00:00:00');
/*!40000 ALTER TABLE `education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `finder_date` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `maximum_dead_line` date DEFAULT NULL,
  `minimum_salary` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_71xsphsa6mnvd5ul0ow71r6we` (`rookie`),
  CONSTRAINT `FK_71xsphsa6mnvd5ul0ow71r6we` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_positions`
--

DROP TABLE IF EXISTS `finder_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_positions` (
  `finder` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  KEY `FK_3d46gil0nks2dhgg7cyhv2m39` (`positions`),
  KEY `FK_l0b3qg4nly59oeqhe8ig5yssc` (`finder`),
  CONSTRAINT `FK_l0b3qg4nly59oeqhe8ig5yssc` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_3d46gil0nks2dhgg7cyhv2m39` FOREIGN KEY (`positions`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_positions`
--

LOCK TABLES `finder_positions` WRITE;
/*!40000 ALTER TABLE `finder_positions` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_sponsorships`
--

DROP TABLE IF EXISTS `finder_sponsorships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_sponsorships` (
  `finder` int(11) NOT NULL,
  `sponsorships` int(11) NOT NULL,
  UNIQUE KEY `UK_meg0rxiy7fn6juprgamska55j` (`sponsorships`),
  KEY `FK_p8aqlyb95blph7hjnynb1vifv` (`finder`),
  CONSTRAINT `FK_p8aqlyb95blph7hjnynb1vifv` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_meg0rxiy7fn6juprgamska55j` FOREIGN KEY (`sponsorships`) REFERENCES `sponsorship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_sponsorships`
--

LOCK TABLES `finder_sponsorships` WRITE;
/*!40000 ALTER TABLE `finder_sponsorships` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_sponsorships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_isojc9iaj7goju6s26847atbn` (`provider`),
  CONSTRAINT `FK_isojc9iaj7goju6s26847atbn` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (186,0,'description','Item1',181),(187,0,'description','Item2',183),(188,0,'description','Item3',185);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_links`
--

DROP TABLE IF EXISTS `item_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_links` (
  `item` int(11) NOT NULL,
  `links` varchar(255) DEFAULT NULL,
  KEY `FK_g63x29gj6aimcehw00xht6eni` (`item`),
  CONSTRAINT `FK_g63x29gj6aimcehw00xht6eni` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_links`
--

LOCK TABLES `item_links` WRITE;
/*!40000 ALTER TABLE `item_links` DISABLE KEYS */;
INSERT INTO `item_links` VALUES (186,'https://www.link.com'),(187,'https://www.link.com'),(188,'https://www.link.com');
/*!40000 ALTER TABLE `item_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_pictures`
--

DROP TABLE IF EXISTS `item_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_pictures` (
  `item` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_aur62dcmq5mod3fcwl099dmxi` (`item`),
  CONSTRAINT `FK_aur62dcmq5mod3fcwl099dmxi` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_pictures`
--

LOCK TABLES `item_pictures` WRITE;
/*!40000 ALTER TABLE `item_pictures` DISABLE KEYS */;
INSERT INTO `item_pictures` VALUES (186,'https://www.picture.com'),(187,'https://www.picture.com'),(188,'https://www.picture.com');
/*!40000 ALTER TABLE `item_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `spam` bit(1) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `recipient` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (243,0,'ha sido seleccionado','2019-01-01 00:00:00','','you\'ve been selected','SELECTED',152,154),(244,0,'It\'s a test','2019-01-01 00:00:00','\0','Hey','',152,154);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data`
--

DROP TABLE IF EXISTS `miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `free_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data`
--

LOCK TABLES `miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data` DISABLE KEYS */;
INSERT INTO `miscellaneous_data` VALUES (227,0,'Testttt'),(228,0,'Testttt');
/*!40000 ALTER TABLE `miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data_attachments`
--

DROP TABLE IF EXISTS `miscellaneous_data_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data_attachments` (
  `miscellaneous_data` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_e1oadh6x6vsemmnrwp19vocmr` (`miscellaneous_data`),
  CONSTRAINT `FK_e1oadh6x6vsemmnrwp19vocmr` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data_attachments`
--

LOCK TABLES `miscellaneous_data_attachments` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_data_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `administrator` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n3k72ybgq8q5x8j9w3acfc20q` (`administrator`),
  CONSTRAINT `FK_n3k72ybgq8q5x8j9w3acfc20q` FOREIGN KEY (`administrator`) REFERENCES `administrator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (141,0,'Our hacking platform has been hacked by rookies registered as rookies on our hacking platform','2019-01-04 10:00:00','We\'ve been hacked','hacked',140),(142,0,'u got april fool\'d','2019-02-04 10:00:00','jk lol','hacked',140);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nueva_entidadxxx`
--

DROP TABLE IF EXISTS `nueva_entidadxxx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nueva_entidadxxx` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `draft_mode` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `audit` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_k9049sfjx5siqbeg5eex2p8ds` (`audit`),
  CONSTRAINT `FK_k9049sfjx5siqbeg5eex2p8ds` FOREIGN KEY (`audit`) REFERENCES `audit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nueva_entidadxxx`
--

LOCK TABLES `nueva_entidadxxx` WRITE;
/*!40000 ALTER TABLE `nueva_entidadxxx` DISABLE KEYS */;
INSERT INTO `nueva_entidadxxx` VALUES (176,0,'TEST Body 1','\0','2019-06-14 15:00:00','https://pictureTEST.jpg','190614 TEST',173),(177,0,'TEST Body 2','','2019-03-24 10:00:00','https://pictureTEST.jpg','190324 TEST',173),(178,0,'TEST Body 3','\0','2019-05-10 12:00:00','https://pictureTEST.jpg','190510 TEST',175),(179,0,'TEST Body 4','\0','2019-03-24 10:00:00','https://pictureTEST.jpg','190324 TEST',175);
/*!40000 ALTER TABLE `nueva_entidadxxx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_data`
--

DROP TABLE IF EXISTS `personal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `github_profile` varchar(255) DEFAULT NULL,
  `linkedin_profile` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_data`
--

LOCK TABLES `personal_data` WRITE;
/*!40000 ALTER TABLE `personal_data` DISABLE KEYS */;
INSERT INTO `personal_data` VALUES (231,0,'https://www.github.com/profile','https://www.linkedin.com/profile','middleName','name','676565648','statement','surname'),(232,0,'https://www.github.com/profile','https://www.linkedin.com/profile','middleName','name','676565648','statement','surname');
/*!40000 ALTER TABLE `personal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `dead_line` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `profile_required` varchar(255) DEFAULT NULL,
  `salary_offered` int(11) NOT NULL,
  `skills_required` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `technologies_required` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_15390c4j2aeju6ha0iwvi6mc5` (`ticker`),
  KEY `UK_bn25ihwv1m6wxomd9w2d18ja7` (`ticker`,`title`,`description`,`profile_required`,`skills_required`,`technologies_required`),
  KEY `UK_7qlfn4nye234rrm4w83nms1g8` (`company`),
  KEY `UK_dhepmeivv1gg72iv7vlqg7e98` (`status`),
  KEY `UK_78luk2mb4s2j2bbtsrkgnvppm` (`dead_line`),
  KEY `UK_nroi0wb84kv1287eao8y2b5tp` (`salary_offered`),
  CONSTRAINT `FK_7qlfn4nye234rrm4w83nms1g8` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (159,0,'2019-12-12','description','profile',666,'skills','final','tecnologies','VALV-0000','Security engineer',152),(160,0,'2019-12-12','description','profile',666,'skills','draft','tecnologies','VALV-0001','Pentesting',152),(161,0,'2019-12-12','description','profile',666,'skills','draft','tecnologies','VALV-0002','Network hacking',152),(162,0,'2019-12-12','description','profile',666,'skills','final','tecnologies','RITO-0000','DNS Spoofing',154),(163,0,'2019-12-12','description','profile',666,'skills','final','tecnologies','RITO-0001','Reverse engineering',154),(164,0,'2019-12-12','description','profile',666,'skills','final','tecnologies','PUNT-0000','Botnets',156),(165,0,'2019-12-12','description','profile',666,'skills','final','tecnologies','PUNT-0001','SQL Injection',156),(166,0,'2019-12-12','description','profile',666,'skills','final','tecnologies','AIMG-0000','Blockchain',158);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_data`
--

DROP TABLE IF EXISTS `position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_data`
--

LOCK TABLES `position_data` WRITE;
/*!40000 ALTER TABLE `position_data` DISABLE KEYS */;
INSERT INTO `position_data` VALUES (229,0,'description','2011-12-12 00:00:00','2010-12-12 00:00:00','title'),(230,0,'description','2011-12-12 00:00:00','2010-12-12 00:00:00','title');
/*!40000 ALTER TABLE `position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_problem`
--

DROP TABLE IF EXISTS `position_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `draft_mode` bit(1) NOT NULL,
  `hint` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hcxthe7h3u7owghygsm1i2q9j` (`position`),
  CONSTRAINT `FK_hcxthe7h3u7owghygsm1i2q9j` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_problem`
--

LOCK TABLES `position_problem` WRITE;
/*!40000 ALTER TABLE `position_problem` DISABLE KEYS */;
INSERT INTO `position_problem` VALUES (192,0,'\0','hint','statement','title',159),(193,0,'\0','hint','statement','title',159),(194,0,'\0','hint','statement','title',159),(195,0,'','hint','statement','title',160),(196,0,'\0','hint','statement','title',161),(197,0,'','hint','statement','title',162),(198,0,'','hint','statement','title',162),(199,0,'','hint','statement','title',163),(200,0,'','hint','statement','title',163),(201,0,'','hint','statement','title',164),(202,0,'','hint','statement','title',164),(203,0,'','hint','statement','title',165),(204,0,'','hint','statement','title',165),(205,0,'','hint','statement','title',165),(206,0,'','hint','statement','title',166),(207,0,'','hint','statement','title',166);
/*!40000 ALTER TABLE `position_problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_problem_attachments`
--

DROP TABLE IF EXISTS `position_problem_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_problem_attachments` (
  `position_problem` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_ho5r23j6k82vpj5nwk0l9n2g0` (`position_problem`),
  CONSTRAINT `FK_ho5r23j6k82vpj5nwk0l9n2g0` FOREIGN KEY (`position_problem`) REFERENCES `position_problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_problem_attachments`
--

LOCK TABLES `position_problem_attachments` WRITE;
/*!40000 ALTER TABLE `position_problem_attachments` DISABLE KEYS */;
INSERT INTO `position_problem_attachments` VALUES (192,'https://www.attachment1.com'),(192,'https://www.attachment2.com'),(193,'https://www.attachment1.com'),(193,'https://www.attachment2.com'),(194,'https://www.attachment1.com'),(194,'https://www.attachment2.com'),(195,'https://www.attachment1.com'),(195,'https://www.attachment2.com'),(196,'https://www.attachment1.com'),(196,'https://www.attachment2.com'),(197,'https://www.attachment1.com'),(197,'https://www.attachment2.com'),(198,'https://www.attachment1.com'),(198,'https://www.attachment2.com'),(199,'https://www.attachment1.com'),(199,'https://www.attachment2.com'),(200,'https://www.attachment1.com'),(200,'https://www.attachment2.com'),(201,'https://www.attachment1.com'),(201,'https://www.attachment2.com'),(202,'https://www.attachment1.com'),(202,'https://www.attachment2.com'),(203,'https://www.attachment1.com'),(203,'https://www.attachment2.com'),(204,'https://www.attachment1.com'),(204,'https://www.attachment2.com'),(205,'https://www.attachment1.com'),(205,'https://www.attachment2.com'),(206,'https://www.attachment1.com'),(206,'https://www.attachment2.com'),(207,'https://www.attachment1.com'),(207,'https://www.attachment2.com');
/*!40000 ALTER TABLE `position_problem_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `credit_card` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  `provider_make` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8tfs0v3dygkxkfyijig9gv9mj` (`credit_card`),
  UNIQUE KEY `UK_pj40m1p8m3lcs2fkdl1n3f3lq` (`user_account`),
  KEY `providerUK_t1reg6b6liecmk2s1k901l4da` (`id`,`spammer`),
  CONSTRAINT `FK_pj40m1p8m3lcs2fkdl1n3f3lq` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_8tfs0v3dygkxkfyijig9gv9mj` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
INSERT INTO `provider` VALUES (181,0,'address','email@email.com','provider1','123456789','http://photo.com','\0','very good provider',21,180,136,'ProviderMake1'),(183,0,'address','email@email.com','provider2','123456789','http://photo.com','\0','very good provider',21,182,137,'ProviderMake2'),(185,0,'address','email@email.com','provider3','123456789','http://photo.com','\0','very good provider',21,184,138,'ProviderMake3');
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie`
--

DROP TABLE IF EXISTS `rookie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `credit_card` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4srgnn4au9gkktapbpr1ip6be` (`credit_card`),
  UNIQUE KEY `UK_2n8nv9qsl5pnxhnosngfkkm4i` (`user_account`),
  KEY `rookieUK_t1reg6b6liecmk2s1k901l4da` (`id`,`spammer`),
  CONSTRAINT `FK_2n8nv9qsl5pnxhnosngfkkm4i` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_4srgnn4au9gkktapbpr1ip6be` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie`
--

LOCK TABLES `rookie` WRITE;
/*!40000 ALTER TABLE `rookie` DISABLE KEYS */;
INSERT INTO `rookie` VALUES (144,0,'address','email@email.com','rookie1','123456789','http://photo.com','\0','very good rookie',21,143,125),(146,0,'address','email@email.com','rookie2','123456789','http://photo.com','\0','very good rookie',21,145,126),(148,0,'address','email@email.com','admin','123456789','http://photo.com','\0','very good rookie',21,147,127),(150,0,'address','email@email.com','admin','123456789','http://photo.com','\0','very good rookie',21,149,128);
/*!40000 ALTER TABLE `rookie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `profile_link` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
INSERT INTO `social_profile` VALUES (237,0,'Rookie1','http://twitter.com/users/demouser','Twitter',144),(238,0,'Rookie2','http://instagram.com/users/demouser','Instagram',146),(239,0,'Rookie3','http://facebook.com/users/demouser','Facebook',148),(240,0,'Company1','http://twitter.com/users/demouser','Twitter',152),(241,0,'Company2','http://instagram.com/users/demouser','Instagram',154),(242,0,'Company3','http://facebook.com/users/demouser','Facebook',156);
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `charged_amount` double NOT NULL,
  `target_page` varchar(255) DEFAULT NULL,
  `creditcard` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_363s93kxlesooltaj11slbnao` (`creditcard`),
  KEY `FK_jnrjojfnyyaie1n4jhsdxjbig` (`position`),
  KEY `FK_dwk5ymekhnr143u957f7gtns6` (`provider`),
  CONSTRAINT `FK_dwk5ymekhnr143u957f7gtns6` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`),
  CONSTRAINT `FK_363s93kxlesooltaj11slbnao` FOREIGN KEY (`creditcard`) REFERENCES `credit_card` (`id`),
  CONSTRAINT `FK_jnrjojfnyyaie1n4jhsdxjbig` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (190,0,'https://www.wireshark.org/assets/theme-2015/images/sflogo.png',0,'https://www.targetpage.com',180,159,181),(191,0,'https://static.dezeen.com/uploads/2017/01/mozilla-finalises-new-logo-design-graphics_dezeen_hero-852x479.jpg',0,'https://www.targetpage.com',189,159,181);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `charge` double NOT NULL,
  `finder_cache_hours` double DEFAULT NULL,
  `finder_max_results` int(11) NOT NULL,
  `name_changed` bit(1) DEFAULT NULL,
  `phone_prefix` varchar(255) DEFAULT NULL,
  `spanish_welcome_message` varchar(255) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `vat` int(11) NOT NULL,
  `welcome_message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config`
--

LOCK TABLES `system_config` WRITE;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
INSERT INTO `system_config` VALUES (123,0,'https://i.imgur.com/cD0Z1LR.png',0.01,1,10,'\0','+34','¡Bienvenidos a Acme Rookies! ¡Somos el mercado de trabajo favorito de los profesionales de las TICs!','Acme Rookies',21,'Welcome to Acme Rookies! We\'re IT rookie\'s favourite job marketplace!');
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_config_spam_words`
--

DROP TABLE IF EXISTS `system_config_spam_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config_spam_words` (
  `system_config` int(11) NOT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  KEY `FK_77ifj6lixsuyq1d5ivfjkf97w` (`system_config`),
  CONSTRAINT `FK_77ifj6lixsuyq1d5ivfjkf97w` FOREIGN KEY (`system_config`) REFERENCES `system_config` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config_spam_words`
--

LOCK TABLES `system_config_spam_words` WRITE;
/*!40000 ALTER TABLE `system_config_spam_words` DISABLE KEYS */;
INSERT INTO `system_config_spam_words` VALUES (123,'sex'),(123,'viagra'),(123,'cialis'),(123,'one million'),(123,'you\'ve been selected'),(123,'Nigeria'),(123,'sexo'),(123,'un millon'),(123,'has sido seleccionado');
/*!40000 ALTER TABLE `system_config_spam_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (124,0,'','21232f297a57a5a743894a0e4a801fc3','admin'),(125,0,'','9701eb1802a4c63f51e1501512fbdd30','rookie1'),(126,0,'','124be4fa2a59341a1d7e965ac49b2923','rookie2'),(127,0,'','b723fa2fd1c2dc65d166df3e7f83e329','rookie3'),(128,0,'','78dfa0fab9872b58c8affee48f0c4252','rookie4'),(129,0,'','df655f976f7c9d3263815bd981225cd9','company1'),(130,0,'','d196a28097115067fefd73d25b0c0be8','company2'),(131,0,'','e828ae3339b8d80b3902c1564578804e','company3'),(132,0,'','856dfd9045541fa727ef6ad392835eb0','company4'),(133,0,'','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(134,0,'','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(135,0,'','fc2073dbe4f65dfd71e46602f8e6a5f3','auditor3'),(136,0,'','cdb82d56473901641525fbbd1d5dab56','provider1'),(137,0,'','ebfc815ee2cc6a16225105bb7b3e1e53','provider2'),(138,0,'','a575bf1b9ca7d068cef7bbc8fa7f43e1','provider3');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (124,'ADMIN'),(125,'ROOKIE'),(126,'ROOKIE'),(127,'ROOKIE'),(128,'ROOKIE'),(129,'COMPANY'),(130,'COMPANY'),(131,'COMPANY'),(132,'COMPANY'),(133,'AUDITOR'),(134,'AUDITOR'),(135,'AUDITOR'),(136,'PROVIDER'),(137,'PROVIDER'),(138,'PROVIDER');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-17 11:19:48
