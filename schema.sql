-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: javaclassproject2021
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `companyinformation`
--

DROP TABLE IF EXISTS `companyinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companyinformation` (
  `ID` int unsigned NOT NULL,
  `CompanyID` varchar(20) DEFAULT NULL,
  `ChineseName` varchar(20) DEFAULT NULL,
  `EnglishName` varchar(50) DEFAULT NULL,
  `Manager` varchar(20) DEFAULT NULL,
  `UniNumber` varchar(8) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `FaxNumber` varchar(20) DEFAULT NULL,
  `ChineseAddress` varchar(50) DEFAULT NULL,
  `EnglishAddress` varchar(100) DEFAULT NULL,
  `EmailAddress` varchar(50) DEFAULT NULL,
  `WebSiteAddress` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companyinformation`
--

LOCK TABLES `companyinformation` WRITE;
/*!40000 ALTER TABLE `companyinformation` DISABLE KEYS */;
INSERT INTO `companyinformation` VALUES (1,'','','','','','','','','','','');
/*!40000 ALTER TABLE `companyinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerinformation`
--

DROP TABLE IF EXISTS `customerinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerinformation` (
  `CustomerID` varchar(10) NOT NULL,
  `UniNumber` varchar(8) DEFAULT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `InvoiceHeader` varchar(20) DEFAULT NULL,
  `Manager` varchar(20) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `PhoneExtention` varchar(10) DEFAULT NULL,
  `FaxNumber` varchar(20) DEFAULT NULL,
  `CellPhoneNumber` varchar(20) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `EmailAddress` varchar(50) DEFAULT NULL,
  `WebSiteAddress` varchar(200) DEFAULT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerinformation`
--

LOCK TABLES `customerinformation` WRITE;
/*!40000 ALTER TABLE `customerinformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empolyeeinformation`
--

DROP TABLE IF EXISTS `empolyeeinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empolyeeinformation` (
  `EmpolyeeID` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `JobTitle` varchar(10) DEFAULT NULL,
  `IdentityNumber` varchar(10) DEFAULT NULL,
  `Brithday` varchar(10) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `CellPhoneNumber` varchar(20) DEFAULT NULL,
  `EmailAddress` varchar(50) DEFAULT NULL,
  `WebSiteAddress` varchar(200) DEFAULT NULL,
  `Education` varchar(50) DEFAULT NULL,
  `FirstDayOnDuty` varchar(10) DEFAULT NULL,
  `ResignDay` varchar(10) DEFAULT NULL,
  `WorkPlace` varchar(20) DEFAULT NULL,
  `ResidenceAddress` varchar(50) DEFAULT NULL,
  `MailingAddress` varchar(50) DEFAULT NULL,
  `bankAccount` varchar(20) DEFAULT NULL,
  `bankName` varchar(10) DEFAULT NULL,
  `EmerContactPerson` varchar(20) DEFAULT NULL,
  `EmerRelation` varchar(10) DEFAULT NULL,
  `EmerPhone` varchar(20) DEFAULT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  `AvatarImage` mediumblob,
  PRIMARY KEY (`EmpolyeeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empolyeeinformation`
--

LOCK TABLES `empolyeeinformation` WRITE;
/*!40000 ALTER TABLE `empolyeeinformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `empolyeeinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ProductID` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Specification` varchar(20) DEFAULT NULL,
  `Type` varchar(20) DEFAULT NULL,
  `Unit` varchar(5) DEFAULT NULL,
  `Total` int unsigned DEFAULT NULL,
  `Cost` int unsigned DEFAULT NULL,
  `SellPrice` int unsigned DEFAULT NULL,
  `SafeAmount` int unsigned DEFAULT NULL,
  `VendorName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productstoreinwarehouse`
--

DROP TABLE IF EXISTS `productstoreinwarehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productstoreinwarehouse` (
  `ProductID` varchar(10) DEFAULT NULL,
  `WarehouseID` varchar(10) DEFAULT NULL,
  `Amount` int DEFAULT NULL,
  KEY `WarehouseID_idx` (`WarehouseID`) /*!80000 INVISIBLE */,
  KEY `ProductID` (`ProductID`),
  CONSTRAINT `ProductID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `WarehouseID` FOREIGN KEY (`WarehouseID`) REFERENCES `warehouse` (`WarehouseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productstoreinwarehouse`
--

LOCK TABLES `productstoreinwarehouse` WRITE;
/*!40000 ALTER TABLE `productstoreinwarehouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `productstoreinwarehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendorinformation`
--

DROP TABLE IF EXISTS `vendorinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendorinformation` (
  `VendorID` varchar(10) NOT NULL,
  `UniNumber` varchar(8) DEFAULT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `InvoiceHeader` varchar(20) DEFAULT NULL,
  `Manager` varchar(20) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `PhoneExtention` varchar(10) DEFAULT NULL,
  `FaxNumber` varchar(20) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `EmailAddress` varchar(50) DEFAULT NULL,
  `WebSiteAddress` varchar(200) DEFAULT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`VendorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendorinformation`
--

LOCK TABLES `vendorinformation` WRITE;
/*!40000 ALTER TABLE `vendorinformation` DISABLE KEYS */;
INSERT INTO `vendorinformation` VALUES ('123','00000000','aaa','','','','','','','','',''),('321','00000000','bbb','','','','','','','','','');
/*!40000 ALTER TABLE `vendorinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `WarehouseID` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `FaxNumber` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`WarehouseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-06 18:52:36
