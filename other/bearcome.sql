-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2021-03-11 14:57:20
-- 服务器版本： 10.4.17-MariaDB
-- PHP 版本： 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `bearcome`
--
CREATE DATABASE IF NOT EXISTS `bearcome` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `bearcome`;

-- --------------------------------------------------------

--
-- 表的结构 `competitor`
--

CREATE TABLE IF NOT EXISTS `competitor` (
  `userid` int(16) NOT NULL,
  `partake` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`score`)),
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `competitor`
--

REPLACE INTO `competitor` (`userid`, `partake`, `score`) VALUES
(1, '3;', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `contest`
--

CREATE TABLE IF NOT EXISTS `contest` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `maxmembers` int(16) NOT NULL,
  `currentmembers` int(16) NOT NULL DEFAULT 0,
  `userid` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creatorid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `judegeid` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `jLock` tinyint(1) NOT NULL DEFAULT 0,
  `cLock` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `contest`
--

REPLACE INTO `contest` (`id`, `name`, `description`, `starttime`, `endtime`, `maxmembers`, `currentmembers`, `userid`, `creatorid`, `judegeid`, `jLock`, `cLock`) VALUES
(1, 'test', 'hello', '2021-02-21 20:39:35', '2021-02-22 20:39:35', 10, 7, '', '', '1', 0, 0),
(2, 'test2', 'hey', '2021-02-23 20:40:06', '2021-02-24 20:40:06', 10, 6, '', '', '1', 0, 0),
(3, '1', '1', '2021-02-25 14:38:17', '2021-07-01 14:38:17', 100, 2, '1;', '', '1', 0, 0),
(4, '123123', '123131', '2021-03-04 13:53:17', '2021-03-05 13:53:17', 10, 0, NULL, '', '1', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `examine`
--

CREATE TABLE IF NOT EXISTS `examine` (
  `userid` int(16) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `judge`
--

CREATE TABLE IF NOT EXISTS `judge` (
  `userid` int(16) NOT NULL,
  `partake` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `createpartake` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `WeChatCode` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `level` int(11) NOT NULL,
  `nickname` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `users`
--

REPLACE INTO `users` (`userid`, `name`, `password`, `WeChatCode`, `level`, `nickname`) VALUES
(1, 'test', '098f6bcd4621d373cade4e832627b4f6', '', 1, ''),
(4, 'judge', '1562eb3f6d9c5ac7e159c04a96ff4dfe', NULL, 2, 'judge');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
