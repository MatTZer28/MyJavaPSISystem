-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2021 at 01:05 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 7.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javaclassproject2021`
--

-- --------------------------------------------------------

--
-- Table structure for table `combine`
--

CREATE TABLE `combine` (
  `CombineName` varchar(10) NOT NULL,
  `CombinePrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `combine`
--

INSERT INTO `combine` (`CombineName`, `CombinePrice`) VALUES
('漢堡', 50);

-- --------------------------------------------------------

--
-- Table structure for table `combineproduct`
--

CREATE TABLE `combineproduct` (
  `CombineName` varchar(10) NOT NULL,
  `ProductID` varchar(10) NOT NULL,
  `Amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `combineproduct`
--

INSERT INTO `combineproduct` (`CombineName`, `ProductID`, `Amount`) VALUES
('漢堡', 'MA', 5);

-- --------------------------------------------------------

--
-- Table structure for table `companyinformation`
--

CREATE TABLE `companyinformation` (
  `ID` int(10) UNSIGNED NOT NULL,
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
  `WebSiteAddress` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `companyinformation`
--

INSERT INTO `companyinformation` (`ID`, `CompanyID`, `ChineseName`, `EnglishName`, `Manager`, `UniNumber`, `PhoneNumber`, `FaxNumber`, `ChineseAddress`, `EnglishAddress`, `EmailAddress`, `WebSiteAddress`) VALUES
(1, '', '', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `customerinformation`
--

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
  `Comment` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customerinformation`
--

INSERT INTO `customerinformation` (`CustomerID`, `UniNumber`, `Name`, `InvoiceHeader`, `Manager`, `PhoneNumber`, `PhoneExtention`, `FaxNumber`, `CellPhoneNumber`, `Address`, `EmailAddress`, `WebSiteAddress`, `Comment`) VALUES
('001', '', '張博皓', '', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `empolyeeinformation`
--

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
  `AvatarImage` mediumblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ProductID` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Specification` varchar(20) DEFAULT NULL,
  `Type` varchar(20) DEFAULT NULL,
  `Unit` varchar(5) DEFAULT NULL,
  `Total` int(10) UNSIGNED DEFAULT NULL,
  `Cost` int(10) UNSIGNED DEFAULT NULL,
  `SellPrice` int(10) UNSIGNED DEFAULT NULL,
  `SafeAmount` int(10) UNSIGNED DEFAULT NULL,
  `VendorName` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ProductID`, `Name`, `Specification`, `Type`, `Unit`, `Total`, `Cost`, `SellPrice`, `SafeAmount`, `VendorName`) VALUES
('MA', '紅茶', '', '', '', 0, 0, 0, 0, 'Matt');

-- --------------------------------------------------------

--
-- Table structure for table `productstoreinwarehouse`
--

CREATE TABLE `productstoreinwarehouse` (
  `ProductID` varchar(10) NOT NULL,
  `WarehouseID` varchar(10) NOT NULL,
  `Amount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `productstoreinwarehouse`
--

INSERT INTO `productstoreinwarehouse` (`ProductID`, `WarehouseID`, `Amount`) VALUES
('MA', '1', 0);

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `PurchaseID` varchar(10) NOT NULL,
  `VendorID` varchar(10) DEFAULT NULL,
  `ProductID` varchar(10) NOT NULL,
  `WarehouseID` varchar(10) NOT NULL,
  `CostPrice` int(11) DEFAULT NULL,
  `Amount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`PurchaseID`, `VendorID`, `ProductID`, `WarehouseID`, `CostPrice`, `Amount`) VALUES
('P001', 'V1', 'MA', '1', 0, 50);

-- --------------------------------------------------------

--
-- Table structure for table `sell`
--

CREATE TABLE `sell` (
  `SellID` varchar(10) NOT NULL,
  `CustomerID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sell`
--

INSERT INTO `sell` (`SellID`, `CustomerID`) VALUES
('S001', '001');

-- --------------------------------------------------------

--
-- Table structure for table `sellcombine`
--

CREATE TABLE `sellcombine` (
  `SellID` varchar(10) NOT NULL,
  `CombineName` varchar(10) NOT NULL,
  `Amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sellcombine`
--

INSERT INTO `sellcombine` (`SellID`, `CombineName`, `Amount`) VALUES
('S001', '漢堡', 10);

-- --------------------------------------------------------

--
-- Table structure for table `vendorinformation`
--

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
  `Comment` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendorinformation`
--

INSERT INTO `vendorinformation` (`VendorID`, `UniNumber`, `Name`, `InvoiceHeader`, `Manager`, `PhoneNumber`, `PhoneExtention`, `FaxNumber`, `Address`, `EmailAddress`, `WebSiteAddress`, `Comment`) VALUES
('V1', '', 'Matt', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `warehouse`
--

CREATE TABLE `warehouse` (
  `WarehouseID` varchar(10) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `FaxNumber` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `warehouse`
--

INSERT INTO `warehouse` (`WarehouseID`, `Name`, `Address`, `PhoneNumber`, `FaxNumber`) VALUES
('1', 'A', '', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `combine`
--
ALTER TABLE `combine`
  ADD PRIMARY KEY (`CombineName`);

--
-- Indexes for table `combineproduct`
--
ALTER TABLE `combineproduct`
  ADD PRIMARY KEY (`CombineName`,`ProductID`);

--
-- Indexes for table `companyinformation`
--
ALTER TABLE `companyinformation`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `customerinformation`
--
ALTER TABLE `customerinformation`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Indexes for table `empolyeeinformation`
--
ALTER TABLE `empolyeeinformation`
  ADD PRIMARY KEY (`EmpolyeeID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `productstoreinwarehouse`
--
ALTER TABLE `productstoreinwarehouse`
  ADD PRIMARY KEY (`ProductID`,`WarehouseID`),
  ADD KEY `WarehouseID_idx` (`WarehouseID`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`PurchaseID`,`WarehouseID`,`ProductID`),
  ADD KEY `WarehouseID_idx` (`WarehouseID`),
  ADD KEY `ProductID_idx` (`ProductID`);

--
-- Indexes for table `sell`
--
ALTER TABLE `sell`
  ADD PRIMARY KEY (`SellID`);

--
-- Indexes for table `sellcombine`
--
ALTER TABLE `sellcombine`
  ADD PRIMARY KEY (`SellID`,`CombineName`) USING BTREE;

--
-- Indexes for table `vendorinformation`
--
ALTER TABLE `vendorinformation`
  ADD PRIMARY KEY (`VendorID`);

--
-- Indexes for table `warehouse`
--
ALTER TABLE `warehouse`
  ADD PRIMARY KEY (`WarehouseID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `combineproduct`
--
ALTER TABLE `combineproduct`
  ADD CONSTRAINT `NameConstraint` FOREIGN KEY (`CombineName`) REFERENCES `combine` (`CombineName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `productstoreinwarehouse`
--
ALTER TABLE `productstoreinwarehouse`
  ADD CONSTRAINT `ProductID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `WarehouseID` FOREIGN KEY (`WarehouseID`) REFERENCES `warehouse` (`WarehouseID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `ProductID1` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `WarehouseID1` FOREIGN KEY (`WarehouseID`) REFERENCES `warehouse` (`WarehouseID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sellcombine`
--
ALTER TABLE `sellcombine`
  ADD CONSTRAINT `SellIDConstraint` FOREIGN KEY (`SellID`) REFERENCES `sell` (`SellID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
