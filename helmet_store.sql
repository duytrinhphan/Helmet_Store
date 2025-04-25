-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: datn
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `noi_dung` varchar(10000) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  `id_phanhoi` int DEFAULT NULL,
  `id_san_pham` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd8074xyo14wtlxe0uoqi3492j` (`id_user`),
  KEY `id_phanhoi` (`id_phanhoi`),
  KEY `id_san_pham` (`id_san_pham`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`id_phanhoi`) REFERENCES `comment` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id`),
  CONSTRAINT `FKd8074xyo14wtlxe0uoqi3492j` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dia_chi`
--

DROP TABLE IF EXISTS `dia_chi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dia_chi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(10000) DEFAULT NULL,
  `id_quan_huyen` int DEFAULT NULL,
  `id_tinh_thanh` int DEFAULT NULL,
  `id_phuong_xa` varchar(255) DEFAULT NULL,
  `loai_dia_chi` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten_quan_huyen` varchar(255) DEFAULT NULL,
  `ten_tinh_thanh` varchar(255) DEFAULT NULL,
  `ten_phuong_xa` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbfv0wiqy3kyntfe72a952m3ym` (`id_user`),
  CONSTRAINT `FKbfv0wiqy3kyntfe72a952m3ym` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_chi`
--

LOCK TABLES `dia_chi` WRITE;
/*!40000 ALTER TABLE `dia_chi` DISABLE KEYS */;
INSERT INTO `dia_chi` VALUES (3,'Thọ Hải, Thọ Xuân',1768,249,'190211','Nhà riêng','2023-07-10','2023-05-13','Yên Phong','Bắc Ninh','Trung Nghĩa',1,3),(7,'thôn 1',2157,267,'800046',NULL,NULL,NULL,'Huyện Lạc Thủy','Hòa Bình','Thị Trấn Ba Hàng Đồi',1,2),(35,'thôn 1',2157,267,'800046',NULL,NULL,NULL,'Huyện Lạc Thủy','Hòa Bình','Thị Trấn Ba Hàng Đồi',1,10001);
/*!40000 ALTER TABLE `dia_chi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gio_hang`
--

DROP TABLE IF EXISTS `gio_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gio_hang` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpvcogldmug8cho628f0m28em0` (`id_user`),
  CONSTRAINT `FKpvcogldmug8cho628f0m28em0` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gio_hang`
--

LOCK TABLES `gio_hang` WRITE;
/*!40000 ALTER TABLE `gio_hang` DISABLE KEYS */;
INSERT INTO `gio_hang` VALUES (3,'GH8976',NULL,'2023/12/07',NULL,10001),(4,'GH6160',NULL,'2023/12/08',NULL,3),(5,'GH7892',NULL,'2023/12/08',NULL,2),(6,'GH9413',NULL,'2023/12/08',NULL,30);
/*!40000 ALTER TABLE `gio_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gio_hang_chi_tiet`
--

DROP TABLE IF EXISTS `gio_hang_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gio_hang_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `don_gia` decimal(20,0) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_gio_hang` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkt2s807w7uf9vgc64x6r3cl2n` (`id_gio_hang`),
  KEY `FKjrkuss0lgfn76maw426puheeu` (`id_san_pham_chi_tiet`),
  CONSTRAINT `FKjrkuss0lgfn76maw426puheeu` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`),
  CONSTRAINT `FKkt2s807w7uf9vgc64x6r3cl2n` FOREIGN KEY (`id_gio_hang`) REFERENCES `gio_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gio_hang_chi_tiet`
--

LOCK TABLES `gio_hang_chi_tiet` WRITE;
/*!40000 ALTER TABLE `gio_hang_chi_tiet` DISABLE KEYS */;
INSERT INTO `gio_hang_chi_tiet` VALUES (12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,1360000,'GHCT6160',NULL,'2023/12/08',22,NULL,4,7),(15,1440000,'GHCT2521',NULL,'2023/12/08',1,NULL,4,17),(16,1360000,'GHCT7892',NULL,'2023/12/08',30,NULL,5,7),(19,1680000,'GHCT9413',NULL,'2023/12/08',1,NULL,6,20),(22,1360000,'GHCT2579',NULL,'2023/12/08',1,NULL,3,7);
/*!40000 ALTER TABLE `gio_hang_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hinh_thuc_giao_hang` int DEFAULT NULL,
  `ly_do` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `ngay_nhan` datetime(6) DEFAULT NULL,
  `ngay_ship` datetime(6) DEFAULT NULL,
  `ngay_sua` datetime(6) DEFAULT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `ngay_thanh_toan` datetime(6) DEFAULT NULL,
  `ten_nguoi_nhan` varchar(255) DEFAULT NULL,
  `tien_sau_khi_giam_gia` decimal(20,0) DEFAULT NULL,
  `tien_ship` decimal(20,0) DEFAULT NULL,
  `tong_tien` decimal(20,0) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_dia_chi_sdt` int DEFAULT NULL,
  `id_phuong_thuc_thanh_toan` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  `id_nguoi_tao` int DEFAULT NULL,
  `tien_khach_dua` decimal(20,0) DEFAULT NULL,
  `id_voucher` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3hpv2auavm1xw5cm0m7f0g8eu` (`id_dia_chi_sdt`),
  KEY `FKi74bvrg7meqbrhpwk8ff6o8xi` (`id_phuong_thuc_thanh_toan`),
  KEY `FKm5hgwxf6p05vqdw5ptm19p0lj` (`id_user`),
  KEY `id_voucher` (`id_voucher`),
  CONSTRAINT `FK3hpv2auavm1xw5cm0m7f0g8eu` FOREIGN KEY (`id_dia_chi_sdt`) REFERENCES `dia_chi` (`id`),
  CONSTRAINT `FKi74bvrg7meqbrhpwk8ff6o8xi` FOREIGN KEY (`id_phuong_thuc_thanh_toan`) REFERENCES `phuong_thuc_thanh_toan` (`id`),
  CONSTRAINT `FKm5hgwxf6p05vqdw5ptm19p0lj` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `hoa_don_ibfk_1` FOREIGN KEY (`id_voucher`) REFERENCES `voucher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES (32,1,NULL,'HD32','aaa',NULL,NULL,NULL,'2023-11-26 17:46:18.956560','2023-11-27 02:30:09.218935',NULL,NULL,NULL,1500000,3,NULL,1,3,2,2000000,NULL),(33,1,NULL,'HD33','xxx',NULL,NULL,NULL,'2023-11-27 02:02:58.453051','2023-11-27 02:45:25.125917',NULL,NULL,NULL,2800000,3,NULL,1,3,2,3000000,NULL),(34,1,NULL,'HD34','xxx',NULL,NULL,NULL,'2023-11-27 02:44:19.757691','2023-11-27 02:46:45.831117',NULL,2800000,NULL,3500000,3,NULL,1,3,2,2800000,NULL),(35,1,NULL,'HD35',NULL,NULL,NULL,NULL,'2023-11-27 02:48:12.433613','2023-12-11 18:41:29.324151','John Doe',4280000,0,4600000,3,NULL,1,8,2,5000000,NULL),(40,2,NULL,'HD40',NULL,NULL,NULL,NULL,'2023-12-02 11:35:15.126926','2023-12-08 06:29:38.344633','Lê Văn Cờ',3000000,0,3000000,3,3,1,3,7,3000000,NULL),(41,2,NULL,'HD6262',NULL,NULL,NULL,NULL,'2023-12-03 06:43:16.252555',NULL,'Lê Văn Cờ',NULL,20501,2600000,2,3,2,3,NULL,NULL,NULL),(42,2,NULL,'HD2533',NULL,NULL,NULL,NULL,'2023-12-03 06:43:18.438622',NULL,'Lê Văn Cờ',NULL,20501,2600000,2,3,2,3,NULL,NULL,NULL),(43,2,NULL,'HD6572',NULL,NULL,NULL,NULL,'2023-12-03 06:43:18.936864',NULL,'Lê Văn Cờ',NULL,20501,2600000,2,3,2,3,NULL,NULL,NULL),(44,2,NULL,'HD5335',NULL,NULL,NULL,NULL,'2023-12-03 06:43:19.110022',NULL,'Lê Văn Cờ',NULL,20501,2600000,2,3,2,3,NULL,NULL,NULL),(45,2,NULL,'HD6537',NULL,NULL,NULL,NULL,'2023-12-03 06:48:48.669260',NULL,'Lê Văn Cờ',NULL,20501,2600000,2,3,2,3,NULL,NULL,NULL),(46,2,NULL,'HD7828',NULL,NULL,NULL,NULL,'2023-12-03 06:51:47.230882',NULL,'Lê Văn Cờ',NULL,20501,3900000,2,3,2,3,NULL,NULL,NULL),(47,2,NULL,'HD8419',NULL,NULL,NULL,NULL,'2023-12-03 06:53:48.923918',NULL,'Lê Văn Cờ',NULL,20501,8000000,2,3,2,3,NULL,NULL,NULL),(48,2,NULL,'HD6001',NULL,NULL,NULL,NULL,'2023-12-03 06:55:33.727844',NULL,'Lê Văn Cờ',NULL,20501,2080000,2,3,2,3,NULL,NULL,NULL),(49,2,NULL,'HD1250',NULL,NULL,NULL,NULL,'2023-12-03 06:56:28.121270','2023-12-03 06:56:28.121270','Lê Văn Cờ',NULL,20501,2600000,2,3,1,3,NULL,NULL,NULL),(50,2,NULL,'HD7449',NULL,NULL,NULL,NULL,'2023-12-03 06:56:53.325432',NULL,'Lê Văn Cờ',NULL,20501,1360000,2,3,2,3,NULL,NULL,NULL),(51,2,NULL,'HD6252',NULL,NULL,NULL,NULL,'2023-12-03 06:56:57.050735','2023-12-03 06:56:57.050735','Lê Văn Cờ',NULL,20501,2600000,2,3,1,3,NULL,NULL,NULL),(52,2,NULL,'HD6610',NULL,NULL,NULL,NULL,'2023-12-03 06:57:24.938603','2023-12-03 06:57:24.938603','Lê Văn Cờ',NULL,20501,2600000,2,3,1,3,NULL,NULL,NULL),(53,2,NULL,'HD5372',NULL,NULL,NULL,NULL,'2023-12-03 06:57:49.432123','2023-12-03 06:57:49.432123','Lê Văn Cờ',NULL,20501,2600000,2,3,1,3,NULL,NULL,NULL),(54,2,NULL,'HD3049',NULL,NULL,NULL,NULL,'2023-12-03 07:00:06.676493',NULL,'Lê Văn Cờ',NULL,20501,7680000,2,3,2,3,NULL,NULL,NULL),(55,2,NULL,'HD3621',NULL,NULL,NULL,NULL,'2023-12-03 07:01:11.749238',NULL,'Lê Văn Cờ',NULL,20501,4560000,2,3,2,3,NULL,NULL,NULL),(56,2,NULL,'HD7519',NULL,NULL,NULL,NULL,'2023-12-03 07:01:16.026380','2023-12-03 07:01:16.026380','Lê Văn C',2500000,20501,2600000,2,3,1,3,NULL,NULL,1),(57,2,NULL,'HD6125',NULL,NULL,'2023-12-12 06:12:00.000000','2023-12-07 23:13:17.746410','2023-12-03 07:07:14.210627','2023-12-03 07:07:14.210627','Lê Văn Cờ',NULL,20501,2600000,5,3,1,3,NULL,NULL,NULL),(61,2,NULL,'HD4837',NULL,'2023-12-08 21:42:50.463358','2023-12-12 21:34:00.000000','2023-12-08 21:42:50.463358','2023-12-08 21:26:23.196068',NULL,'vinh nguyễn',1360000,51502,1360000,3,35,2,10001,NULL,NULL,NULL),(62,1,NULL,'HD62',NULL,NULL,NULL,NULL,'2023-12-11 16:06:43.734384','2023-12-11 18:40:54.661612','Nguyễn Văn Mẹc Si',3040000,0,3800000,3,NULL,1,6,7,4500000,NULL),(63,1,NULL,'HD63',NULL,NULL,NULL,NULL,'2023-12-11 16:09:36.591635','2023-12-11 18:42:34.940513','Rô Nan Đô',2800000,0,3500000,3,NULL,1,4,7,3000000,NULL),(64,NULL,NULL,'HD64',NULL,NULL,NULL,NULL,'2023-12-11 18:44:09.610448',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,10,7,NULL,NULL),(65,NULL,NULL,'HD65',NULL,NULL,NULL,NULL,'2023-12-11 18:56:53.847267',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,1,7,NULL,NULL);
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don_chi_tiet`
--

DROP TABLE IF EXISTS `hoa_don_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `don_gia` decimal(20,0) DEFAULT NULL,
  `ly_do` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_hoa_don` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  `chiet_khau` decimal(20,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3igy4tfvmm2b6mypd176k4948` (`id_hoa_don`),
  KEY `FKmm0mt4gwrghnll65uq9b5b4ox` (`id_san_pham_chi_tiet`),
  CONSTRAINT `FK3igy4tfvmm2b6mypd176k4948` FOREIGN KEY (`id_hoa_don`) REFERENCES `hoa_don` (`id`),
  CONSTRAINT `FKmm0mt4gwrghnll65uq9b5b4ox` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_chi_tiet`
--

LOCK TABLES `hoa_don_chi_tiet` WRITE;
/*!40000 ALTER TABLE `hoa_don_chi_tiet` DISABLE KEYS */;
INSERT INTO `hoa_don_chi_tiet` VALUES (50,1500000,NULL,NULL,NULL,NULL,1,NULL,32,1,0),(55,1500000,NULL,NULL,NULL,NULL,1,NULL,33,1,0),(56,1300000,NULL,NULL,NULL,NULL,1,NULL,33,2,0),(57,1800000,NULL,NULL,NULL,NULL,1,NULL,34,3,360000),(60,1700000,NULL,NULL,NULL,NULL,1,NULL,34,7,340000),(68,1300000,NULL,'HDCT6262',NULL,NULL,2,2,41,2,NULL),(69,1300000,NULL,'HDCT2533',NULL,NULL,2,2,42,2,NULL),(70,1300000,NULL,'HDCT6572',NULL,NULL,2,2,43,2,NULL),(71,1300000,NULL,'HDCT5335',NULL,NULL,2,2,44,2,NULL),(72,1300000,NULL,'HDCT6537',NULL,NULL,2,2,45,2,NULL),(73,1300000,NULL,'HDCT7828',NULL,NULL,3,2,46,2,NULL),(74,1600000,NULL,'HDCT8419',NULL,NULL,5,2,47,5,NULL),(75,1040000,NULL,'HDCT6001',NULL,NULL,2,2,48,12,NULL),(76,1300000,NULL,'HDCT1250',NULL,NULL,2,2,49,2,NULL),(77,1360000,NULL,'HDCT7449',NULL,NULL,1,2,50,7,NULL),(78,1300000,NULL,'HDCT6252',NULL,NULL,2,2,51,2,NULL),(79,1300000,NULL,'HDCT6610',NULL,NULL,2,2,52,2,NULL),(80,1300000,NULL,'HDCT5372',NULL,NULL,2,2,53,2,NULL),(81,1280000,NULL,'HDCT3049',NULL,NULL,6,2,54,15,NULL),(82,1520000,NULL,'HDCT3621',NULL,NULL,3,2,55,9,NULL),(83,1300000,NULL,'HDCT7519',NULL,NULL,2,2,56,2,NULL),(84,1300000,NULL,'HDCT6125',NULL,NULL,2,5,57,2,NULL),(86,1500000,NULL,NULL,NULL,NULL,2,NULL,40,1,0),(90,1360000,NULL,'HDCT4837',NULL,NULL,1,3,61,7,NULL),(92,1500000,NULL,NULL,NULL,NULL,2,NULL,35,1,0),(95,1600000,NULL,NULL,NULL,NULL,1,NULL,35,6,320000),(96,1900000,NULL,NULL,NULL,NULL,2,NULL,62,9,380000),(98,1500000,NULL,NULL,NULL,NULL,1,NULL,63,14,300000),(99,2000000,NULL,NULL,NULL,NULL,1,NULL,63,19,400000),(100,1500000,NULL,NULL,NULL,NULL,2,NULL,64,1,0);
/*!40000 ALTER TABLE `hoa_don_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `anh` varchar(10000) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_san_pham` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa4gihn9sp5ngnuwb70tce2nxx` (`id_san_pham`),
  CONSTRAINT `FKa4gihn9sp5ngnuwb70tce2nxx` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,NULL,NULL,NULL,NULL,1,1),(2,NULL,NULL,NULL,NULL,1,1),(3,NULL,NULL,NULL,NULL,1,1),(4,NULL,NULL,NULL,NULL,1,1),(5,NULL,NULL,NULL,NULL,1,2),(6,NULL,NULL,NULL,NULL,1,2),(7,NULL,NULL,NULL,NULL,1,2),(8,NULL,NULL,NULL,NULL,1,2),(9,NULL,NULL,NULL,NULL,1,2),(10,NULL,NULL,NULL,NULL,1,3),(11,NULL,NULL,NULL,NULL,1,3),(12,NULL,NULL,NULL,NULL,1,3),(13,NULL,NULL,NULL,NULL,1,3),(15,NULL,NULL,NULL,NULL,1,3),(16,NULL,NULL,NULL,NULL,1,4),(17,NULL,NULL,NULL,NULL,1,4),(18,NULL,NULL,NULL,NULL,1,4),(19,NULL,NULL,NULL,NULL,1,4),(20,NULL,NULL,NULL,NULL,1,4),(21,NULL,NULL,NULL,NULL,1,10),(22,NULL,NULL,NULL,NULL,1,10),(23,NULL,NULL,NULL,NULL,1,10),(24,NULL,NULL,NULL,NULL,1,20),(25,NULL,NULL,NULL,NULL,1,21),(26,NULL,NULL,NULL,NULL,1,22),(27,NULL,NULL,NULL,NULL,1,22),(28,NULL,NULL,NULL,NULL,1,22),(29,NULL,NULL,NULL,NULL,1,22),(30,NULL,NULL,NULL,NULL,1,22),(31,NULL,NULL,NULL,NULL,1,23),(32,NULL,NULL,NULL,NULL,1,23),(33,NULL,NULL,NULL,NULL,1,23),(34,NULL,NULL,NULL,NULL,1,23),(35,NULL,NULL,NULL,NULL,1,23),(36,NULL,NULL,NULL,NULL,1,17),(37,NULL,NULL,NULL,NULL,1,17),(38,NULL,NULL,NULL,NULL,1,17),(39,NULL,NULL,NULL,NULL,1,17),(40,NULL,NULL,NULL,NULL,1,17),(41,NULL,NULL,NULL,NULL,1,31),(42,NULL,NULL,NULL,NULL,1,31),(43,NULL,NULL,NULL,NULL,NULL,31),(44,NULL,NULL,NULL,NULL,NULL,31),(45,NULL,NULL,NULL,NULL,NULL,31),(46,NULL,NULL,NULL,NULL,NULL,32),(47,NULL,NULL,NULL,NULL,NULL,32),(48,NULL,NULL,NULL,NULL,NULL,32),(49,NULL,NULL,NULL,NULL,NULL,32),(50,NULL,NULL,NULL,NULL,NULL,35),(51,NULL,NULL,NULL,NULL,NULL,35),(52,NULL,NULL,NULL,NULL,NULL,35),(53,NULL,NULL,NULL,NULL,NULL,35),(54,NULL,NULL,NULL,NULL,NULL,35),(55,NULL,NULL,NULL,NULL,NULL,37),(56,NULL,NULL,NULL,NULL,NULL,34),(57,NULL,NULL,NULL,NULL,NULL,34),(58,NULL,NULL,NULL,NULL,NULL,34),(59,NULL,NULL,NULL,NULL,NULL,34),(60,NULL,NULL,NULL,NULL,NULL,34);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyen_mai`
--

DROP TABLE IF EXISTS `khuyen_mai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyen_mai` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dieu_kien` decimal(20,0) DEFAULT NULL,
  `gia_tri_giam` int DEFAULT NULL,
  `giam_toi_da` decimal(20,0) DEFAULT NULL,
  `kieu_giam_gia` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` datetime(6) DEFAULT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoi_gian_bat_dau` datetime(6) DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime(6) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyen_mai`
--

LOCK TABLES `khuyen_mai` WRITE;
/*!40000 ALTER TABLE `khuyen_mai` DISABLE KEYS */;
INSERT INTO `khuyen_mai` VALUES (1,NULL,5,NULL,NULL,'KM1','giảm 5% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-11-23 00:00:00.288154','2023-10-24 00:00:00.000000',100,'giảm 5%','2023-11-15 00:00:00.000000','2024-11-23 00:00:00.000000',0),(2,NULL,15,NULL,NULL,'KM2','giảm 15% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-11-23 00:00:00.388156','2023-09-24 00:00:00.000000',80,'giảm 15%','2023-10-15 00:00:00.000000','2024-11-23 00:00:00.000000',0),(3,NULL,20,NULL,NULL,'KM2','giảm 20% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-11-20 00:00:00.198751','2023-06-24 00:00:00.000000',60,'giảm 20%','2023-07-15 00:00:00.000000','2024-07-23 00:00:00.000000',0),(4,NULL,25,NULL,NULL,'KM4','giảm 25% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-09-10 00:00:00.000000','2023-08-24 00:00:00.000000',400,'giảm 25%','2023-09-15 00:00:00.000000','2024-09-23 00:00:00.000000',0);
/*!40000 ALTER TABLE `khuyen_mai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai`
--

DROP TABLE IF EXISTS `loai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai`
--

LOCK TABLES `loai` WRITE;
/*!40000 ALTER TABLE `loai` DISABLE KEYS */;
INSERT INTO `loai` VALUES (1,'L1','2023-09-10','2023-09-05','3/4 đầu',1),(2,'L2','2023-09-15','2023-09-10','Fullface',1),(3,'L3','2023-09-23','2023-09-19','1/2 đầu',1),(4,'L4','2023-09-24','2023-09-20','Xe đạp',1),(5,'L5','2023-08-10','2023-07-25','Trẻ em',1),(6,'L6','2023-07-19','2023-07-15','Lật cằm',1);
/*!40000 ALTER TABLE `loai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mau_sac`
--

DROP TABLE IF EXISTS `mau_sac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mau_sac` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mau_sac`
--

LOCK TABLES `mau_sac` WRITE;
/*!40000 ALTER TABLE `mau_sac` DISABLE KEYS */;
INSERT INTO `mau_sac` VALUES (1,'MS1',NULL,'2023-05-05','2023-04-13','Vàng',1),(2,'MS2',NULL,'2023-05-05','2023-04-13','Đen',1),(3,'MS3',NULL,'2023-05-05','2023-04-13','Trắng',1),(4,'MS4',NULL,'2023-05-05','2023-04-13','Xanh',1),(5,'MS5',NULL,'2023-05-05','2023-04-13','Đỏ',1),(6,'MS6',NULL,'2023-05-05','2023-04-13','Hồng',1),(7,'MS7',NULL,'2023-05-05','2023-04-13','Rêu',1),(8,'MS8',NULL,'2023-05-05','2023-04-13','Sữa',1);
/*!40000 ALTER TABLE `mau_sac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phuong_thuc_thanh_toan`
--

DROP TABLE IF EXISTS `phuong_thuc_thanh_toan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phuong_thuc_thanh_toan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phuong_thuc_thanh_toan`
--

LOCK TABLES `phuong_thuc_thanh_toan` WRITE;
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` DISABLE KEYS */;
INSERT INTO `phuong_thuc_thanh_toan` VALUES (1,'PTTT1',NULL,'2023-11-17 12:50:00','Tiền mặt',0),(2,'PTTT2',NULL,'2023-11-17 12:50:00','Chuyển khoản',0);
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reset_password_token`
--

DROP TABLE IF EXISTS `reset_password_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reset_password_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `thoi_gian_tao` datetime(6) DEFAULT NULL,
  `thoi_gian_het_han` datetime(6) DEFAULT NULL,
  `hieu_luc` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reset_password_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reset_password_token`
--

LOCK TABLES `reset_password_token` WRITE;
/*!40000 ALTER TABLE `reset_password_token` DISABLE KEYS */;
INSERT INTO `reset_password_token` VALUES (1,17,'1702387360308-e4a212fd-c02f-4a14-a006-71e1bcd0e208','2023-12-12 20:22:40.309706','2023-12-12 20:37:40.309706',0),(2,17,'1702387487699-f3899666-7d00-4c89-8e49-eb6aa512e427','2023-12-12 20:24:47.700425','2023-12-12 20:39:47.700425',0),(3,17,'1702387525985-cc07e8dc-31d9-4f49-a8d3-7ad75b39e168','2023-12-12 20:25:25.986969','2023-12-12 20:40:25.986969',0),(4,17,'1702387553469-08ba172d-f43e-496a-a154-93d9bc5068a2','2023-12-12 20:25:53.469236','2023-12-12 20:40:53.469236',0),(5,17,'1702388287441-28f2c6ce-3b95-4780-82f9-306439382888','2023-12-12 20:38:07.442136','2023-12-12 20:53:07.442136',0),(6,17,'1702388336560-dea25d2e-41f6-4e84-881a-9c46ce1fcdc4','2023-12-12 20:38:56.562094','2023-12-12 20:53:56.562094',0),(7,17,'1702388548224-dd7f4cc4-84a5-4a6b-aaa3-e20d2a034eb0','2023-12-12 20:42:28.226947','2023-12-12 20:57:28.226947',0),(8,17,'1702389039724-d34a1ae2-94ea-4950-8a97-5686b735af3d','2023-12-12 20:50:39.725582','2023-12-12 21:05:39.725582',0),(9,17,'1702400652067-99dedc88-f1cb-4f92-86fa-c64ca4cb1fef','2023-12-13 00:04:12.068480','2023-12-13 00:19:12.068480',0),(10,17,'1702400690375-dbf85ad8-6b88-4c31-9371-b87865c2ffa1','2023-12-13 00:04:50.375369','2023-12-13 00:19:50.375369',0),(11,17,'1702401503419-15a21f39-8762-4f11-9db1-177e939fce04','2023-12-13 00:18:23.420231','2023-12-13 00:33:23.420231',1);
/*!40000 ALTER TABLE `reset_password_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham`
--

DROP TABLE IF EXISTS `san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham` (
  `id` int NOT NULL AUTO_INCREMENT,
  `anh` varchar(255) DEFAULT NULL,
  `dem_lot` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `quai_deo` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_loai` int DEFAULT NULL,
  `id_thuong_hieu` int DEFAULT NULL,
  `id_vat_lieu` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ietyty0m29mftr3yaff0v4wb` (`id_loai`),
  KEY `FKng0be3yah8qweo3tnmxm9pnrw` (`id_thuong_hieu`),
  KEY `FKk978ncnc6kbpaj5ugxf5y6ia3` (`id_vat_lieu`),
  CONSTRAINT `FK3ietyty0m29mftr3yaff0v4wb` FOREIGN KEY (`id_loai`) REFERENCES `loai` (`id`),
  CONSTRAINT `FKk978ncnc6kbpaj5ugxf5y6ia3` FOREIGN KEY (`id_vat_lieu`) REFERENCES `vat_lieu` (`id`),
  CONSTRAINT `FKng0be3yah8qweo3tnmxm9pnrw` FOREIGN KEY (`id_thuong_hieu`) REFERENCES `thuong_hieu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham`
--

LOCK TABLES `san_pham` WRITE;
/*!40000 ALTER TABLE `san_pham` DISABLE KEYS */;
INSERT INTO `san_pham` VALUES (1,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/klIZba_MG_3349.jpg','Vải','SP1','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M20D',1,1,9,1),(2,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/b4hEP5SwGTU0royal-m139-v7.jpg','Vải','SP2','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M139 DESIGN',1,1,9,2),(3,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/of3MY8_MG_8636.jpg','Vải','SP3','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M139',1,1,8,3),(4,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xKHMALIMG_5434.jpg','Vải','SP4','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M268',1,1,7,3),(10,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/lANmFe_MG_9655.jpg','Vật liệu mềm','SP10','Bền đẹp, bảo đảm an toàn khi tham gia giao thông','2023/12/17','2023-12-17 11:08:55','Quai đeo đặc biệt','M134C',1,1,1,3),(17,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9th8nxIMG_4967.jpg','Vải','SP17','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M141K',1,2,8,3),(20,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CRTP8N139B-den-bong.jpg','Da','SP20','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Da','M138B',1,2,5,2),(21,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CRTP8N139B-den-bong.jpg','Vải','SP21','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M266 DESIGN',1,2,8,2),(22,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UX95KJsX3lM5IMG_5275.jpg','Vải','SP22','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M266',1,2,9,2),(23,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/jzdsGW_MG_1270.jpg','Vải','SP23','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M136 DESIGN',1,2,6,1),(27,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2e3Vnam08-vang.jpg','Polycarbonate','SP27','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','M08 DESIGN',1,6,1,2),(28,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/OA5B2P_MG_1524.jpg','Vải','SP28','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M08',1,6,1,2),(29,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hDS5IjM179-V1-4.jpg','Polycarbonate','SP29','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','M179 DESIGN',1,6,3,3),(30,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UgxaR0_MG_8621.jpg','Polycarbonate','SP30','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','M179',1,6,2,2),(31,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/nmlwi7IMG_9768.jpg','Vải','SP31','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M152K',1,3,5,1),(32,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/HQ0S39IMG_9737.jpg','Vải','SP32','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M153K',1,3,6,2),(34,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/iCmEnHIMG_0634b.jpg','Polycarbonate','SP34','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','M152LT',1,3,8,1),(35,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hX6g9FIMG_9718.jpg','Vải','SP35','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M153LT',1,3,7,1),(37,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/7MC6kFIMG_4053-copy.jpg','Polycarbonate','SP37','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','M379',1,3,4,3),(40,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/kEP3OBIMG_6413.jpg','Vải','SP40','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','MD-15 DESIGN',1,4,6,3),(42,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/6QMwPiVn-09.jpg','Da','SP42','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Da','MD-16 DESIGN',1,4,2,1),(43,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/YVNGyMIMG_8992-copy.jpg','Polycarbonate','SP43','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','MD-16',1,4,6,1),(45,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/n6M957MD-07-(5).jpg','Da','SP45','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Da','MD-07',1,4,8,1),(46,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/naBX9iIMG_1424.jpg','Polycarbonate','SP46','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Polycarbonate','MD-05',1,4,1,2),(48,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K9FtBoIMG_8995.jpg','Da','SP48','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Da','M139 KID',1,5,6,3),(49,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gTzVfEroyal-kl-08.jpg','Vải','SP49','Bền đẹp, bảo đảm an toàn khi tham gia giao thông',NULL,'2023-12-17 11:08:55','Vải','M20KS',1,5,4,1);
/*!40000 ALTER TABLE `san_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham_chi_tiet`
--

DROP TABLE IF EXISTS `san_pham_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `anh` varchar(255) DEFAULT NULL,
  `gia_ban` decimal(20,0) DEFAULT NULL,
  `gia_nhap` decimal(20,0) DEFAULT NULL,
  `gia_sau_giam` decimal(20,0) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong_ton` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_khuyen_mai` int DEFAULT NULL,
  `id_mau_sac` int DEFAULT NULL,
  `id_san_pham` int DEFAULT NULL,
  `id_size` int DEFAULT NULL,
  `id_trong_luong` int DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1rjmi0xu7agob46sq32ns1587` (`id_khuyen_mai`),
  KEY `FKdt9xxy835agedr1aa67p8vw0o` (`id_mau_sac`),
  KEY `FKmby561odp360b0sfqx4mmarja` (`id_san_pham`),
  KEY `FK899rggxfp9dtblqirh75pme1` (`id_size`),
  KEY `FKdrjkavdharm2dd7tsh3wywlr1` (`id_trong_luong`),
  CONSTRAINT `FK1rjmi0xu7agob46sq32ns1587` FOREIGN KEY (`id_khuyen_mai`) REFERENCES `khuyen_mai` (`id`),
  CONSTRAINT `FK899rggxfp9dtblqirh75pme1` FOREIGN KEY (`id_size`) REFERENCES `size` (`id`),
  CONSTRAINT `FKdrjkavdharm2dd7tsh3wywlr1` FOREIGN KEY (`id_trong_luong`) REFERENCES `trong_luong` (`id`),
  CONSTRAINT `FKdt9xxy835agedr1aa67p8vw0o` FOREIGN KEY (`id_mau_sac`) REFERENCES `mau_sac` (`id`),
  CONSTRAINT `FKmby561odp360b0sfqx4mmarja` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham_chi_tiet`
--

LOCK TABLES `san_pham_chi_tiet` WRITE;
/*!40000 ALTER TABLE `san_pham_chi_tiet` DISABLE KEYS */;
INSERT INTO `san_pham_chi_tiet` VALUES (1,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Zt7RKI_MG_3349.jpg',103000,NULL,NULL,NULL,'2023-12-17 14:58:15',24,1,NULL,1,1,1,2,'CTSP1'),(2,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/vJqbjnroyal-m139-v4.jpg',75000,NULL,NULL,NULL,'2023-12-17 14:58:15',11,1,NULL,2,2,1,3,'CTSP2'),(3,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/of3MY8_MG_8636.jpg',469000,NULL,NULL,NULL,'2023-12-17 14:58:15',35,1,NULL,7,3,1,1,'CTSP3'),(4,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/of3MY8_MG_8636.jpg',265000,NULL,NULL,NULL,'2023-12-17 14:58:15',70,1,NULL,7,3,2,1,'CTSP4'),(5,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/of3MY8_MG_8636.jpg',321000,NULL,NULL,NULL,'2023-12-17 14:58:15',45,1,NULL,7,3,4,1,'CTSP5'),(6,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QPhNDS_MG_0742.jpg',309000,NULL,NULL,NULL,'2023-12-17 14:58:15',14,1,NULL,1,3,1,1,'CTSP6'),(7,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QPhNDS_MG_0742.jpg',82000,NULL,NULL,NULL,'2023-12-17 14:58:15',35,1,NULL,1,3,2,1,'CTSP7'),(8,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CmSw3f_MG_9437.jpg',335000,NULL,NULL,NULL,'2023-12-17 14:58:15',33,1,NULL,5,3,1,1,'CTSP8'),(9,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/AfmhwnIMG_5283.jpg',479000,NULL,NULL,NULL,'2023-12-17 14:58:15',59,1,NULL,5,4,2,2,'CTSP9'),(10,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Zt7RKI_MG_3349.jpg',438000,NULL,NULL,NULL,'2023-12-17 14:58:15',94,1,NULL,1,1,2,2,'CTSP10'),(11,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/P0jXDB_MG_3354.jpg',250000,NULL,NULL,NULL,'2023-12-17 14:58:15',96,1,NULL,2,1,1,2,'CTSP11'),(12,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sN4zd1Royal-m139-v10.jpg',340000,NULL,NULL,NULL,'2023-12-17 14:58:15',94,1,NULL,3,2,1,3,'CTSP12'),(13,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QPhNDS_MG_0742.jpg',450000,NULL,NULL,NULL,'2023-12-17 14:58:15',83,1,NULL,1,3,4,1,'CTSP13'),(14,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/YJ9rG8IMG_2412.jpg',279000,NULL,NULL,NULL,'2023-12-17 14:58:15',32,1,NULL,6,3,2,1,'CTSP14'),(15,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/YJ9rG8IMG_2412.jpg',445000,NULL,NULL,NULL,'2023-12-17 14:58:15',12,1,NULL,6,3,1,1,'CTSP15'),(16,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CmSw3f_MG_9437.jpg',436000,NULL,NULL,NULL,'2023-12-17 14:58:15',64,1,NULL,5,3,4,1,'CTSP16'),(17,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CmSw3f_MG_9437.jpg',347000,NULL,NULL,NULL,'2023-12-17 14:58:15',81,1,NULL,5,3,2,1,'CTSP17'),(18,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/YJ9rG8IMG_2412.jpg',374000,NULL,NULL,NULL,'2023-12-17 14:58:15',14,1,NULL,6,3,4,1,'CTSP18'),(19,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/8MzEPqIMG_5454.jpg',330000,NULL,NULL,NULL,'2023-12-17 14:58:15',27,1,NULL,8,4,2,2,'CTSP19'),(20,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/AfmhwnIMG_5283.jpg',480000,NULL,NULL,NULL,'2023-12-17 14:58:15',92,1,NULL,5,4,1,2,'CTSP20'),(21,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/P0jXDB_MG_3354.jpg',457000,NULL,NULL,NULL,'2023-12-17 14:58:15',77,1,NULL,2,1,2,2,'CTSP21'),(22,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/0IAa3e_MG_0585.jpg',346000,NULL,NULL,NULL,'2023-12-17 14:58:15',8,1,NULL,3,1,1,2,'CTSP22'),(23,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/0IAa3e_MG_0585.jpg',309000,NULL,NULL,NULL,'2023-12-17 14:58:15',10,1,NULL,3,1,2,2,'CTSP23'),(24,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K6uk81_MG_3354-muc.jpg',457000,NULL,NULL,NULL,'2023-12-17 14:58:15',27,1,NULL,4,1,1,2,'CTSP24'),(25,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K6uk81_MG_3354-muc.jpg',406000,NULL,NULL,NULL,'2023-12-17 14:58:15',3,1,NULL,4,1,2,2,'CTSP25'),(26,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/pUtKrM_MG_0597.jpg',157000,NULL,NULL,NULL,'2023-12-17 14:58:15',33,1,NULL,7,1,1,2,'CTSP26'),(27,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/pUtKrM_MG_0597.jpg',420000,NULL,NULL,NULL,'2023-12-17 14:58:15',57,1,NULL,7,1,2,2,'CTSP27'),(28,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sN4zd1Royal-m139-v10.jpg',229000,NULL,NULL,NULL,'2023-12-17 14:58:15',83,1,NULL,3,2,4,3,'CTSP28'),(29,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/vJqbjnroyal-m139-v4.jpg',284000,NULL,NULL,NULL,'2023-12-17 14:58:15',45,1,NULL,2,2,2,3,'CTSP29'),(30,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/vJqbjnroyal-m139-v4.jpg',234000,NULL,NULL,NULL,'2023-12-17 14:58:15',77,1,NULL,2,2,4,3,'CTSP30'),(31,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QH7gUuroyal-m139-v4-2.jpg',269000,NULL,NULL,NULL,'2023-12-17 14:58:15',50,1,NULL,6,2,1,3,'CTSP31'),(32,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QH7gUuroyal-m139-v4-2.jpg',140000,NULL,NULL,NULL,'2023-12-17 14:58:15',17,1,NULL,6,2,2,3,'CTSP32'),(33,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QH7gUuroyal-m139-v4-2.jpg',296000,NULL,NULL,NULL,'2023-12-17 14:58:15',37,1,NULL,6,2,4,3,'CTSP33'),(34,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sN4zd1Royal-m139-v10.jpg',106000,NULL,NULL,NULL,'2023-12-17 14:58:15',31,1,NULL,3,2,2,3,'CTSP34'),(35,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/PeCI1GIMG_3358.jpg',498000,NULL,NULL,NULL,'2023-12-17 14:58:15',45,1,NULL,2,4,2,2,'CTSP35'),(36,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9iomrJIMG_5461.jpg',317000,NULL,NULL,NULL,'2023-12-17 14:58:15',29,1,NULL,4,4,1,2,'CTSP36'),(37,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9iomrJIMG_5461.jpg',493000,NULL,NULL,NULL,'2023-12-17 14:58:15',11,1,NULL,4,4,2,2,'CTSP37'),(38,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/8MzEPqIMG_5454.jpg',109000,NULL,NULL,NULL,'2023-12-17 14:58:15',69,1,NULL,8,4,1,2,'CTSP38'),(39,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/PeCI1GIMG_3358.jpg',372000,NULL,NULL,NULL,'2023-12-17 14:58:15',10,1,NULL,2,4,1,2,'CTSP39'),(40,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/lANmFe_MG_9655.jpg',130000,NULL,NULL,NULL,'2023-12-17 14:58:15',45,1,NULL,2,10,2,1,'CTSP40'),(41,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CRTP8N139B-den-bong.jpg',384000,NULL,NULL,NULL,'2023-12-17 14:58:15',92,1,NULL,2,20,1,1,'CTSP41'),(42,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CRTP8N139B-den-bong.jpg',131000,NULL,NULL,NULL,'2023-12-17 14:58:15',24,1,NULL,2,20,4,1,'CTSP42'),(43,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/TKXck1IMG_6096.jpg',353000,NULL,NULL,NULL,'2023-12-17 14:58:15',42,1,NULL,4,21,2,3,'CTSP43'),(44,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/TKXck1IMG_6096.jpg',420000,NULL,NULL,NULL,'2023-12-17 14:58:15',40,1,NULL,4,21,1,3,'CTSP44'),(45,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/fPbOjEIMG_6116.jpg',93000,NULL,NULL,NULL,'2023-12-17 14:58:15',73,1,NULL,5,21,2,3,'CTSP45'),(46,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CRTP8N139B-den-bong.jpg',54000,NULL,NULL,NULL,'2023-12-17 14:58:15',45,1,NULL,2,20,2,1,'CTSP46'),(47,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/LucA6vIMG_1147.jpg',392000,NULL,NULL,NULL,'2023-12-17 14:58:15',5,1,NULL,2,22,1,2,'CTSP47'),(48,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/LucA6vIMG_1147.jpg',398000,NULL,NULL,NULL,'2023-12-17 14:58:15',88,1,NULL,2,22,2,2,'CTSP48'),(49,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/fPbOjEIMG_6116.jpg',314000,NULL,NULL,NULL,'2023-12-17 14:58:15',26,1,NULL,5,21,1,3,'CTSP49'),(50,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/7uQysP_MG_1227.jpg',326000,NULL,NULL,NULL,'2023-12-17 14:58:15',66,1,NULL,5,23,2,4,'CTSP50'),(51,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/l61YIj_MG_1227-vang.jpg',187000,NULL,NULL,NULL,'2023-12-17 14:58:15',52,1,NULL,1,23,1,4,'CTSP51'),(52,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/l61YIj_MG_1227-vang.jpg',357000,NULL,NULL,NULL,'2023-12-17 14:58:15',61,1,NULL,1,23,2,4,'CTSP52'),(53,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/utOmb1_MG_1233-xanhlam.jpg',274000,NULL,NULL,NULL,'2023-12-17 14:58:15',47,1,NULL,4,23,1,4,'CTSP53'),(54,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/utOmb1_MG_1233-xanhlam.jpg',251000,NULL,NULL,NULL,'2023-12-17 14:58:15',51,1,NULL,4,23,2,4,'CTSP54'),(55,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/7uQysP_MG_1227.jpg',382000,NULL,NULL,NULL,'2023-12-17 14:58:15',15,1,NULL,5,23,1,4,'CTSP55'),(56,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9th8nxIMG_4967.jpg',205000,NULL,NULL,NULL,'2023-12-17 14:58:15',20,1,NULL,3,17,1,2,'CTSP56'),(57,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9th8nxIMG_4967.jpg',282000,NULL,NULL,NULL,'2023-12-17 14:58:15',55,1,NULL,3,17,2,2,'CTSP57'),(58,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/H53xjhIMG_0631.jpg',294000,NULL,NULL,NULL,'2023-12-17 14:58:15',13,1,NULL,2,17,1,2,'CTSP58'),(59,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/H53xjhIMG_0631.jpg',125000,NULL,NULL,NULL,'2023-12-17 14:58:15',100,1,NULL,2,17,2,2,'CTSP59'),(60,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/eKrhU4IMG_0597.jpg',142000,NULL,NULL,NULL,'2023-12-17 14:58:15',62,1,NULL,1,17,1,2,'CTSP60'),(61,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/eKrhU4IMG_0597.jpg',287000,NULL,NULL,NULL,'2023-12-17 14:58:15',7,1,NULL,1,17,2,2,'CTSP61'),(62,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/nmlwi7IMG_9768.jpg',59000,NULL,NULL,NULL,'2023-12-17 14:58:15',48,1,NULL,5,31,2,4,'CTSP62'),(63,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Oh23BlIMG_2953.jpg',286000,NULL,NULL,NULL,'2023-12-17 14:58:15',22,1,NULL,4,31,1,4,'CTSP63'),(64,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Oh23BlIMG_2953.jpg',299000,NULL,NULL,NULL,'2023-12-17 14:58:15',62,1,NULL,4,31,2,4,'CTSP64'),(65,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/KDMNaEIMG_2945.jpg',139000,NULL,NULL,NULL,'2023-12-17 14:58:15',46,1,NULL,2,31,1,4,'CTSP65'),(66,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/KDMNaEIMG_2945.jpg',200000,NULL,NULL,NULL,'2023-12-17 14:58:15',43,1,NULL,2,31,2,4,'CTSP66'),(67,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/nmlwi7IMG_9768.jpg',82000,NULL,NULL,NULL,'2023-12-17 14:58:15',76,1,NULL,5,31,1,4,'CTSP67'),(68,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hRgDYVIMG_9754.jpg',210000,NULL,NULL,NULL,'2023-12-17 14:58:15',50,1,NULL,4,32,2,4,'CTSP68'),(69,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hRgDYVIMG_9754.jpg',305000,NULL,NULL,NULL,'2023-12-17 14:58:15',24,1,NULL,4,32,1,4,'CTSP69'),(70,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/HQ0S39IMG_9737.jpg',396000,NULL,NULL,NULL,'2023-12-17 14:58:15',67,1,NULL,5,32,2,4,'CTSP70'),(71,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/cN0qpvIMG_2909.jpg',112000,NULL,NULL,NULL,'2023-12-17 14:58:15',62,1,NULL,2,32,1,4,'CTSP71'),(72,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/cN0qpvIMG_2909.jpg',225000,NULL,NULL,NULL,'2023-12-17 14:58:15',10,1,NULL,2,32,2,4,'CTSP72'),(73,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/HQ0S39IMG_9737.jpg',286000,NULL,NULL,NULL,'2023-12-17 14:58:15',61,1,NULL,5,32,1,4,'CTSP73'),(74,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hX6g9FIMG_9718.jpg',257000,NULL,NULL,NULL,'2023-12-17 14:58:15',76,1,NULL,5,35,2,3,'CTSP74'),(75,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/cXLDHSM153-LT-(3).jpg',377000,NULL,NULL,NULL,'2023-12-17 14:58:15',96,1,NULL,2,35,1,3,'CTSP75'),(76,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/cXLDHSM153-LT-(3).jpg',161000,NULL,NULL,NULL,'2023-12-17 14:58:15',51,1,NULL,2,35,2,3,'CTSP76'),(77,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hX6g9FIMG_9718.jpg',77000,NULL,NULL,NULL,'2023-12-17 14:58:15',67,1,NULL,5,35,1,3,'CTSP77'),(78,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/QhKmMYIMG_4068-copy.jpg',302000,NULL,NULL,NULL,'2023-12-17 14:58:15',82,1,NULL,2,37,1,2,'CTSP78'),(79,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/StzqxeIMG_4064-copy.jpg',329000,NULL,NULL,NULL,'2023-12-17 14:58:15',7,1,NULL,1,37,1,2,'CTSP79'),(80,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/O5IMUPIMG_4076-copy.jpg',237000,NULL,NULL,NULL,'2023-12-17 14:58:15',87,1,NULL,3,37,1,2,'CTSP80'),(81,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/iCmEnHIMG_0634b.jpg',151000,NULL,NULL,NULL,'2023-12-17 14:58:15',16,1,NULL,5,34,2,2,'CTSP81'),(82,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/iCmEnHIMG_0634b.jpg',446000,NULL,NULL,NULL,'2023-12-17 14:58:15',19,1,NULL,5,34,1,2,'CTSP82'),(83,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/IrwyHWIMG_2940.jpg',372000,NULL,NULL,NULL,'2023-12-17 14:58:15',45,1,NULL,4,34,2,2,'CTSP83'),(84,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/IrwyHWIMG_2940.jpg',473000,NULL,NULL,NULL,'2023-12-17 14:58:15',67,1,NULL,4,34,1,2,'CTSP84'),(85,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/u5lSeNIMG_2963.jpg',297000,NULL,NULL,NULL,'2023-12-17 14:58:15',100,1,NULL,2,34,2,2,'CTSP85'),(86,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/u5lSeNIMG_2963.jpg',467000,NULL,NULL,NULL,'2023-12-17 14:58:15',99,1,NULL,2,34,1,2,'CTSP86'),(87,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/naBX9iIMG_1424.jpg',492000,NULL,NULL,NULL,'2023-12-17 14:58:15',94,1,NULL,2,46,1,5,'CTSP87'),(88,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/naBX9iIMG_1424.jpg',109000,NULL,NULL,NULL,'2023-12-17 14:58:15',75,1,NULL,2,46,2,5,'CTSP88'),(89,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/V6fpcUIMG_6428.jpg',374000,NULL,NULL,NULL,'2023-12-17 14:58:15',89,1,NULL,3,40,1,2,'CTSP89'),(90,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/P9dO7qMD15.jpg',140000,NULL,NULL,NULL,'2023-12-17 14:58:15',21,1,NULL,4,40,1,2,'CTSP90'),(91,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/YVNGyMIMG_8992-copy.jpg',428000,NULL,NULL,NULL,'2023-12-17 14:58:15',38,1,NULL,3,43,1,3,'CTSP91'),(92,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qYFJh7IMG_8993.jpg',318000,NULL,NULL,NULL,'2023-12-17 14:58:15',25,1,NULL,2,43,1,3,'CTSP92'),(93,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/3TDzRaIMG_1446.jpg',259000,NULL,NULL,NULL,'2023-12-17 14:58:15',10,1,NULL,1,45,1,1,'CTSP93'),(94,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/peGS8HIMG_1455.jpg',290000,NULL,NULL,NULL,'2023-12-17 14:58:15',74,1,NULL,2,45,1,1,'CTSP94'),(95,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/DzjPZ8IMG_8969-copy.jpg',171000,NULL,NULL,NULL,'2023-12-17 14:58:15',41,1,NULL,4,42,1,4,'CTSP95'),(96,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/je8YaBIMG_8967-copy.jpg',390000,NULL,NULL,NULL,'2023-12-17 14:58:15',81,1,NULL,5,42,1,4,'CTSP96'),(97,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/k75KYuIMG_9020.jpg',483000,NULL,NULL,NULL,'2023-12-17 14:58:15',84,1,NULL,4,48,3,2,'CTSP97'),(98,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/vLXzeQIMG_9014.jpg',293000,NULL,NULL,NULL,'2023-12-17 14:58:15',77,1,NULL,5,48,3,2,'CTSP98'),(99,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K9FtBoIMG_8995.jpg',419000,NULL,NULL,NULL,'2023-12-17 14:58:15',31,1,NULL,1,48,3,2,'CTSP99'),(100,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2zeIjJIMG_1777-copy.jpg',264000,NULL,NULL,NULL,'2023-12-17 14:58:15',24,1,NULL,6,48,3,2,'CTSP100'),(101,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/DyXUshroyal-kl-14.jpg',463000,NULL,NULL,NULL,'2023-12-17 14:58:15',28,1,NULL,1,49,3,4,'CTSP101'),(102,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gTzVfEroyal-kl-08.jpg',123000,NULL,NULL,NULL,'2023-12-17 14:58:15',67,1,NULL,5,49,3,4,'CTSP102'),(103,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2h5Wqxroyal-kl-02.jpg',78000,NULL,NULL,NULL,'2023-12-17 14:58:15',48,1,NULL,4,49,3,4,'CTSP103'),(104,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/adguIvroyal-kl-12.jpg',424000,NULL,NULL,NULL,'2023-12-17 14:58:15',40,1,NULL,6,49,3,4,'CTSP104'),(105,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2e3Vnam08-vang.jpg',481000,NULL,NULL,NULL,'2023-12-17 14:58:15',56,1,NULL,1,27,1,5,'CTSP105'),(106,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2e3Vnam08-vang.jpg',183000,NULL,NULL,NULL,'2023-12-17 14:58:15',58,1,NULL,1,27,2,5,'CTSP106'),(107,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xkQZyt_MG_0872.jpg',323000,NULL,NULL,NULL,'2023-12-17 14:58:15',23,1,NULL,4,27,1,5,'CTSP107'),(108,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xkQZyt_MG_0872.jpg',113000,NULL,NULL,NULL,'2023-12-17 14:58:15',39,1,NULL,4,27,2,5,'CTSP108'),(109,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/OA5B2P_MG_1524.jpg',451000,NULL,NULL,NULL,'2023-12-17 14:58:15',26,1,NULL,2,28,1,1,'CTSP109'),(110,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/OA5B2P_MG_1524.jpg',61000,NULL,NULL,NULL,'2023-12-17 14:58:15',13,1,NULL,2,28,2,1,'CTSP110'),(111,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/wj3UWbM179-V1-12.jpg',254000,NULL,NULL,NULL,'2023-12-17 14:58:15',86,1,NULL,5,29,1,2,'CTSP111'),(112,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/wj3UWbM179-V1-12.jpg',137000,NULL,NULL,NULL,'2023-12-17 14:58:15',91,1,NULL,5,29,2,2,'CTSP112'),(113,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/37MKTPM179-V3-9.jpg',326000,NULL,NULL,NULL,'2023-12-17 14:58:15',97,1,NULL,4,29,1,2,'CTSP113'),(114,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/37MKTPM179-V3-9.jpg',266000,NULL,NULL,NULL,'2023-12-17 14:58:15',11,1,NULL,4,29,2,2,'CTSP114'),(115,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UgxaR0_MG_8621.jpg',305000,NULL,NULL,NULL,'2023-12-17 14:58:15',62,1,NULL,7,30,1,2,'CTSP115'),(116,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UgxaR0_MG_8621.jpg',223000,NULL,NULL,NULL,'2023-12-17 14:58:15',78,1,NULL,7,30,2,2,'CTSP116'),(117,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/7mPt1b_MG_9810.jpg',153000,NULL,NULL,NULL,'2023-12-17 14:58:15',4,1,NULL,2,30,1,2,'CTSP117'),(118,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/7mPt1b_MG_9810.jpg',497000,NULL,NULL,NULL,'2023-12-17 14:58:15',85,1,NULL,2,30,2,2,'CTSP118');
/*!40000 ALTER TABLE `san_pham_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'S1','56-57cm',NULL,NULL,'L',1),(2,'S2','58-59cm',NULL,NULL,'XL',1),(3,'S3','52-54cm',NULL,NULL,'S',1),(4,'S4','54-55cm',NULL,NULL,'M',1),(5,'S5','Freesize',NULL,NULL,'Freesize',1);
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thong_bao`
--

DROP TABLE IF EXISTS `thong_bao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thong_bao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5dd115i50w4aw5s2ciyepw3gx` (`id_san_pham_chi_tiet`),
  KEY `FKskwt6maa75a67qfpgigo1kwam` (`id_user`),
  CONSTRAINT `FK5dd115i50w4aw5s2ciyepw3gx` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`),
  CONSTRAINT `FKskwt6maa75a67qfpgigo1kwam` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thong_bao`
--

LOCK TABLES `thong_bao` WRITE;
/*!40000 ALTER TABLE `thong_bao` DISABLE KEYS */;
INSERT INTO `thong_bao` VALUES (1,NULL,NULL,1,0,NULL,'hóa đơn HD6262 chờ xác nhận',3),(2,NULL,NULL,1,0,NULL,'hóa đơn HD6537 chờ xác nhận',3),(3,NULL,NULL,1,0,NULL,'hóa đơn HD7828 chờ xác nhận',3),(4,NULL,NULL,1,0,NULL,'hóa đơn HD8419 chờ xác nhận',3),(5,NULL,NULL,1,0,NULL,'hóa đơn HD6001 chờ xác nhận',3),(6,NULL,NULL,1,0,NULL,'hóa đơn HD7449 chờ xác nhận',3),(7,NULL,NULL,1,0,NULL,'hóa đơn HD3049 chờ xác nhận',3),(8,NULL,NULL,0,0,NULL,'hóa đơn HD3621 chờ xác nhận',3),(9,NULL,NULL,1,1,NULL,'Hóa đơn mã HD6125 đã được xác nhận',3),(10,NULL,NULL,1,0,NULL,'hóa đơn HD58 chờ xác nhận',10001),(11,NULL,NULL,0,1,NULL,'Hóa đơn mã HD58 đã được xác nhận',10001),(12,NULL,NULL,1,8,NULL,'Hóa đơn mã HD58 đã hoàn thành',10001),(13,NULL,NULL,1,3,NULL,'yêu cầu trả sản phẩm có mã: SP2',10001),(14,NULL,NULL,1,4,NULL,'Hóa đơn mã HD58 đã đồng ý đổi trả',10001),(15,NULL,NULL,1,0,NULL,'hóa đơn HD60 chờ xác nhận',10001),(16,NULL,NULL,1,1,NULL,'Hóa đơn mã HD60 đã được xác nhận',10001),(17,NULL,NULL,1,8,NULL,'Hóa đơn mã HD60 đã hoàn thành',10001),(18,NULL,NULL,1,3,NULL,'yêu cầu trả sản phẩm có mã: SP8',10001),(19,NULL,NULL,1,0,NULL,'hóa đơn HD4837 chờ xác nhận',10001),(20,NULL,NULL,1,1,NULL,'Hóa đơn mã HD4837 đã được xác nhận',10001),(21,NULL,NULL,1,8,NULL,'Hóa đơn mã HD4837 đã hoàn thành',10001);
/*!40000 ALTER TABLE `thong_bao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuong_hieu`
--

DROP TABLE IF EXISTS `thuong_hieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuong_hieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuong_hieu`
--

LOCK TABLES `thuong_hieu` WRITE;
/*!40000 ALTER TABLE `thuong_hieu` DISABLE KEYS */;
INSERT INTO `thuong_hieu` VALUES (1,'1001','2023-09-10','2023-09-05','Amoro',1),(2,'1002','2023-09-15','2023-09-10','Protec',1),(3,'1003','2023-09-23','2023-09-19','HSL',0),(4,'1004','2023-09-24','2023-09-20','Hitech',0),(5,'1005','2023-08-10','2023-07-25','Andes',1),(6,'1006','2023-07-19','2023-07-15','Honda',1),(7,'1007','2023-05-20','2023-05-15','Sankyo',0),(8,'1008','2023-10-10','2023-10-05','Avex',0),(9,'1009','2023-07-10','2023-07-01','Royal',0);
/*!40000 ALTER TABLE `thuong_hieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trong_luong`
--

DROP TABLE IF EXISTS `trong_luong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trong_luong` (
  `id` int NOT NULL AUTO_INCREMENT,
  `don_vi` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `value` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trong_luong`
--

LOCK TABLES `trong_luong` WRITE;
/*!40000 ALTER TABLE `trong_luong` DISABLE KEYS */;
INSERT INTO `trong_luong` VALUES (1,'gam','TL1','2023-09-10','2023-08-20',1,1050),(2,'gam','TL2','2023-09-10','2023-08-20',1,850),(3,'gam','TL3','2023-09-10','2023-08-20',1,752),(4,'gam','TL4','2023-09-10','2023-08-20',1,500),(5,'gam','TL5','2023-09-10','2023-08-20',1,350);
/*!40000 ALTER TABLE `trong_luong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `gioi_tinh` int DEFAULT NULL,
  `anh` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sinh` varchar(255) DEFAULT NULL,
  `ngay_sua` datetime DEFAULT NULL,
  `ngay_tao` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'',NULL,NULL,'U1',NULL,NULL,NULL,'','USER','','Khách hàng lẻ',-1,''),(2,'cskh@gmail.com',NULL,NULL,'U2',NULL,NULL,'2023-11-29 10:57:28','$2a$12$Xcp214DEIsQr61KrINMt5egl.2Tqfcjwhu32Y9Y5TCEFzH5yiEOlS','ADMIN','0987678987','Tôn Thất Học',2,'cskh@gmail.com'),(3,'levanc@gmail.com',1,NULL,'U3','2002-12-07','2023-11-10 00:00:00','2023-11-29 15:57:28','$2a$12$Xcp214DEIsQr61KrINMt5egl.2Tqfcjwhu32Y9Y5TCEFzH5yiEOlS','USER','0987678985','Lê Văn Cờ',2,'levanc@gmail.com'),(4,'0123456789@gmail.com',NULL,NULL,'U4',NULL,NULL,'2023-11-29 13:57:28',NULL,'USER','0123456789','Rô Nan Đô',2,'0123456789@gmail.com'),(5,'0999999999@gmail.com',NULL,NULL,'U5',NULL,NULL,'2023-11-29 16:57:28',NULL,'USER','0999999999','Lê Văn Đốt Ky',2,'0999999999@gmail.com'),(6,'0912345678@gmail.com',NULL,NULL,'U6',NULL,NULL,'2023-11-29 12:57:28',NULL,'USER','0912345678','Nguyễn Văn Mẹc Si',2,'0912345678@gmail.com'),(7,'lothiton@gmail.com',1,NULL,'U7','2002-10-07','2023-11-10 00:00:00','2023-11-29 10:57:28','$2a$12$Xcp214DEIsQr61KrINMt5egl.2Tqfcjwhu32Y9Y5TCEFzH5yiEOlS','ADMIN','0987678789','Lò Thị Tôn',2,'lothiton@gmail.com'),(8,'0123456789@gmail.com',NULL,NULL,'U8',NULL,NULL,'2023-11-29 11:57:28',NULL,'USER','0123456789','John Doe',2,'0123456789@gmail.com'),(9,'0987654321@gmail.com',NULL,NULL,'U9',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0987654321','Jane Smith',2,'0987654321@gmail.com'),(10,'0321456789@gmail.com',NULL,NULL,'U10',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0321456789','Peter Jones',2,'0321456789@gmail.com'),(11,'0654321987@gmail.com',NULL,NULL,'U11',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0654321987','Mary Williams',2,'0654321987@gmail.com'),(12,'0987123456@gmail.com',NULL,NULL,'U12',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0987123456','David Brown',2,'0987123456@gmail.com'),(13,'0123654321@gmail.com',NULL,NULL,'U13',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0123654321','Susan Miller',2,'0123654321@gmail.com'),(14,'0456789123@gmail.com',NULL,NULL,'U14',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0456789123','Paul Anderson',2,'0456789123@gmail.com'),(15,'0789321456@gmail.com',NULL,NULL,'U15',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0789321456','Sarah Taylor',2,'0789321456@gmail.com'),(16,'0123098765@gmail.com',NULL,NULL,'U16',NULL,NULL,'2023-11-29 10:57:28',NULL,'USER','0123098765','Mark Thomas',2,'0123098765@gmail.com'),(17,'ngthanhnam371@gmail.com',NULL,NULL,'U17',NULL,NULL,'2023-11-29 10:57:28','$2a$10$323A.p2uDw3RzabRSKDuPu/gCEWolLjTLywaicMpw.foQbh3wVKdq','USER','0456123987','Karen Thompson',2,'ngthanhnam371@gmail.com'),(30,'0123789654@gmail.com',2,NULL,'U6675','2023/12/05 00:00:00',NULL,'2023-12-05 09:04:38','$2a$10$gR.MO37wyqBEyTY8qcsQu.kwUM7kzyMx71nHMFGHwV1v3F4afmMz6','USER','0123789654','Elizabeth Roberts',2,'0123789654@gmail.com'),(10001,'hn7566410@gmail.com',NULL,'https://lh3.googleusercontent.com/a/ACg8ocLm_XqTXg5gIW5wi09UW_pz-nwcni6qJqf-cY5v5czLOWPW=s96-c','US10001',NULL,NULL,'2023-12-07 23:09:48','$2a$12$Xcp214DEIsQr61KrINMt5egl.2Tqfcjwhu32Y9Y5TCEFzH5yiEOlS','USER',NULL,'vinh nguyễn',2,'hn7566410@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vat_lieu`
--

DROP TABLE IF EXISTS `vat_lieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vat_lieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vat_lieu`
--

LOCK TABLES `vat_lieu` WRITE;
/*!40000 ALTER TABLE `vat_lieu` DISABLE KEYS */;
INSERT INTO `vat_lieu` VALUES (1,'VL1','Độ bền cao','2023-11-10','2023-10-24','Nhựa ABS nguyên sinh',1),(2,'VL2','Êm ái, thông thoáng','2023-11-10','2023-10-24','Xốp EPS ',1),(3,'VL3','Mềm mịn','2023-11-10','2023-10-24','Da Simili',1);
/*!40000 ALTER TABLE `vat_lieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `giam_toi_da` decimal(20,0) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `gia_tri_giam` int DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoi_gian_bat_dau` datetime(6) DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime(6) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES (1,15000,'ma giam gia zomot',NULL,NULL,'zô mốt','2023-06-23 00:00:00.000000','2023-06-25 00:00:00.000000',1),(2,13000,'freeship',NULL,NULL,'freeship','2023-06-23 00:00:00.000000','2023-06-25 00:00:00.000000',1),(3,16000,'thời trang',NULL,NULL,'thời trang','2023-06-23 00:00:00.000000','2023-06-25 00:00:00.000000',1),(4,15000,'siêu voucher',NULL,NULL,'siêu voucher','2023-06-23 00:00:00.000000','2023-06-25 00:00:00.000000',1),(5,11000,'06-06',NULL,NULL,'06-06','2023-06-23 00:00:00.000000','2023-06-25 00:00:00.000000',1);
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-17 22:21:04
