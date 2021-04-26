-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: javaclassproject2021
-- ------------------------------------------------------
-- Server version	8.0.23

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-26 21:45:34
