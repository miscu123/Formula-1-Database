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
-- Table structure for table `masina`
--

DROP TABLE IF EXISTS `masina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `masina` (
  `MasinaID` int NOT NULL AUTO_INCREMENT,
  `EchipaID` int DEFAULT NULL,
  `Model` varchar(50) DEFAULT NULL,
  `An` year DEFAULT NULL,
  `Motor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MasinaID`),
  KEY `EchipaID` (`EchipaID`),
  CONSTRAINT `masina_ibfk_1` FOREIGN KEY (`EchipaID`) REFERENCES `echipa` (`EchipaID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masina`
--

LOCK TABLES `masina` WRITE;
/*!40000 ALTER TABLE `masina` DISABLE KEYS */;
INSERT INTO `masina` VALUES (11,6,'A524',2024,'Renault'),(12,5,'AMR24',2024,'Mercedes'),(13,3,'SF-24',2024,'Ferrari'),(14,10,'VF-24',2024,'Ferrari'),(15,9,'C44',2024,'Ferrari'),(16,4,'MCL38',2024,'Mercedes'),(17,2,'W15',2024,'Mercedes'),(18,8,'VCARB 01',2024,'Honda RBPT'),(19,1,'RB20',2024,'Honda RBPT'),(20,7,'FW46',2024,'Mercedes');
/*!40000 ALTER TABLE `masina` ENABLE KEYS */;
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
