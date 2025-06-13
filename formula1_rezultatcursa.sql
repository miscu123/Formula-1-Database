-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: formula1
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `rezultatcursa`
--

DROP TABLE IF EXISTS `rezultatcursa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rezultatcursa` (
  `RezultatID` int NOT NULL AUTO_INCREMENT,
  `CursaID` int DEFAULT NULL,
  `PilotID` int DEFAULT NULL,
  `PozitieFinala` int DEFAULT NULL,
  `TimpFinal` time DEFAULT NULL,
  `Puncte` int DEFAULT NULL,
  PRIMARY KEY (`RezultatID`),
  KEY `CursaID` (`CursaID`),
  KEY `PilotID` (`PilotID`),
  CONSTRAINT `rezultatcursa_ibfk_1` FOREIGN KEY (`CursaID`) REFERENCES `cursa` (`CursaID`),
  CONSTRAINT `rezultatcursa_ibfk_2` FOREIGN KEY (`PilotID`) REFERENCES `pilot` (`PilotID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezultatcursa`
--

LOCK TABLES `rezultatcursa` WRITE;
/*!40000 ALTER TABLE `rezultatcursa` DISABLE KEYS */;
INSERT INTO `rezultatcursa` VALUES (1,1,1,1,'01:31:24',25),(2,1,2,2,'01:31:59',18),(3,1,3,3,'01:32:11',15),(4,1,4,4,'01:32:32',12),(5,1,5,5,'01:32:55',10),(6,1,6,6,'01:33:11',8),(7,1,7,7,'01:33:31',6),(8,1,8,8,'01:33:52',4),(9,1,9,9,'01:34:13',2),(10,1,10,10,'01:34:35',1),(11,2,2,1,'01:30:30',25),(12,2,3,2,'01:30:50',18),(13,2,4,3,'01:31:05',15),(14,2,5,4,'01:31:20',12),(15,2,6,5,'01:31:40',10),(16,2,7,6,'01:32:00',8),(17,2,8,7,'01:32:15',6),(18,2,9,8,'01:32:35',4),(19,2,10,9,'01:32:50',2),(20,2,1,10,'01:33:10',1),(21,3,3,1,'01:29:50',25),(22,3,4,2,'01:30:05',18),(23,3,5,3,'01:30:20',15),(24,3,6,4,'01:30:40',12),(25,3,7,5,'01:30:55',10),(26,3,8,6,'01:31:10',8),(27,3,9,7,'01:31:30',6),(28,3,10,8,'01:31:45',4),(29,3,1,9,'01:32:00',2),(30,3,2,10,'01:32:20',1),(31,4,4,1,'01:31:00',25),(32,4,5,2,'01:31:15',18),(33,4,6,3,'01:31:30',15),(34,4,7,4,'01:31:50',12),(35,4,8,5,'01:32:05',10),(36,4,9,6,'01:32:20',8),(37,4,10,7,'01:32:40',6),(38,4,1,8,'01:32:55',4),(39,4,2,9,'01:33:10',2),(40,4,3,10,'01:33:30',1),(41,5,5,1,'01:30:40',25),(42,5,6,2,'01:30:55',18),(43,5,7,3,'01:31:10',15),(44,5,8,4,'01:31:30',12),(45,5,9,5,'01:31:45',10),(46,5,10,6,'01:32:00',8),(47,5,1,7,'01:32:15',6),(48,5,2,8,'01:32:35',4),(49,5,3,9,'01:32:50',2),(50,5,4,10,'01:33:05',1),(51,6,6,1,'01:31:10',25),(52,6,7,2,'01:31:25',18),(53,6,8,3,'01:31:40',15),(54,6,9,4,'01:32:00',12),(55,6,10,5,'01:32:15',10),(56,6,1,6,'01:32:30',8),(57,6,2,7,'01:32:50',6),(58,6,3,8,'01:33:05',4),(59,6,4,9,'01:33:20',2),(60,6,5,10,'01:33:35',1),(61,7,7,1,'01:30:55',25),(62,7,8,2,'01:31:10',18),(63,7,9,3,'01:31:25',15),(64,7,10,4,'01:31:45',12),(65,7,1,5,'01:32:00',10),(66,7,2,6,'01:32:15',8),(67,7,3,7,'01:32:35',6),(68,7,4,8,'01:32:50',4),(69,7,5,9,'01:33:05',2),(70,7,6,10,'01:33:20',1),(71,8,8,1,'01:31:05',25),(72,8,9,2,'01:31:20',18),(73,8,10,3,'01:31:35',15),(74,8,1,4,'01:31:55',12),(75,8,2,5,'01:32:10',10),(76,8,3,6,'01:32:25',8),(77,8,4,7,'01:32:45',6),(78,8,5,8,'01:33:00',4),(79,8,6,9,'01:33:15',2),(80,8,7,10,'01:33:30',1),(81,9,9,1,'01:31:35',25),(82,9,10,2,'01:31:50',18),(83,9,1,3,'01:32:05',15),(84,9,2,4,'01:32:25',12),(85,9,3,5,'01:32:40',10),(86,9,4,6,'01:32:55',8),(87,9,5,7,'01:33:15',6),(88,9,6,8,'01:33:30',4),(89,9,7,9,'01:33:45',2),(90,9,8,10,'01:34:00',1),(91,10,10,1,'01:31:20',25),(92,10,1,2,'01:31:35',18),(93,10,2,3,'01:31:50',15),(94,10,3,4,'01:32:10',12),(95,10,4,5,'01:32:25',10),(96,10,5,6,'01:32:40',8),(97,10,6,7,'01:33:00',6),(98,10,7,8,'01:33:15',4),(99,10,8,9,'01:33:30',2),(100,10,9,10,'01:33:45',1);
/*!40000 ALTER TABLE `rezultatcursa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-13 20:56:10
