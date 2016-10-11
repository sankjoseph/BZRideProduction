-- phpMyAdmin SQL Dump
-- version 4.0.10.14
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Oct 06, 2016 at 01:11 AM
-- Server version: 5.6.30-log
-- PHP Version: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bzride5_bzRide`
--

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_driverbankdetails`
--

CREATE TABLE IF NOT EXISTS `bztbl_driverbankdetails` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `userid` varchar(10) NOT NULL,
  `AccountType` varchar(20) NOT NULL,
  `BankName` varchar(50) NOT NULL,
  `AccountToken` varchar(50) NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `bztbl_driverbankdetails`
--

INSERT INTO `bztbl_driverbankdetails` (`Id`, `userid`, `AccountType`, `BankName`, `AccountToken`, `CreatedByDate`, `LastModifiedDate`) VALUES
(3, '3', 'individual', 'Bank of America', 'btok_9JMq858He8y3bL', '2016-10-03 20:59:49', '2016-10-03 21:00:53');

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_driverinsurancedetails`
--

CREATE TABLE IF NOT EXISTS `bztbl_driverinsurancedetails` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `DriverId` varchar(10) NOT NULL,
  `InsCompany` varchar(50) NOT NULL,
  `InsPolicyNumber` varchar(50) NOT NULL,
  `InsValidFrom` datetime NOT NULL,
  `InsExpDate` datetime NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `bztbl_driverinsurancedetails`
--

INSERT INTO `bztbl_driverinsurancedetails` (`Id`, `DriverId`, `InsCompany`, `InsPolicyNumber`, `InsValidFrom`, `InsExpDate`, `CreatedByDate`, `LastModifiedDate`) VALUES
(3, '3', 'Liberty Mutual', 'A0012e4567i', '2015-11-01 00:00:00', '2016-11-01 00:00:00', '2016-10-03 20:59:49', '2016-10-03 20:59:49');

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_driverlicensedetails`
--

CREATE TABLE IF NOT EXISTS `bztbl_driverlicensedetails` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `driverid` int(10) NOT NULL,
  `licNumber` varchar(15) NOT NULL,
  `licStateIssued` varchar(20) NOT NULL,
  `licDateIssued` datetime NOT NULL,
  `licExpDate` datetime NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `bztbl_driverlicensedetails`
--

INSERT INTO `bztbl_driverlicensedetails` (`Id`, `driverid`, `licNumber`, `licStateIssued`, `licDateIssued`, `licExpDate`, `CreatedByDate`, `LastModifiedDate`) VALUES
(3, 3, 'F3457616', 'CA', '2016-04-04 00:00:00', '2021-05-14 00:00:00', '2016-10-03 20:59:49', '2016-10-03 20:59:49');

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_drivers`
--

CREATE TABLE IF NOT EXISTS `bztbl_drivers` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) NOT NULL,
  `MiddleName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `Address1` varchar(500) NOT NULL,
  `Address2` varchar(500) NOT NULL,
  `City` varchar(500) NOT NULL,
  `State` varchar(500) NOT NULL,
  `Zip` varchar(50) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `DOB` datetime NOT NULL,
  `SSN` varchar(20) NOT NULL,
  `DeviceId` varchar(200) NOT NULL,
  `DeviceToken` varchar(200) NOT NULL,
  `DeviceType` varchar(20) NOT NULL,
  `isLicenseAccepted` tinyint(1) NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `status` varchar(2) NOT NULL,
  `currentlat` float DEFAULT NULL,
  `currentlong` float DEFAULT NULL,
  `CardType` varchar(20) NOT NULL,
  `CardProvider` varchar(20) NOT NULL,
  `cardBillingAddress1` varchar(500) NOT NULL,
  `cardBillingAddress2` varchar(500) NOT NULL,
  `cardBillingCity` varchar(500) NOT NULL,
  `cardBillingState` varchar(500) NOT NULL,
  `cardBillingZip` varchar(50) NOT NULL,
  `CardToken` varchar(200) NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `bztbl_drivers`
--

INSERT INTO `bztbl_drivers` (`Id`, `FirstName`, `MiddleName`, `LastName`, `Email`, `Password`, `Address1`, `Address2`, `City`, `State`, `Zip`, `Phone`, `DOB`, `SSN`, `DeviceId`, `DeviceToken`, `DeviceType`, `isLicenseAccepted`, `isActive`, `status`, `currentlat`, `currentlong`, `CardType`, `CardProvider`, `cardBillingAddress1`, `cardBillingAddress2`, `cardBillingCity`, `cardBillingState`, `cardBillingZip`, `CardToken`, `CreatedByDate`, `LastModifiedDate`) VALUES
(3, 'Shenjin', '', 'Thomas', 'shenjinthomas@yahoo.com', 'Alan2008', '1418 Skibbereen Way', '', 'Rocklin', 'CA', '95765', '9164263900', '1977-05-10 00:00:00', '000000000', '738e824729e2ec4d', 'fLPuxDgTPUE:APA91bGUUiFSEutQIeIldrUhtvtiXlDyC2yVZUocBBCeaEl96Y9yHLVLlZs0iLjp4wBF2NSohKdhPvI8sdps76nTlUT0DMpsPZmm1du8EZlkQtrTkmPbfstDLC3230mJxjYH7DyK6dtZ', 'A', 1, 1, 'A', 38.8181, -121.279, 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', '2016-10-03 20:59:49', '2016-10-03 20:59:49');

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_drivervehicledetails`
--

CREATE TABLE IF NOT EXISTS `bztbl_drivervehicledetails` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `Driverid` varchar(10) NOT NULL,
  `VehicleModel` varchar(10) NOT NULL,
  `VMake` varchar(20) NOT NULL,
  `VColor` varchar(20) NOT NULL,
  `VYear` varchar(5) NOT NULL,
  `VehicleNumber` varchar(20) NOT NULL,
  `VRegState` varchar(20) NOT NULL,
  `VDateRegistered` datetime NOT NULL,
  `VExpDate` datetime NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `bztbl_drivervehicledetails`
--

INSERT INTO `bztbl_drivervehicledetails` (`Id`, `Driverid`, `VehicleModel`, `VMake`, `VColor`, `VYear`, `VehicleNumber`, `VRegState`, `VDateRegistered`, `VExpDate`, `CreatedByDate`, `LastModifiedDate`) VALUES
(3, '3', 'Q7', 'Audi', 'Silver', '2009', '6TKH159', 'CA', '2015-12-04 00:00:00', '2016-12-04 00:00:00', '2016-10-03 20:59:49', '2016-10-03 20:59:49');

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_reportedproblems`
--

CREATE TABLE IF NOT EXISTS `bztbl_reportedproblems` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `UserId` varchar(10) NOT NULL,
  `ReportTitle` varchar(200) NOT NULL,
  `ReportDescription` varchar(500) NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_riderequests`
--

CREATE TABLE IF NOT EXISTS `bztbl_riderequests` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `RequestType` varchar(2) NOT NULL,
  `RequestorId` varchar(10) NOT NULL,
  `DriverId` varchar(10) NOT NULL,
  `StartLocation` varchar(200) NOT NULL,
  `EndLocation` varchar(200) NOT NULL,
  `StartLat` float DEFAULT NULL,
  `StartLong` float DEFAULT NULL,
  `EndLat` float DEFAULT NULL,
  `EndLong` float DEFAULT NULL,
  `ActualStartLat` float DEFAULT NULL,
  `ActualStartLong` float DEFAULT NULL,
  `ActualEndLat` float DEFAULT NULL,
  `ActualEndLong` float DEFAULT NULL,
  `ChargeDistance` float DEFAULT NULL,
  `ChargeTime` float DEFAULT NULL,
  `FinalCharge` float DEFAULT NULL,
  `FaretoCompany` float DEFAULT NULL,
  `FareCommission` float DEFAULT NULL,
  `FarePayableToDriver` float DEFAULT NULL,
  `Status` varchar(2) NOT NULL,
  `RideDateTime` datetime NOT NULL,
  `ActualRideDateTimeStart` datetime NOT NULL,
  `ActualRideDateTimeEnd` datetime NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

-- --------------------------------------------------------

--
-- Table structure for table `bztbl_riders`
--

CREATE TABLE IF NOT EXISTS `bztbl_riders` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) NOT NULL,
  `MiddleName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `Address1` varchar(500) NOT NULL,
  `Address2` varchar(500) NOT NULL,
  `City` varchar(500) NOT NULL,
  `State` varchar(500) NOT NULL,
  `Zip` varchar(50) NOT NULL,
  `Phone` varchar(15) NOT NULL,
  `DeviceId` varchar(200) NOT NULL,
  `DeviceToken` varchar(200) NOT NULL,
  `DeviceType` varchar(20) NOT NULL,
  `isLicenseAccepted` tinyint(1) NOT NULL,
  `isActive` tinyint(1) NOT NULL,
  `status` varchar(2) NOT NULL,
  `CardType` varchar(20) NOT NULL,
  `CardProvider` varchar(20) NOT NULL,
  `cardBillingAddress1` varchar(500) NOT NULL,
  `cardBillingAddress2` varchar(500) NOT NULL,
  `cardBillingCity` varchar(500) NOT NULL,
  `cardBillingState` varchar(500) NOT NULL,
  `cardBillingZip` varchar(50) NOT NULL,
  `CardToken` varchar(200) NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  `LastModifiedDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT AUTO_INCREMENT=13 ;

--
-- Dumping data for table `bztbl_riders`
--

INSERT INTO `bztbl_riders` (`Id`, `FirstName`, `MiddleName`, `LastName`, `Email`, `Password`, `Address1`, `Address2`, `City`, `State`, `Zip`, `Phone`, `DeviceId`, `DeviceToken`, `DeviceType`, `isLicenseAccepted`, `isActive`, `status`, `CardType`, `CardProvider`, `cardBillingAddress1`, `cardBillingAddress2`, `cardBillingCity`, `cardBillingState`, `cardBillingZip`, `CardToken`, `CreatedByDate`, `LastModifiedDate`) VALUES
(5, 'Nobby', '', 'Joseph', 'shince_thomas@yahoo.com', 'Ashin2011', '1418 Skibbereen Way', '', 'Rocklin', 'CA', '95765', '9164269000', 'ae96700de935d90a', 'eHySmla-2I0:APA91bEvq100SSz3SM9ebYieqtM8Hnofo-EDUQm8CeNnNZ1bh_jADFXpj6-mqoqbVugH82S573akpx6MeIQiQ9wyDofeVLBgqR3mHCPv3wDTqiuRJJu00JVWGmd8SWw2WbTVOxFJbA3M', 'A', 1, 1, 'N', 'C', 'M', '1418 Skibbereen Way', '', 'Rocklin', 'CA', '95765', 'tok_190k96DdKBsqiExAsTbEs8MC', '2016-10-03 21:03:26', '2016-10-03 21:03:26');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
