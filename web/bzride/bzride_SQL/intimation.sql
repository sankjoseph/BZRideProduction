-- phpMyAdmin SQL Dump
-- version 3.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 11, 2016 at 11:21 AM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `intimation`
--

-- --------------------------------------------------------

--
-- Table structure for table `bz_driver_details`
--

CREATE TABLE IF NOT EXISTS `bz_driver_details` (
  `Driverid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Firstname` varchar(20) NOT NULL,
  `Lastname` varchar(20) NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Addr1` varchar(60) NOT NULL,
  `Addr2` varchar(60) NOT NULL,
  `Telephone` int(11) NOT NULL,
  `SSN` varchar(20) NOT NULL,
  `Vehicleyear` int(11) NOT NULL,
  `Vmodel` varchar(20) NOT NULL,
  `Vmake` varchar(20) NOT NULL,
  `Vcolor` varchar(20) NOT NULL,
  `Regno` varchar(20) NOT NULL,
  `Regstate` varchar(20) NOT NULL,
  `Regdate` int(11) NOT NULL,
  `Regexpiry` int(11) NOT NULL,
  `Insurancecompany` varchar(40) NOT NULL,
  `Insplocyno` varchar(20) NOT NULL,
  `Insdate` int(11) NOT NULL,
  `Insexpiry` int(11) NOT NULL,
  `Licenseno` varchar(20) NOT NULL,
  `Licissuestate` varchar(20) NOT NULL,
  `Licissuedate` int(11) NOT NULL,
  `Licexpiry` int(11) NOT NULL,
  `Photo` varchar(30) NOT NULL,
  `Role` int(11) NOT NULL,
  PRIMARY KEY (`Driverid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `bz_driver_details`
--

INSERT INTO `bz_driver_details` (`Driverid`, `Firstname`, `Lastname`, `Email`, `Password`, `Addr1`, `Addr2`, `Telephone`, `SSN`, `Vehicleyear`, `Vmodel`, `Vmake`, `Vcolor`, `Regno`, `Regstate`, `Regdate`, `Regexpiry`, `Insurancecompany`, `Insplocyno`, `Insdate`, `Insexpiry`, `Licenseno`, `Licissuestate`, `Licissuedate`, `Licexpiry`, `Photo`, `Role`) VALUES
(1, 'Bibin', 'Baby', 'lovingbibin', '1234', 'asd', 'dsa', 125488566, '665', 2016, 'Innova', 'Toyota', 'black', 'kl 2 561', 'kerala', 251297, 25122017, 'religare', 'klj445', 1254226, 1161646, 'klgh556', 'kerala', 12042016, 12041028, 'logo.png', 2),
(2, 'athira', 's', 'email', 'passssss', 'addrrr', 'adress', 1234566, '555', 2014, 'alto', 'Maruthy', 'red', 'kl 10 a 1010', 'kerala', 4546626, 6565465, 'national', 'na', 0, 0, '', '', 0, 0, '', 2),
(3, 'athira', 's', 'email', '', 'addrrr', 'adress', 1234566, '555', 2014, 'alto', 'Maruthy', 'red', 'kl 10 a 1010', 'kerala', 4546626, 6565465, 'national', 'na54545', 15454, 16, '15565-4222', 'kerala', 45265, 4544655, '', 2),
(4, 'arya', 's', 'cfvgbh@gmgf.com', '1234', 'azxsd', 'qssdd', 125488566, '555', 2014, 'q4', 'audi', 'white', 'kl 10 a 1010', 'kerala', 251297, 25122017, 'national', 'klj445', 1254226, 1161646, 'jn66', 'kerala', 12042016, 12041028, '', 2),
(5, 'arya23', 's', 'cfvgbh@gmgf.com', '1234', 'azxsd', 'qssdd', 125488566, '555', 2014, 'q4', 'audi', 'white', 'kl 10 a 1010', 'kerala', 251297, 25122017, 'national', 'klj445', 1254226, 1161646, 'jn66', 'kerala', 12042016, 12041028, '', 2),
(6, 'arya', 'sasi', 'arya@gmail.com', '1234', 'address1', 'addr2', 99478547, '999', 2014, 'q4', 'audi', 'Black', 'KL-38D-3838', 'Kerala', 10, 9, 'National', 'klaudi445', 10, 9, 'lic1234', 'Kerala', 25, 24, 'Driver_image', 2),
(7, 'Arya', ' ', 'arya@gmail.com', '1234', 'address1', 'addr2', 99478547, '999', 2014, 'q4', 'audi', 'Black', 'KL-38D-3838', 'Kerala', 10, 9, 'National', 'klaudi445', 10, 9, 'lic1234', 'Kerala', 25, 24, 'Driver_image', 2),
(8, 'Arya', 'Intimation', 'arya@gmail.com', '1234', 'address1', 'addr2', 99478547, '999', 2014, 'q4', 'audi', 'Black', 'KL-38D-3838', 'Kerala', 10, 9, 'National', 'klaudi445', 10, 9, 'lic1234', 'Kerala', 25, 24, 'Driver_image', 2);

-- --------------------------------------------------------

--
-- Table structure for table `bz_user_details`
--

CREATE TABLE IF NOT EXISTS `bz_user_details` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `Firstname` varchar(20) NOT NULL,
  `Lastname` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(50) NOT NULL,
  `addr1` varchar(60) NOT NULL,
  `addr2` varchar(60) NOT NULL,
  `Telephone` int(11) NOT NULL,
  `Mobile` int(11) NOT NULL,
  `Cardtype` varchar(20) NOT NULL,
  `Subtype` varchar(20) NOT NULL,
  `Cardno` varchar(20) NOT NULL,
  `Expirydate` varchar(15) NOT NULL,
  `Billingaddr1` varchar(60) NOT NULL,
  `Billingaddr2` varchar(60) NOT NULL,
  `CVV` int(11) NOT NULL,
  `Role` int(11) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `bz_user_details`
--

INSERT INTO `bz_user_details` (`userid`, `Firstname`, `Lastname`, `email`, `password`, `addr1`, `addr2`, `Telephone`, `Mobile`, `Cardtype`, `Subtype`, `Cardno`, `Expirydate`, `Billingaddr1`, `Billingaddr2`, `CVV`, `Role`) VALUES
(1, 'Name1', 'Name2', '@yahoo', 'passs', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(2, 'Name1', 'Name2', '@yahoo', 'passs', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(3, 'Name1', 'Name2', '@yahoo', 'passs', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(4, 'Name1', 'Name2', '@gmail', 'passs', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(5, 'Name2', 'Name2', '@gmail', 'passs', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(6, 'athira', 'sasi', '@yahoo', 'passs', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(7, 'Athira', 'Sasi', 'ath@yahoo', '123456', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(8, '1234555', 'Sasi', 'ath@yahoo', '123456', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(9, 'Athira ', 'Intimation', 'ath@yahoo', '123456', 'tdpa', 'pala', 1857, 1892, 'mastro', 'visa', '225875', '2018', 'tdpa1', 'pala1', 185, 2),
(10, 'inti', 'mation', 'admin', '', 'dfsf', 'snnxv', 1234566, 987654321, 'debit', 'Visa', '5469871230', '12112018', 'fshfsd', 'erewrqehrwj', 777, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
