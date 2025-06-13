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
-- Table structure for table `circuit`
--

DROP TABLE IF EXISTS `circuit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `circuit` (
  `CircuitID` int NOT NULL AUTO_INCREMENT,
  `Nume` varchar(100) DEFAULT NULL,
  `Tara` varchar(50) DEFAULT NULL,
  `LungimeKM` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`CircuitID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `circuit`
--

LOCK TABLES `circuit` WRITE;
/*!40000 ALTER TABLE `circuit` DISABLE KEYS */;
INSERT INTO `circuit` VALUES (1,'Bahrain International Circuit','Bahrein',5.41),(2,'Jeddah Street Circuit','Arabia Saudită',6.17),(3,'Albert Park Circuit','Australia',5.28),(4,'Suzuka Circuit','Japonia',5.81),(5,'Shanghai International Circuit','China',5.45),(6,'Miami International Autodrome','SUA',5.41),(7,'Imola Circuit','Italia',4.91),(8,'Circuit de Monaco','Monaco',3.34),(9,'Circuit de Barcelona-Catalunya','Spania',4.66),(10,'Red Bull Ring','Austria',4.32);
/*!40000 ALTER TABLE `circuit` ENABLE KEYS */;
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
