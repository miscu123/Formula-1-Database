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
-- Table structure for table `echipa`
--

DROP TABLE IF EXISTS `echipa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `echipa` (
  `EchipaID` int NOT NULL AUTO_INCREMENT,
  `NumeEchipa` varchar(100) NOT NULL,
  `Sediu` varchar(100) DEFAULT NULL,
  `AnInfiintare` year DEFAULT NULL,
  PRIMARY KEY (`EchipaID`),
  UNIQUE KEY `NumeEchipa` (`NumeEchipa`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `echipa`
--

LOCK TABLES `echipa` WRITE;
/*!40000 ALTER TABLE `echipa` DISABLE KEYS */;
INSERT INTO `echipa` VALUES (1,'Red Bull Racing','Milton Keynes, UK',2005),(2,'Mercedes','Brackley, UK',2010),(3,'Ferrari','Maranello, Italia',1950),(4,'McLaren','Woking, UK',1966),(5,'Aston Martin','Silverstone, UK',2021),(6,'Alpine','Enstone, UK',2021),(7,'Williams','Grove, UK',1977),(8,'Visa Cash App RB','Faenza, Italia',2006),(9,'Kick Sauber','Hinwil, Elveția',1993),(10,'Haas','Kannapolis, SUA',2016);
/*!40000 ALTER TABLE `echipa` ENABLE KEYS */;
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
