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
-- Table structure for table `pilot`
--

DROP TABLE IF EXISTS `pilot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pilot` (
  `PilotID` int NOT NULL AUTO_INCREMENT,
  `Nume` varchar(50) DEFAULT NULL,
  `Prenume` varchar(50) DEFAULT NULL,
  `Nationalitate` varchar(50) DEFAULT NULL,
  `DataNasterii` date DEFAULT NULL,
  `EchipaID` int DEFAULT NULL,
  PRIMARY KEY (`PilotID`),
  KEY `EchipaID` (`EchipaID`),
  CONSTRAINT `pilot_ibfk_1` FOREIGN KEY (`EchipaID`) REFERENCES `echipa` (`EchipaID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pilot`
--

LOCK TABLES `pilot` WRITE;
/*!40000 ALTER TABLE `pilot` DISABLE KEYS */;
INSERT INTO `pilot` VALUES (1,'Verstappen','Max','Olanda','1997-09-30',1),(2,'Perez','Sergio','Mexic','1990-01-26',1),(3,'Hamilton','Lewis','Marea Britanie','1985-01-07',2),(4,'Russell','George','Marea Britanie','1998-02-15',2),(5,'Leclerc','Charles','Monaco','1997-10-16',3),(6,'Sainz','Carlos','Spania','1994-09-01',3),(7,'Norris','Lando','Marea Britanie','1999-11-13',4),(8,'Piastri','Oscar','Australia','2001-04-06',4),(9,'Alonso','Fernando','Spania','1981-07-29',5),(10,'Stroll','Lance','Canada','1998-10-29',5),(11,'Ocon','Esteban','Franța','1996-09-17',6),(12,'Gasly','Pierre','Franța','1996-02-07',6),(13,'Albon','Alexander','Thailanda','1996-03-23',7),(14,'Sargeant','Logan','SUA','2000-12-31',7),(15,'Tsunoda','Yuki','Japonia','2000-05-11',8),(16,'Ricciardo','Daniel','Australia','1989-07-01',8),(17,'Bottas','Valtteri','Finlanda','1989-08-28',9),(18,'Zhou','Guanyu','China','1999-05-30',9),(19,'Magnussen','Kevin','Danemarca','1992-10-05',10),(20,'Hulkenberg','Nico','Germania','1987-08-19',10);
/*!40000 ALTER TABLE `pilot` ENABLE KEYS */;
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
