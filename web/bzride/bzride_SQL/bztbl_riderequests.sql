-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Sep 09, 2016 at 03:01 AM
-- Server version: 5.6.29-log
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
  `Status` varchar(2) NOT NULL,
  `RideDateTime` datetime NOT NULL,
  `ActualRideDateTime` datetime NOT NULL,
  `CreatedByDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=46 ;

--
-- Dumping data for table `bztbl_riderequests`
--

INSERT INTO `bztbl_riderequests` (`Id`, `RequestType`, `RequestorId`, `DriverId`, `StartLocation`, `EndLocation`, `StartLat`, `StartLong`, `EndLat`, `EndLong`, `Status`, `RideDateTime`, `ActualRideDateTime`, `CreatedByDate`) VALUES
(1, 'I', '1', '', 'roseville', 'sacramento', 38.7521, 121.288, 38.5816, 121.494, 'C', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2016-08-25 21:56:06'),
(41, 'I', '1', '', 'roseville', 'sacramento', 38.7521, 121.288, 38.5816, 121.494, 'C', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2016-09-02 08:38:15'),
(42, 'I', '1', '', 'roseville', 'sacramento', 38.7521, 121.288, 38.5816, 121.494, 'C', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2016-09-02 08:38:24'),
(43, 'I', '1', '', 'roseville', 'sacramento', 38.7521, 121.288, 38.5816, 121.494, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2016-09-02 08:38:32'),
(44, 'I', '1', '', 'roseville', 'sacramento', 38.7521, 121.288, 38.5816, 121.494, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2016-09-02 08:41:14'),
(45, 'I', '1', '', 'roseville', 'sacramento', 38.7521, 121.288, 38.5816, 121.494, 'C', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2016-09-02 08:41:48');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
