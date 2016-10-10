-- phpMyAdmin SQL Dump
-- version 3.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 16, 2016 at 10:44 AM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

// add 3 fileds in driver and rider
// added faretocompany

CREATE TABLE IF NOT EXISTS `bztbl_test` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `DOB` datetime NOT NULL,
   PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bzridedb`
--

alter table add DOB
ALTER TABLE bztbl_drivers
ADD DOB date NOT NULL

FinalCharge bztbl_riderequests added

  `FareCommission` FLOAT,bztbl_riderequests added
  `FarePayableToDriver` FLOAT,bztbl_riderequests added
  

ALTER TABLE bztbl_riderequests
ADD FinalCharge FLOAT

ALTER TABLE bztbl_riderequests
ADD FareCommission FLOAT

ALTER TABLE bztbl_riderequests
ADD FarePayableToDriver FLOAT

INSERT INTO `bztbl_drivers` (`Id`, `Firstname`, `MiddleName`,`Lastname`, `Email`, `Password`, `Address1`, `Address2`, `Phone`,`SSN`,`DeviceId`,`DeviceType`,`isLicenseAccepted`, 
 `isActive`,`status`, `currentlat`, `currentlong`, `CardType`, `CardProvider`, `cardBillingAddress1`, `cardBillingAddress2`, `CardToken`, CreatedByDate) VALUES
(1, 'Shenjin', 'Thomas','Sacramento', 'shenjin@outlook.com', 'passs', 'muttom', 'tpz', '0000-0000', 'sssn1', 'xxzxzxzczc', 'A', 1, 1, 'A',38.7521, 121.2880,'C', 'VISA','passs', 'muttom','zxxxx', now());

INSERT INTO `bztbl_riders` (`Id`, `Firstname`, `MiddleName`,`Lastname`, `Email`, `Password`, `Address1`, `Address2`, `Phone`,`DeviceId`,`DeviceType`,`isLicenseAccepted`, 
 `isActive`, `CardType`, `CardProvider`, `cardBillingAddress1`, `cardBillingAddress2`, `CardToken`, CreatedByDate) VALUES
(1, 'Santhosh', 'Joseph','K', 'sankjoseph@gmail.com', 'passs', 'muttom', 'tpz', '0000-0000',  'xxzxzxzczc', 'A', 1, 1,'C', 'VISA','passs', 'muttom','zxxxx', now());


DROP TABLE 	bztbl_reportedproblems;// added USerId and merged

CREATE TABLE IF NOT EXISTS `bztbl_reportedproblems` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `UserId` varchar(10) NOT NULL,
  `ReportTitle` varchar(200) NOT NULL,
  `ReportDescription` varchar(500) NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


DROP TABLE 	bztbl_riderequests;//added to main script

CREATE TABLE IF NOT EXISTS `bztbl_riderequests` (
  `Id` int(10) NOT NULL AUTO_INCREMENT,
  `RequestType` varchar(2) NOT NULL,
  `RequestorId` varchar(10) NOT NULL,
  `DriverId` varchar(10) NOT NULL,
  `StartLocation` varchar(200) NOT NULL,
  `EndLocation` varchar(200) NOT NULL,
  `StartLat` FLOAT,
  `StartLong` FLOAT,
  `EndLat` FLOAT,
  `EndLong` FLOAT,
  `ActualStartLat` FLOAT,
  `ActualStartLong` FLOAT,
  `ActualEndLat` FLOAT,
  `ActualEndLong` FLOAT,
  `ChargeDistance` FLOAT,
  `ChargeTime` FLOAT,
  `Status` varchar(2) NOT NULL,
  `RideDateTime` datetime NOT NULL,
  `ActualRideDateTimeStart` datetime NOT NULL,
  `ActualRideDateTimeEnd` datetime NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;


--
-- Table structure for table `bztbl_driverinsurancedetails`
--

--
-- Dumping data for table `bztbl_userbankdetails`
--

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
