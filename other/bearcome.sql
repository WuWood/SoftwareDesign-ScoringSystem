-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2021-02-25 07:47:24
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
CREATE DATABASE IF NOT EXISTS `bearcome` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bearcome`;

-- --------------------------------------------------------

--
-- 表的结构 `competitor`
--

CREATE TABLE `competitor` (
  `userid` int(16) NOT NULL,
  `partake` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `competitor`
--

INSERT INTO `competitor` (`userid`, `partake`) VALUES
(1, '3;');

-- --------------------------------------------------------

--
-- 表的结构 `contest`
--

CREATE TABLE `contest` (
  `id` int(16) NOT NULL,
  `name` text NOT NULL,
  `description` text NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `maxmembers` int(16) NOT NULL,
  `currentmembers` int(16) NOT NULL,
  `userid` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `contest`
--

INSERT INTO `contest` (`id`, `name`, `description`, `starttime`, `endtime`, `maxmembers`, `currentmembers`, `userid`) VALUES
(1, 'test', 'hello', '2021-02-21 20:39:35', '2021-02-22 20:39:35', 10, 7, ''),
(2, 'test2', 'hey', '2021-02-23 20:40:06', '2021-02-24 20:40:06', 10, 6, ''),
(3, '1', '1', '2021-02-25 14:38:17', '2021-07-01 14:38:17', 100, 2, '1;');

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE `users` (
  `id` int(16) NOT NULL,
  `name` text NOT NULL,
  `password` text NOT NULL,
  `wechatcode` text NOT NULL,
  `level` int(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
