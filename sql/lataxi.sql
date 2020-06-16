-- phpMyAdmin SQL Dump
-- version 4.7.8
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 06, 2018 at 09:17 AM
-- Server version: 5.7.23-0ubuntu0.16.04.1
-- PHP Version: 7.1.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `techlabz_lataxi`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `cust_id` int(11) NOT NULL,
  `work` varchar(100) NOT NULL,
  `home` varchar(100) NOT NULL,
  `home_lat` varchar(25) DEFAULT NULL,
  `home_long` varchar(25) NOT NULL,
  `work_lat` varchar(25) NOT NULL,
  `work_long` varchar(25) NOT NULL,
  `name` varchar(250) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1=>home,2=>work',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1=>Active,2=>Inactive'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `admin_users`
--

CREATE TABLE `admin_users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_type` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin_users`
--

INSERT INTO `admin_users` (`id`, `username`, `email`, `password`, `role_type`) VALUES
(1, 'admin', 'doan@gmail.com', '21232f297a57a5a743894a0e4a801fc3', 1);

-- --------------------------------------------------------

--
-- Table structure for table `auth_table`
--

CREATE TABLE `auth_table` (
  `id` int(11) NOT NULL,
  `cust_id` int(11) NOT NULL,
  `unique_id` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(11) NOT NULL,
  `booking_id` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_date` varchar(100) NOT NULL,
  `car_type` varchar(50) NOT NULL,
  `driver_id` int(25) NOT NULL,
  `base_fare` double NOT NULL,
  `km_fare` double NOT NULL,
  `min_fare` double NOT NULL,
  `promotion_fare` double NOT NULL,
  `sub_total_fare` double NOT NULL,
  `fare` double NOT NULL,
  `total_km` double NOT NULL,
  `time` varchar(100) NOT NULL,
  `start_time` varchar(100) NOT NULL,
  `end_time` varchar(100) NOT NULL,
  `trip_start_time` varchar(100) NOT NULL,
  `trip_end_time` varchar(100) NOT NULL,
  `source` varchar(250) NOT NULL,
  `destination` varchar(250) NOT NULL,
  `distance` varchar(100) NOT NULL,
  `source_lat` varchar(100) NOT NULL,
  `source_lng` varchar(100) NOT NULL,
  `destination_lat` varchar(100) NOT NULL,
  `destination_lng` varchar(100) NOT NULL,
  `payment_status` int(11) NOT NULL COMMENT '1=>Succes,0=>Pending',
  `status` int(11) DEFAULT NULL COMMENT '0=>cancelled,1=>booking,2=>inprocess,3=>completed',
  `pattern_id` int(10) NOT NULL,
  `fee` int(50) NOT NULL,
  `tax` int(50) NOT NULL,
  `discount` int(10) NOT NULL,
  `payout` int(50) NOT NULL,
  `cash_collection` int(10) NOT NULL DEFAULT '0' COMMENT '1->Cash Collected, 0->Cash Not collected',
  `car_arrival` int(10) NOT NULL DEFAULT '0' COMMENT '1->Arrived, 0->Not Arrived',
  `trip_path` longtext NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `car`
--

CREATE TABLE `car` (
  `id` int(11) NOT NULL,
  `driv_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `model` varchar(50) NOT NULL,
  `car_owner` varchar(100) NOT NULL,
  `vehicle_reg_num` varchar(100) NOT NULL,
  `max_seat` int(50) NOT NULL,
  `min_fare` double NOT NULL,
  `km_fare` double NOT NULL,
  `time_fare` double NOT NULL,
  `image` varchar(250) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1=>Active , 0=>Inactive, 2=>Delete'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `car_type`
--

CREATE TABLE `car_type` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image` varchar(250) NOT NULL,
  `max_seat` int(50) NOT NULL,
  `fare` double NOT NULL,
  `min_fare` double NOT NULL,
  `km_fare` double NOT NULL,
  `time_fare` double NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `charge_condition`
--

CREATE TABLE `charge_condition` (
  `id` int(11) NOT NULL,
  `pat_id` int(11) NOT NULL,
  `start_time` varchar(10) NOT NULL,
  `end_time` varchar(10) NOT NULL,
  `fare` double NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE `countries` (
  `id` int(6) UNSIGNED NOT NULL,
  `id_countries` int(3) UNSIGNED NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `iso_alpha2` varchar(2) DEFAULT NULL,
  `iso_alpha3` varchar(3) DEFAULT NULL,
  `iso_numeric` int(11) DEFAULT NULL,
  `currency_code` char(3) DEFAULT NULL,
  `currency_name` varchar(32) DEFAULT NULL,
  `currrency_symbol` varchar(3) DEFAULT NULL,
  `flag` varchar(6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`id`, `id_countries`, `name`, `iso_alpha2`, `iso_alpha3`, `iso_numeric`, `currency_code`, `currency_name`, `currrency_symbol`, `flag`) VALUES
(1, 1, 'Afghanistan', 'AF', 'AFG', 4, 'AFN', 'Afghani', '؋', 'AF.png'),
(2, 2, 'Albania', 'AL', 'ALB', 8, 'ALL', 'Lek', 'Lek', 'AL.png'),
(3, 3, 'Algeria', 'DZ', 'DZA', 12, 'DZD', 'Dinar', NULL, 'DZ.png'),
(4, 4, 'American Samoa', 'AS', 'ASM', 16, 'USD', 'Dollar', '$', 'AS.png'),
(5, 5, 'Andorra', 'AD', 'AND', 20, 'EUR', 'Euro', '€', 'AD.png'),
(6, 6, 'Angola', 'AO', 'AGO', 24, 'AOA', 'Kwanza', 'Kz', 'AO.png'),
(7, 7, 'Anguilla', 'AI', 'AIA', 660, 'XCD', 'Dollar', '$', 'AI.png'),
(8, 8, 'Antarctica', 'AQ', 'ATA', 10, '', '', NULL, 'AQ.png'),
(9, 9, 'Antigua and Barbuda', 'AG', 'ATG', 28, 'XCD', 'Dollar', '$', 'AG.png'),
(10, 10, 'Argentina', 'AR', 'ARG', 32, 'ARS', 'Peso', '$', 'AR.png'),
(11, 11, 'Armenia', 'AM', 'ARM', 51, 'AMD', 'Dram', NULL, 'AM.png'),
(12, 12, 'Aruba', 'AW', 'ABW', 533, 'AWG', 'Guilder', 'ƒ', 'AW.png'),
(13, 13, 'Australia', 'AU', 'AUS', 36, 'AUD', 'Dollar', '$', 'AU.png'),
(14, 14, 'Austria', 'AT', 'AUT', 40, 'EUR', 'Euro', '€', 'AT.png'),
(15, 15, 'Azerbaijan', 'AZ', 'AZE', 31, 'AZN', 'Manat', 'ман', 'AZ.png'),
(16, 16, 'Bahamas', 'BS', 'BHS', 44, 'BSD', 'Dollar', '$', 'BS.png'),
(17, 17, 'Bahrain', 'BH', 'BHR', 48, 'BHD', 'Dinar', NULL, 'BH.png'),
(18, 18, 'Bangladesh', 'BD', 'BGD', 50, 'BDT', 'Taka', NULL, 'BD.png'),
(19, 19, 'Barbados', 'BB', 'BRB', 52, 'BBD', 'Dollar', '$', 'BB.png'),
(20, 20, 'Belarus', 'BY', 'BLR', 112, 'BYR', 'Ruble', 'p.', 'BY.png'),
(21, 21, 'Belgium', 'BE', 'BEL', 56, 'EUR', 'Euro', '€', 'BE.png'),
(22, 22, 'Belize', 'BZ', 'BLZ', 84, 'BZD', 'Dollar', 'BZ$', 'BZ.png'),
(23, 23, 'Benin', 'BJ', 'BEN', 204, 'XOF', 'Franc', NULL, 'BJ.png'),
(24, 24, 'Bermuda', 'BM', 'BMU', 60, 'BMD', 'Dollar', '$', 'BM.png'),
(25, 25, 'Bhutan', 'BT', 'BTN', 64, 'BTN', 'Ngultrum', NULL, 'BT.png'),
(26, 26, 'Bolivia', 'BO', 'BOL', 68, 'BOB', 'Boliviano', '$b', 'BO.png'),
(27, 27, 'Bosnia and Herzegovina', 'BA', 'BIH', 70, 'BAM', 'Marka', 'KM', 'BA.png'),
(28, 28, 'Botswana', 'BW', 'BWA', 72, 'BWP', 'Pula', 'P', 'BW.png'),
(29, 29, 'Bouvet Island', 'BV', 'BVT', 74, 'NOK', 'Krone', 'kr', 'BV.png'),
(30, 30, 'Brazil', 'BR', 'BRA', 76, 'BRL', 'Real', 'R$', 'BR.png'),
(31, 31, 'British Indian Ocean Territory', 'IO', 'IOT', 86, 'USD', 'Dollar', '$', 'IO.png'),
(32, 32, 'British Virgin Islands', 'VG', 'VGB', 92, 'USD', 'Dollar', '$', 'VG.png'),
(33, 33, 'Brunei', 'BN', 'BRN', 96, 'BND', 'Dollar', '$', 'BN.png'),
(34, 34, 'Bulgaria', 'BG', 'BGR', 100, 'BGN', 'Lev', 'лв', 'BG.png'),
(35, 35, 'Burkina Faso', 'BF', 'BFA', 854, 'XOF', 'Franc', NULL, 'BF.png'),
(36, 36, 'Burundi', 'BI', 'BDI', 108, 'BIF', 'Franc', NULL, 'BI.png'),
(37, 37, 'Cambodia', 'KH', 'KHM', 116, 'KHR', 'Riels', '៛', 'KH.png'),
(38, 38, 'Cameroon', 'CM', 'CMR', 120, 'XAF', 'Franc', 'FCF', 'CM.png'),
(39, 39, 'Canada', 'CA', 'CAN', 124, 'CAD', 'Dollar', '$', 'CA.png'),
(40, 40, 'Cape Verde', 'CV', 'CPV', 132, 'CVE', 'Escudo', NULL, 'CV.png'),
(41, 41, 'Cayman Islands', 'KY', 'CYM', 136, 'KYD', 'Dollar', '$', 'KY.png'),
(42, 42, 'Central African Republic', 'CF', 'CAF', 140, 'XAF', 'Franc', 'FCF', 'CF.png'),
(43, 43, 'Chad', 'TD', 'TCD', 148, 'XAF', 'Franc', NULL, 'TD.png'),
(44, 44, 'Chile', 'CL', 'CHL', 152, 'CLP', 'Peso', NULL, 'CL.png'),
(45, 45, 'China', 'CN', 'CHN', 156, 'CNY', 'Yuan Renminbi', '¥', 'CN.png'),
(46, 46, 'Christmas Island', 'CX', 'CXR', 162, 'AUD', 'Dollar', '$', 'CX.png'),
(47, 47, 'Cocos Islands', 'CC', 'CCK', 166, 'AUD', 'Dollar', '$', 'CC.png'),
(48, 48, 'Colombia', 'CO', 'COL', 170, 'COP', 'Peso', '$', 'CO.png'),
(49, 49, 'Comoros', 'KM', 'COM', 174, 'KMF', 'Franc', NULL, 'KM.png'),
(50, 50, 'Cook Islands', 'CK', 'COK', 184, 'NZD', 'Dollar', '$', 'CK.png'),
(51, 51, 'Costa Rica', 'CR', 'CRI', 188, 'CRC', 'Colon', '₡', 'CR.png'),
(52, 52, 'Croatia', 'HR', 'HRV', 191, 'HRK', 'Kuna', 'kn', 'HR.png'),
(53, 53, 'Cuba', 'CU', 'CUB', 192, 'CUP', 'Peso', '₱', 'CU.png'),
(54, 54, 'Cyprus', 'CY', 'CYP', 196, 'CYP', 'Pound', NULL, 'CY.png'),
(55, 55, 'Czech Republic', 'CZ', 'CZE', 203, 'CZK', 'Koruna', 'KĿ', 'CZ.png'),
(56, 56, 'Democratic Republic of the Congo', 'CD', 'COD', 180, 'CDF', 'Franc', NULL, 'CD.png'),
(57, 57, 'Denmark', 'DK', 'DNK', 208, 'DKK', 'Krone', 'kr', 'DK.png'),
(58, 58, 'Djibouti', 'DJ', 'DJI', 262, 'DJF', 'Franc', NULL, 'DJ.png'),
(59, 59, 'Dominica', 'DM', 'DMA', 212, 'XCD', 'Dollar', '$', 'DM.png'),
(60, 60, 'Dominican Republic', 'DO', 'DOM', 214, 'DOP', 'Peso', 'RD$', 'DO.png'),
(61, 61, 'East Timor', 'TL', 'TLS', 626, 'USD', 'Dollar', '$', 'TL.png'),
(62, 62, 'Ecuador', 'EC', 'ECU', 218, 'USD', 'Dollar', '$', 'EC.png'),
(63, 63, 'Egypt', 'EG', 'EGY', 818, 'EGP', 'Pound', '£', 'EG.png'),
(64, 64, 'El Salvador', 'SV', 'SLV', 222, 'SVC', 'Colone', '$', 'SV.png'),
(65, 65, 'Equatorial Guinea', 'GQ', 'GNQ', 226, 'XAF', 'Franc', 'FCF', 'GQ.png'),
(66, 66, 'Eritrea', 'ER', 'ERI', 232, 'ERN', 'Nakfa', 'Nfk', 'ER.png'),
(67, 67, 'Estonia', 'EE', 'EST', 233, 'EEK', 'Kroon', 'kr', 'EE.png'),
(68, 68, 'Ethiopia', 'ET', 'ETH', 231, 'ETB', 'Birr', NULL, 'ET.png'),
(69, 69, 'Falkland Islands', 'FK', 'FLK', 238, 'FKP', 'Pound', '£', 'FK.png'),
(70, 70, 'Faroe Islands', 'FO', 'FRO', 234, 'DKK', 'Krone', 'kr', 'FO.png'),
(71, 71, 'Fiji', 'FJ', 'FJI', 242, 'FJD', 'Dollar', '$', 'FJ.png'),
(72, 72, 'Finland', 'FI', 'FIN', 246, 'EUR', 'Euro', '€', 'FI.png'),
(73, 73, 'France', 'FR', 'FRA', 250, 'EUR', 'Euro', '€', 'FR.png'),
(74, 74, 'French Guiana', 'GF', 'GUF', 254, 'EUR', 'Euro', '€', 'GF.png'),
(75, 75, 'French Polynesia', 'PF', 'PYF', 258, 'XPF', 'Franc', NULL, 'PF.png'),
(76, 76, 'French Southern Territories', 'TF', 'ATF', 260, 'EUR', 'Euro  ', '€', 'TF.png'),
(77, 77, 'Gabon', 'GA', 'GAB', 266, 'XAF', 'Franc', 'FCF', 'GA.png'),
(78, 78, 'Gambia', 'GM', 'GMB', 270, 'GMD', 'Dalasi', 'D', 'GM.png'),
(79, 79, 'Georgia', 'GE', 'GEO', 268, 'GEL', 'Lari', NULL, 'GE.png'),
(80, 80, 'Germany', 'DE', 'DEU', 276, 'EUR', 'Euro', '€', 'DE.png'),
(81, 81, 'Ghana', 'GH', 'GHA', 288, 'GHC', 'Cedi', '¢', 'GH.png'),
(82, 82, 'Gibraltar', 'GI', 'GIB', 292, 'GIP', 'Pound', '£', 'GI.png'),
(83, 83, 'Greece', 'GR', 'GRC', 300, 'EUR', 'Euro', '€', 'GR.png'),
(84, 84, 'Greenland', 'GL', 'GRL', 304, 'DKK', 'Krone', 'kr', 'GL.png'),
(85, 85, 'Grenada', 'GD', 'GRD', 308, 'XCD', 'Dollar', '$', 'GD.png'),
(86, 86, 'Guadeloupe', 'GP', 'GLP', 312, 'EUR', 'Euro', '€', 'GP.png'),
(87, 87, 'Guam', 'GU', 'GUM', 316, 'USD', 'Dollar', '$', 'GU.png'),
(88, 88, 'Guatemala', 'GT', 'GTM', 320, 'GTQ', 'Quetzal', 'Q', 'GT.png'),
(89, 89, 'Guinea', 'GN', 'GIN', 324, 'GNF', 'Franc', NULL, 'GN.png'),
(90, 90, 'Guinea-Bissau', 'GW', 'GNB', 624, 'XOF', 'Franc', NULL, 'GW.png'),
(91, 91, 'Guyana', 'GY', 'GUY', 328, 'GYD', 'Dollar', '$', 'GY.png'),
(92, 92, 'Haiti', 'HT', 'HTI', 332, 'HTG', 'Gourde', 'G', 'HT.png'),
(93, 93, 'Heard Island and McDonald Islands', 'HM', 'HMD', 334, 'AUD', 'Dollar', '$', 'HM.png'),
(94, 94, 'Honduras', 'HN', 'HND', 340, 'HNL', 'Lempira', 'L', 'HN.png'),
(95, 95, 'Hong Kong', 'HK', 'HKG', 344, 'HKD', 'Dollar', '$', 'HK.png'),
(96, 96, 'Hungary', 'HU', 'HUN', 348, 'HUF', 'Forint', 'Ft', 'HU.png'),
(97, 97, 'Iceland', 'IS', 'ISL', 352, 'ISK', 'Krona', 'kr', 'IS.png'),
(98, 98, 'India', 'IN', 'IND', 356, 'INR', 'Rupee', '₹', 'IN.png'),
(99, 99, 'Indonesia', 'ID', 'IDN', 360, 'IDR', 'Rupiah', 'Rp', 'ID.png'),
(100, 100, 'Iran', 'IR', 'IRN', 364, 'IRR', 'Rial', '﷼', 'IR.png'),
(101, 101, 'Iraq', 'IQ', 'IRQ', 368, 'IQD', 'Dinar', NULL, 'IQ.png'),
(102, 102, 'Ireland', 'IE', 'IRL', 372, 'EUR', 'Euro', '€', 'IE.png'),
(103, 103, 'Israel', 'IL', 'ISR', 376, 'ILS', 'Shekel', '₪', 'IL.png'),
(104, 104, 'Italy', 'IT', 'ITA', 380, 'EUR', 'Euro', '€', 'IT.png'),
(105, 105, 'Ivory Coast', 'CI', 'CIV', 384, 'XOF', 'Franc', NULL, 'CI.png'),
(106, 106, 'Jamaica', 'JM', 'JAM', 388, 'JMD', 'Dollar', '$', 'JM.png'),
(107, 107, 'Japan', 'JP', 'JPN', 392, 'JPY', 'Yen', '¥', 'JP.png'),
(108, 108, 'Jordan', 'JO', 'JOR', 400, 'JOD', 'Dinar', NULL, 'JO.png'),
(109, 109, 'Kazakhstan', 'KZ', 'KAZ', 398, 'KZT', 'Tenge', 'лв', 'KZ.png'),
(110, 110, 'Kenya', 'KE', 'KEN', 404, 'KES', 'Shilling', NULL, 'KE.png'),
(111, 111, 'Kiribati', 'KI', 'KIR', 296, 'AUD', 'Dollar', '$', 'KI.png'),
(112, 112, 'Kuwait', 'KW', 'KWT', 414, 'KWD', 'Dinar', NULL, 'KW.png'),
(113, 113, 'Kyrgyzstan', 'KG', 'KGZ', 417, 'KGS', 'Som', 'лв', 'KG.png'),
(114, 114, 'Laos', 'LA', 'LAO', 418, 'LAK', 'Kip', '₭', 'LA.png'),
(115, 115, 'Latvia', 'LV', 'LVA', 428, 'LVL', 'Lat', 'Ls', 'LV.png'),
(116, 116, 'Lebanon', 'LB', 'LBN', 422, 'LBP', 'Pound', '£', 'LB.png'),
(117, 117, 'Lesotho', 'LS', 'LSO', 426, 'LSL', 'Loti', 'L', 'LS.png'),
(118, 118, 'Liberia', 'LR', 'LBR', 430, 'LRD', 'Dollar', '$', 'LR.png'),
(119, 119, 'Libya', 'LY', 'LBY', 434, 'LYD', 'Dinar', NULL, 'LY.png'),
(120, 120, 'Liechtenstein', 'LI', 'LIE', 438, 'CHF', 'Franc', 'CHF', 'LI.png'),
(121, 121, 'Lithuania', 'LT', 'LTU', 440, 'LTL', 'Litas', 'Lt', 'LT.png'),
(122, 122, 'Luxembourg', 'LU', 'LUX', 442, 'EUR', 'Euro', '€', 'LU.png'),
(123, 123, 'Macao', 'MO', 'MAC', 446, 'MOP', 'Pataca', 'MOP', 'MO.png'),
(124, 124, 'Macedonia', 'MK', 'MKD', 807, 'MKD', 'Denar', 'ден', 'MK.png'),
(125, 125, 'Madagascar', 'MG', 'MDG', 450, 'MGA', 'Ariary', NULL, 'MG.png'),
(126, 126, 'Malawi', 'MW', 'MWI', 454, 'MWK', 'Kwacha', 'MK', 'MW.png'),
(127, 127, 'Malaysia', 'MY', 'MYS', 458, 'MYR', 'Ringgit', 'RM', 'MY.png'),
(128, 128, 'Maldives', 'MV', 'MDV', 462, 'MVR', 'Rufiyaa', 'Rf', 'MV.png'),
(129, 129, 'Mali', 'ML', 'MLI', 466, 'XOF', 'Franc', NULL, 'ML.png'),
(130, 130, 'Malta', 'MT', 'MLT', 470, 'MTL', 'Lira', NULL, 'MT.png'),
(131, 131, 'Marshall Islands', 'MH', 'MHL', 584, 'USD', 'Dollar', '$', 'MH.png'),
(132, 132, 'Martinique', 'MQ', 'MTQ', 474, 'EUR', 'Euro', '€', 'MQ.png'),
(133, 133, 'Mauritania', 'MR', 'MRT', 478, 'MRO', 'Ouguiya', 'UM', 'MR.png'),
(134, 134, 'Mauritius', 'MU', 'MUS', 480, 'MUR', 'Rupee', '₨', 'MU.png'),
(135, 135, 'Mayotte', 'YT', 'MYT', 175, 'EUR', 'Euro', '€', 'YT.png'),
(136, 136, 'Mexico', 'MX', 'MEX', 484, 'MXN', 'Peso', '$', 'MX.png'),
(137, 137, 'Micronesia', 'FM', 'FSM', 583, 'USD', 'Dollar', '$', 'FM.png'),
(138, 138, 'Moldova', 'MD', 'MDA', 498, 'MDL', 'Leu', NULL, 'MD.png'),
(139, 139, 'Monaco', 'MC', 'MCO', 492, 'EUR', 'Euro', '€', 'MC.png'),
(140, 140, 'Mongolia', 'MN', 'MNG', 496, 'MNT', 'Tugrik', '₮', 'MN.png'),
(141, 141, 'Montserrat', 'MS', 'MSR', 500, 'XCD', 'Dollar', '$', 'MS.png'),
(142, 142, 'Morocco', 'MA', 'MAR', 504, 'MAD', 'Dirham', NULL, 'MA.png'),
(143, 143, 'Mozambique', 'MZ', 'MOZ', 508, 'MZN', 'Meticail', 'MT', 'MZ.png'),
(144, 144, 'Myanmar', 'MM', 'MMR', 104, 'MMK', 'Kyat', 'K', 'MM.png'),
(145, 145, 'Namibia', 'NA', 'NAM', 516, 'NAD', 'Dollar', '$', 'NA.png'),
(146, 146, 'Nauru', 'NR', 'NRU', 520, 'AUD', 'Dollar', '$', 'NR.png'),
(147, 147, 'Nepal', 'NP', 'NPL', 524, 'NPR', 'Rupee', '₨', 'NP.png'),
(148, 148, 'Netherlands', 'NL', 'NLD', 528, 'EUR', 'Euro', '€', 'NL.png'),
(149, 149, 'Netherlands Antilles', 'AN', 'ANT', 530, 'ANG', 'Guilder', 'ƒ', 'AN.png'),
(150, 150, 'New Caledonia', 'NC', 'NCL', 540, 'XPF', 'Franc', NULL, 'NC.png'),
(151, 151, 'New Zealand', 'NZ', 'NZL', 554, 'NZD', 'Dollar', '$', 'NZ.png'),
(152, 152, 'Nicaragua', 'NI', 'NIC', 558, 'NIO', 'Cordoba', 'C$', 'NI.png'),
(153, 153, 'Niger', 'NE', 'NER', 562, 'XOF', 'Franc', NULL, 'NE.png'),
(154, 154, 'Nigeria', 'NG', 'NGA', 566, 'NGN', 'Naira', '₦', 'NG.png'),
(155, 155, 'Niue', 'NU', 'NIU', 570, 'NZD', 'Dollar', '$', 'NU.png'),
(156, 156, 'Norfolk Island', 'NF', 'NFK', 574, 'AUD', 'Dollar', '$', 'NF.png'),
(157, 157, 'North Korea', 'KP', 'PRK', 408, 'KPW', 'Won', '₩', 'KP.png'),
(158, 158, 'Northern Mariana Islands', 'MP', 'MNP', 580, 'USD', 'Dollar', '$', 'MP.png'),
(159, 159, 'Norway', 'NO', 'NOR', 578, 'NOK', 'Krone', 'kr', 'NO.png'),
(160, 160, 'Oman', 'OM', 'OMN', 512, 'OMR', 'Rial', '﷼', 'OM.png'),
(161, 161, 'Pakistan', 'PK', 'PAK', 586, 'PKR', 'Rupee', '₨', 'PK.png'),
(162, 162, 'Palau', 'PW', 'PLW', 585, 'USD', 'Dollar', '$', 'PW.png'),
(163, 163, 'Palestinian Territory', 'PS', 'PSE', 275, 'ILS', 'Shekel', '₪', 'PS.png'),
(164, 164, 'Panama', 'PA', 'PAN', 591, 'PAB', 'Balboa', 'B/.', 'PA.png'),
(165, 165, 'Papua New Guinea', 'PG', 'PNG', 598, 'PGK', 'Kina', NULL, 'PG.png'),
(166, 166, 'Paraguay', 'PY', 'PRY', 600, 'PYG', 'Guarani', 'Gs', 'PY.png'),
(167, 167, 'Peru', 'PE', 'PER', 604, 'PEN', 'Sol', 'S/.', 'PE.png'),
(168, 168, 'Philippines', 'PH', 'PHL', 608, 'PHP', 'Peso', 'Php', 'PH.png'),
(169, 169, 'Pitcairn', 'PN', 'PCN', 612, 'NZD', 'Dollar', '$', 'PN.png'),
(170, 170, 'Poland', 'PL', 'POL', 616, 'PLN', 'Zloty', 'zł', 'PL.png'),
(171, 171, 'Portugal', 'PT', 'PRT', 620, 'EUR', 'Euro', '€', 'PT.png'),
(172, 172, 'Puerto Rico', 'PR', 'PRI', 630, 'USD', 'Dollar', '$', 'PR.png'),
(173, 173, 'Qatar', 'QA', 'QAT', 634, 'QAR', 'Rial', '﷼', 'QA.png'),
(174, 174, 'Republic of the Congo', 'CG', 'COG', 178, 'XAF', 'Franc', 'FCF', 'CG.png'),
(175, 175, 'Reunion', 'RE', 'REU', 638, 'EUR', 'Euro', '€', 'RE.png'),
(176, 176, 'Romania', 'RO', 'ROU', 642, 'RON', 'Leu', 'lei', 'RO.png'),
(177, 177, 'Russia', 'RU', 'RUS', 643, 'RUB', 'Ruble', 'руб', 'RU.png'),
(178, 178, 'Rwanda', 'RW', 'RWA', 646, 'RWF', 'Franc', NULL, 'RW.png'),
(179, 179, 'Saint Helena', 'SH', 'SHN', 654, 'SHP', 'Pound', '£', 'SH.png'),
(180, 180, 'Saint Kitts and Nevis', 'KN', 'KNA', 659, 'XCD', 'Dollar', '$', 'KN.png'),
(181, 181, 'Saint Lucia', 'LC', 'LCA', 662, 'XCD', 'Dollar', '$', 'LC.png'),
(182, 182, 'Saint Pierre and Miquelon', 'PM', 'SPM', 666, 'EUR', 'Euro', '€', 'PM.png'),
(183, 183, 'Saint Vincent and the Grenadines', 'VC', 'VCT', 670, 'XCD', 'Dollar', '$', 'VC.png'),
(184, 184, 'Samoa', 'WS', 'WSM', 882, 'WST', 'Tala', 'WS$', 'WS.png'),
(185, 185, 'San Marino', 'SM', 'SMR', 674, 'EUR', 'Euro', '€', 'SM.png'),
(186, 186, 'Sao Tome and Principe', 'ST', 'STP', 678, 'STD', 'Dobra', 'Db', 'ST.png'),
(187, 187, 'Saudi Arabia', 'SA', 'SAU', 682, 'SAR', 'Rial', '﷼', 'SA.png'),
(188, 188, 'Senegal', 'SN', 'SEN', 686, 'XOF', 'Franc', NULL, 'SN.png'),
(189, 189, 'Serbia and Montenegro', 'CS', 'SCG', 891, 'RSD', 'Dinar', 'Дин', 'CS.png'),
(190, 190, 'Seychelles', 'SC', 'SYC', 690, 'SCR', 'Rupee', '₨', 'SC.png'),
(191, 191, 'Sierra Leone', 'SL', 'SLE', 694, 'SLL', 'Leone', 'Le', 'SL.png'),
(192, 192, 'Singapore', 'SG', 'SGP', 702, 'SGD', 'Dollar', '$', 'SG.png'),
(193, 193, 'Slovakia', 'SK', 'SVK', 703, 'SKK', 'Koruna', 'Sk', 'SK.png'),
(194, 194, 'Slovenia', 'SI', 'SVN', 705, 'EUR', 'Euro', '€', 'SI.png'),
(195, 195, 'Solomon Islands', 'SB', 'SLB', 90, 'SBD', 'Dollar', '$', 'SB.png'),
(196, 196, 'Somalia', 'SO', 'SOM', 706, 'SOS', 'Shilling', '$', 'SO.png'),
(197, 197, 'South Africa', 'ZA', 'ZAF', 710, 'ZAR', 'Rand', 'R', 'ZA.png'),
(198, 198, 'South Georgia and the South Sandwich Islands', 'GS', 'SGS', 239, 'GBP', 'Pound', '£', 'GS.png'),
(199, 199, 'South Korea', 'KR', 'KOR', 410, 'KRW', 'Won', '₩', 'KR.png'),
(200, 200, 'Spain', 'ES', 'ESP', 724, 'EUR', 'Euro', '€', 'ES.png'),
(201, 201, 'Sri Lanka', 'LK', 'LKA', 144, 'LKR', 'Rupee', '₨', 'LK.png'),
(202, 202, 'Sudan', 'SD', 'SDN', 736, 'SDD', 'Dinar', NULL, 'SD.png'),
(203, 203, 'Suriname', 'SR', 'SUR', 740, 'SRD', 'Dollar', '$', 'SR.png'),
(204, 204, 'Svalbard and Jan Mayen', 'SJ', 'SJM', 744, 'NOK', 'Krone', 'kr', 'SJ.png'),
(205, 205, 'Swaziland', 'SZ', 'SWZ', 748, 'SZL', 'Lilangeni', NULL, 'SZ.png'),
(206, 206, 'Sweden', 'SE', 'SWE', 752, 'SEK', 'Krona', 'kr', 'SE.png'),
(207, 207, 'Switzerland', 'CH', 'CHE', 756, 'CHF', 'Franc', 'CHF', 'CH.png'),
(208, 208, 'Syria', 'SY', 'SYR', 760, 'SYP', 'Pound', '£', 'SY.png'),
(209, 209, 'Taiwan', 'TW', 'TWN', 158, 'TWD', 'Dollar', 'NT$', 'TW.png'),
(210, 210, 'Tajikistan', 'TJ', 'TJK', 762, 'TJS', 'Somoni', NULL, 'TJ.png'),
(211, 211, 'Tanzania', 'TZ', 'TZA', 834, 'TZS', 'Shilling', NULL, 'TZ.png'),
(212, 212, 'Thailand', 'TH', 'THA', 764, 'THB', 'Baht', '฿', 'TH.png'),
(213, 213, 'Togo', 'TG', 'TGO', 768, 'XOF', 'Franc', NULL, 'TG.png'),
(214, 214, 'Tokelau', 'TK', 'TKL', 772, 'NZD', 'Dollar', '$', 'TK.png'),
(215, 215, 'Tonga', 'TO', 'TON', 776, 'TOP', 'Pa\'anga', 'T$', 'TO.png'),
(216, 216, 'Trinidad and Tobago', 'TT', 'TTO', 780, 'TTD', 'Dollar', 'TT$', 'TT.png'),
(217, 217, 'Tunisia', 'TN', 'TUN', 788, 'TND', 'Dinar', NULL, 'TN.png'),
(218, 218, 'Turkey', 'TR', 'TUR', 792, 'TRY', 'Lira', 'YTL', 'TR.png'),
(219, 219, 'Turkmenistan', 'TM', 'TKM', 795, 'TMM', 'Manat', 'm', 'TM.png'),
(220, 220, 'Turks and Caicos Islands', 'TC', 'TCA', 796, 'USD', 'Dollar', '$', 'TC.png'),
(221, 221, 'Tuvalu', 'TV', 'TUV', 798, 'AUD', 'Dollar', '$', 'TV.png'),
(222, 222, 'U.S. Virgin Islands', 'VI', 'VIR', 850, 'USD', 'Dollar', '$', 'VI.png'),
(223, 223, 'Uganda', 'UG', 'UGA', 800, 'UGX', 'Shilling', NULL, 'UG.png'),
(224, 224, 'Ukraine', 'UA', 'UKR', 804, 'UAH', 'Hryvnia', '₴', 'UA.png'),
(225, 225, 'United Arab Emirates', 'AE', 'ARE', 784, 'AED', 'Dirham', NULL, 'AE.png'),
(226, 226, 'United Kingdom', 'GB', 'GBR', 826, 'GBP', 'Pound', '£', 'GB.png'),
(227, 227, 'United States', 'US', 'USA', 840, 'USD', 'Dollar', '$', 'US.png'),
(228, 228, 'United States Minor Outlying Islands', 'UM', 'UMI', 581, 'USD', 'Dollar ', '$', 'UM.png'),
(229, 229, 'Uruguay', 'UY', 'URY', 858, 'UYU', 'Peso', '$U', 'UY.png'),
(230, 230, 'Uzbekistan', 'UZ', 'UZB', 860, 'UZS', 'Som', 'лв', 'UZ.png'),
(231, 231, 'Vanuatu', 'VU', 'VUT', 548, 'VUV', 'Vatu', 'Vt', 'VU.png'),
(232, 232, 'Vatican', 'VA', 'VAT', 336, 'EUR', 'Euro', '€', 'VA.png'),
(233, 233, 'Venezuela', 'VE', 'VEN', 862, 'VEF', 'Bolivar', 'Bs', 'VE.png'),
(234, 234, 'Vietnam', 'VN', 'VNM', 704, 'VND', 'Dong', '₫', 'VN.png'),
(235, 235, 'Wallis and Futuna', 'WF', 'WLF', 876, 'XPF', 'Franc', NULL, 'WF.png'),
(236, 236, 'Western Sahara', 'EH', 'ESH', 732, 'MAD', 'Dirham', NULL, 'EH.png'),
(237, 237, 'Yemen', 'YE', 'YEM', 887, 'YER', 'Rial', '﷼', 'YE.png'),
(238, 238, 'Zambia', 'ZM', 'ZMB', 894, 'ZMK', 'Kwacha', 'ZK', 'ZM.png'),
(239, 239, 'Zimbabwe', 'ZW', 'ZWE', 716, 'ZWD', 'Dollar', 'Z$', 'ZW.png');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `image` varchar(250) NOT NULL,
  `user_code` varchar(250) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0=>Inactive,1=>Active,2=>delete',
  `otp` int(11) NOT NULL,
  `promo_usage_count` int(11) NOT NULL DEFAULT '0',
  `fcm_token` text NOT NULL,
  `promocode` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `name`, `phone`, `email`, `password`, `image`, `user_code`, `status`, `otp`, `promo_usage_count`, `fcm_token`, `promocode`) VALUES
(1, 'Don Antony', '+919605092881', 'don_user@gmail.com', '25f9e794323b453885f5181f1b624d0b', './assets/uploads/customer/567275212PhotoLaTaxi20180830_143322_.jpg', '', 1, 0, 10, 'cdKIb2LMJWw:APA91bEt3LmgD2zuHBrBvcbCzyYUiQBs6x6qFzfHUHww23yWGxY6WIspYxYFCYrh7w3NKoMaNL4pKUofImAbXHuW-axPwr9_ocDn6ippOjti4tEVIBn-9XEfk3-hZ7WjxrnigH7d_ghM', 'LAOFF'),
(2, 'ayo', '+2347064664695', 'olophaayomide@gmail.com', '44656c67aa24bb2d9636235598cafca7', './assets/uploads/customer/1510755423IMG-20180903-WA0009.jpg', '', 1, 0, 0, 'ewE-LDUNl20:APA91bGHdxjHFppNMiu231USdd_fDXn-dFHRw7pkvX7JkJUHiV6qUd7rFesPKc8BidRfqyLz_yqSboQwQoRqvzFx3zbHdrrAD0MkN31zCbJujvFJKsSyzYJuymPlWUM6iRESV45OPUso', ''),
(3, 'jhon', '+573148515154', 'jhonhpz@gmail.com', '25d55ad283aa400af464c76d713c07ad', './assets/uploads/customer/1269024544IMG-20180905-WA0000.jpg', '', 1, 0, 0, 'dm9JgBkwDVk:APA91bE82imywFSak3xE4KyrdRD1sgBqCIiVBxWygnSeBlGig7YtAthRtpVvQbo-tLFEUexYHWuzRIY1i-opz5ursqYhJnXd2I8qJcgV1tRm5PL4-vyvcvku7iR2SW0TTJpDEhS-coBF', ''),
(4, 'Jhon Peñuela', '+573177533838', 'jhonp@gmail.com', '70ed6657f985f479a5a2542dfc6ac08e', '', '', 1, 0, 0, 'cSlVKEpFhP0:APA91bEz4O2TUw0u0eGWPHKr0Gn-AbzgVZSUrcxp9cPk-2JpoWO9wuspzUqRsHOisGdygI-D_pqZjJx-OYwCK-hbyiMAPrVZEXw98VEwyUbdn2nebgxXMC4PQUkjuPre1RTWYg51uBd0', ''),
(5, 'yousif', '+9647714490331', 'qusaisiso@gmail.com', 'eb97046b01e837770d9c1658bc0e83ee', '', '', 1, 0, 0, 'e88hXDPipwc:APA91bFhywcTLjV0BuUlUOK_GYxvVxigJoP0PpXaQdgJpYKUMi08h3FbOMmLvqH5cGyfrVnPD8cc3Mp25ihOrLNMsXqOtm3DOq49AXemk9_WvHbvbE9QIiFO8BJyHFalex76ooNthoPt', '');

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `id` int(11) NOT NULL,
  `driver_name` varchar(255) NOT NULL,
  `name` varchar(250) NOT NULL,
  `car_id` int(11) NOT NULL,
  `image` varchar(750) NOT NULL,
  `post_code` varchar(50) NOT NULL,
  `vehicle_reg_no` varchar(25) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `address` text NOT NULL,
  `driver_lat` varchar(255) NOT NULL,
  `driver_lng` varchar(255) NOT NULL,
  `license_no` varchar(25) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1=>Active,0=>Inactive,2=>delete',
  `book_status` int(11) NOT NULL COMMENT '1- booking, 2- Processing, 0 - Free/Completed',
  `is_online` int(11) NOT NULL COMMENT '1- Online, 0- Offline',
  `fcm_token` text NOT NULL,
  `driver_type` int(10) NOT NULL COMMENT '0=>Driver Cum Owner,1=>Non - Driving Partner',
  `is_deaf` int(11) NOT NULL COMMENT 'true=>1,false=>0',
  `is_flash_required` int(11) NOT NULL COMMENT 'true=>1,false=>0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver_auth_table`
--

CREATE TABLE `driver_auth_table` (
  `id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `unique_id` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver_document`
--

CREATE TABLE `driver_document` (
  `id` int(10) NOT NULL,
  `driver_id` int(10) NOT NULL,
  `type` varchar(250) NOT NULL,
  `image` varchar(750) NOT NULL,
  `status` varchar(10) DEFAULT '0' COMMENT '0->Not Uploaded,1->Pending Approval,2->Approved,3->Rejected'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver_feedback`
--

CREATE TABLE `driver_feedback` (
  `id` int(11) NOT NULL,
  `driver_rating` varchar(250) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver_online`
--

CREATE TABLE `driver_online` (
  `id` int(10) NOT NULL,
  `driver_id` int(10) NOT NULL,
  `sign_in` varchar(250) NOT NULL,
  `sign_out` varchar(250) NOT NULL,
  `cur_date` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver_request`
--

CREATE TABLE `driver_request` (
  `id` int(11) NOT NULL,
  `req_id` int(11) NOT NULL,
  `req_date` varchar(255) NOT NULL,
  `driver_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `cust_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `rating` double NOT NULL,
  `good_feedback` varchar(255) NOT NULL,
  `bad_feedback` varchar(255) NOT NULL,
  `driver_feedback` longtext NOT NULL,
  `trip_report` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `help_review`
--

CREATE TABLE `help_review` (
  `id` int(10) NOT NULL,
  `driver_id` int(10) NOT NULL,
  `help_id` int(10) NOT NULL,
  `help_status` int(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `help_table`
--

CREATE TABLE `help_table` (
  `id` int(10) NOT NULL,
  `image` varchar(250) NOT NULL,
  `head` varchar(250) NOT NULL,
  `content` longtext NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1=>Active,0=>Inactive'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `keys`
--

CREATE TABLE `keys` (
  `id` int(11) NOT NULL,
  `security_key` varchar(40) NOT NULL,
  `level` int(2) NOT NULL,
  `ignore_limits` tinyint(1) DEFAULT '0',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `cust_id` int(11) NOT NULL,
  `home` varchar(250) NOT NULL,
  `work` varchar(250) NOT NULL,
  `work_latitude` int(25) NOT NULL,
  `work_longitude` int(25) NOT NULL,
  `home_latitude` int(25) NOT NULL,
  `home_longitude` int(25) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pattern`
--

CREATE TABLE `pattern` (
  `id` int(11) NOT NULL,
  `pattern_name` varchar(250) NOT NULL,
  `car_name` varchar(100) NOT NULL,
  `car_type` int(11) NOT NULL,
  `location` varchar(100) NOT NULL,
  `range` varchar(100) NOT NULL,
  `base_price` int(50) NOT NULL,
  `km_rate` int(50) NOT NULL,
  `min_rate` int(50) NOT NULL,
  `lat` varchar(100) NOT NULL,
  `lng` varchar(100) NOT NULL,
  `currency` varchar(10) CHARACTER SET utf8 NOT NULL,
  `status` int(11) DEFAULT '1' COMMENT '1=>Active,0=>Inactive, 2=>Delete'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `promocode`
--

CREATE TABLE `promocode` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `start_date` date NOT NULL,
  `expiration_date` date NOT NULL,
  `type` int(11) NOT NULL DEFAULT '2' COMMENT '1=>percentage,2=>fixed',
  `off` int(11) NOT NULL,
  `promo_type` int(11) NOT NULL COMMENT '1=>perperson,2=>broadcast',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1=>Active,0=>Inactive',
  `promo_usage` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `id` int(11) NOT NULL,
  `rating` int(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `cust_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `car_type` int(10) NOT NULL,
  `source` varchar(250) NOT NULL,
  `destination` varchar(250) NOT NULL,
  `source_lat` varchar(250) NOT NULL,
  `source_lng` varchar(250) NOT NULL,
  `destination_lat` varchar(250) NOT NULL,
  `destination_lng` varchar(250) NOT NULL,
  `status` int(11) NOT NULL COMMENT '0 - Pending, 1 - Assigned, 2 - Request Failed,3 - Request Cancelled',
  `trip_id` varchar(50) NOT NULL,
  `pattern_id` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(10) NOT NULL,
  `role` varchar(750) NOT NULL,
  `permission` varchar(750) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `service_log`
--

CREATE TABLE `service_log` (
  `id` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parameter` longtext,
  `auth` varchar(255) DEFAULT NULL,
  `result` text,
  `time` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `logo` varchar(500) NOT NULL,
  `favicon` varchar(500) NOT NULL,
  `tax` int(10) NOT NULL,
  `admin_charge` int(50) NOT NULL,
  `booking_code` varchar(100) NOT NULL,
  `key` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`id`, `title`, `logo`, `favicon`, `tax`, `admin_charge`, `booking_code`, `key`) VALUES
(1, 'LaTaxi', 'assets/uploads/settings//1500894921.png', '', 0, 10, 'LTXI', 'AIzaSyCkUU9_wA4o6j0300MLDmKREwNhoJAAzM8');

-- --------------------------------------------------------

--
-- Table structure for table `trip_feedback`
--

CREATE TABLE `trip_feedback` (
  `id` int(11) NOT NULL,
  `trip_feedback` varchar(250) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_promotion`
--

CREATE TABLE `user_promotion` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `promotion_code` int(50) NOT NULL,
  `use_ltd` int(25) NOT NULL,
  `default` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admin_users`
--
ALTER TABLE `admin_users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `auth_table`
--
ALTER TABLE `auth_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `car_type`
--
ALTER TABLE `car_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `charge_condition`
--
ALTER TABLE `charge_condition`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_auth_table`
--
ALTER TABLE `driver_auth_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_document`
--
ALTER TABLE `driver_document`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_online`
--
ALTER TABLE `driver_online`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_request`
--
ALTER TABLE `driver_request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `help_review`
--
ALTER TABLE `help_review`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `help_table`
--
ALTER TABLE `help_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `keys`
--
ALTER TABLE `keys`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indexes for table `pattern`
--
ALTER TABLE `pattern`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `promocode`
--
ALTER TABLE `promocode`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_log`
--
ALTER TABLE `service_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trip_feedback`
--
ALTER TABLE `trip_feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_promotion`
--
ALTER TABLE `user_promotion`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `admin_users`
--
ALTER TABLE `admin_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `auth_table`
--
ALTER TABLE `auth_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `car`
--
ALTER TABLE `car`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `car_type`
--
ALTER TABLE `car_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `charge_condition`
--
ALTER TABLE `charge_condition`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `countries`
--
ALTER TABLE `countries`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=240;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver_auth_table`
--
ALTER TABLE `driver_auth_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver_document`
--
ALTER TABLE `driver_document`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver_online`
--
ALTER TABLE `driver_online`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver_request`
--
ALTER TABLE `driver_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `help_review`
--
ALTER TABLE `help_review`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `help_table`
--
ALTER TABLE `help_table`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `keys`
--
ALTER TABLE `keys`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `cust_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pattern`
--
ALTER TABLE `pattern`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `promocode`
--
ALTER TABLE `promocode`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rating`
--
ALTER TABLE `rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `service_log`
--
ALTER TABLE `service_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `trip_feedback`
--
ALTER TABLE `trip_feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_promotion`
--
ALTER TABLE `user_promotion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
