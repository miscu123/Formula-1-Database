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
-- Table structure for table `cursa`
--

DROP TABLE IF EXISTS `cursa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cursa` (
  `CursaID` int NOT NULL AUTO_INCREMENT,
  `CircuitID` int DEFAULT NULL,
  `DataCursa` date DEFAULT NULL,
  `NumeCursa` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CursaID`),
  KEY `CircuitID` (`CircuitID`),
  CONSTRAINT `cursa_ibfk_1` FOREIGN KEY (`CircuitID`) REFERENCES `circuit` (`CircuitID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursa`
--

LOCK TABLES `cursa` WRITE;
/*!40000 ALTER TABLE `cursa` DISABLE KEYS */;
INSERT INTO `cursa` VALUES (1,1,'2024-03-02','Grand Prix Bahrain'),(2,2,'2024-03-09','Grand Prix Arabia Saudită'),(3,3,'2024-03-24','Grand Prix Australia'),(4,4,'2024-04-07','Grand Prix Japonia'),(5,5,'2024-04-21','Grand Prix China'),(6,6,'2024-05-05','Grand Prix Miami'),(7,7,'2024-05-19','Grand Prix Emilia-Romagna'),(8,8,'2024-05-26','Grand Prix Monaco'),(9,9,'2024-06-23','Grand Prix Spania'),(10,10,'2024-06-30','Grand Prix Austria');
/*!40000 ALTER TABLE `cursa` ENABLE KEYS */;
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
