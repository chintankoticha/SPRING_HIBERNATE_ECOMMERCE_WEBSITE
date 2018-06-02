-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart_table`
--

DROP TABLE IF EXISTS `cart_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_table` (
  `quantity` int(11) DEFAULT NULL,
  `userid` bigint(20) NOT NULL,
  `productid` bigint(20) NOT NULL,
  PRIMARY KEY (`userid`,`productid`),
  KEY `FK899A41EFF8A7D4D8` (`productid`),
  KEY `FK899A41EF80C36CB2` (`userid`),
  CONSTRAINT `FK899A41EF80C36CB2` FOREIGN KEY (`userid`) REFERENCES `user_table` (`personID`),
  CONSTRAINT `FK899A41EFF8A7D4D8` FOREIGN KEY (`productid`) REFERENCES `product_table` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_table`
--

LOCK TABLES `cart_table` WRITE;
/*!40000 ALTER TABLE `cart_table` DISABLE KEYS */;
INSERT INTO `cart_table` VALUES (3,1,2),(2,1,4),(1,1,5),(3,1,7),(1,1,10),(5,1,11),(1,1,15),(51,2,4);
/*!40000 ALTER TABLE `cart_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_product_table`
--

DROP TABLE IF EXISTS `category_product_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_product_table` (
  `categoryID` bigint(20) NOT NULL,
  `productID` bigint(20) NOT NULL,
  PRIMARY KEY (`categoryID`,`productID`),
  KEY `FK52DB6FDF8A7D4D8` (`productID`),
  KEY `FK52DB6FD3FF445D8` (`categoryID`),
  CONSTRAINT `FK52DB6FD3FF445D8` FOREIGN KEY (`categoryID`) REFERENCES `category_table` (`categoryId`),
  CONSTRAINT `FK52DB6FDF8A7D4D8` FOREIGN KEY (`productID`) REFERENCES `product_table` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_product_table`
--

LOCK TABLES `category_product_table` WRITE;
/*!40000 ALTER TABLE `category_product_table` DISABLE KEYS */;
INSERT INTO `category_product_table` VALUES (1,1),(2,1),(3,1),(4,1),(1,2),(2,2),(3,2),(4,2),(1,4),(2,4),(3,4),(4,4),(1,5),(2,5),(3,5),(4,5),(1,7),(2,7),(3,7),(4,7),(1,8),(5,10),(5,11),(5,12),(1,13),(2,13),(4,13),(5,13),(1,14),(2,14),(4,14),(5,14),(1,15),(2,15),(4,15),(5,15),(1,16),(2,16),(4,16),(5,16),(1,17),(2,17),(4,17),(5,17),(5,20);
/*!40000 ALTER TABLE `category_product_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_table`
--

DROP TABLE IF EXISTS `category_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_table` (
  `categoryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `categoryId` (`categoryId`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_table`
--

LOCK TABLES `category_table` WRITE;
/*!40000 ALTER TABLE `category_table` DISABLE KEYS */;
INSERT INTO `category_table` VALUES (2,'BOOKS'),(1,'ELECTRONICS'),(5,'SHOES'),(4,'SUIT'),(3,'WATCH');
/*!40000 ALTER TABLE `category_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_table`
--

DROP TABLE IF EXISTS `email_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_table` (
  `emailID` bigint(20) NOT NULL,
  `email_Address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`emailID`),
  UNIQUE KEY `emailID` (`emailID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_table`
--

LOCK TABLES `email_table` WRITE;
/*!40000 ALTER TABLE `email_table` DISABLE KEYS */;
INSERT INTO `email_table` VALUES (1,'chintankoticha@gmail.com'),(2,'chintankoticha@gmail.com'),(3,'gor.v@husky.neu.edu'),(4,'uiewg@gw.com'),(5,'chintankoticha@gmail.com');
/*!40000 ALTER TABLE `email_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_table` (
  `orderid` int(11) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `productid` bigint(20) NOT NULL,
  `order_date` date DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`,`orderid`,`productid`),
  KEY `FK75A2F39DF8A7D4D8` (`productid`),
  KEY `FK75A2F39D80C36CB2` (`userid`),
  CONSTRAINT `FK75A2F39D80C36CB2` FOREIGN KEY (`userid`) REFERENCES `user_table` (`personID`),
  CONSTRAINT `FK75A2F39DF8A7D4D8` FOREIGN KEY (`productid`) REFERENCES `product_table` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table`
--

LOCK TABLES `order_table` WRITE;
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
INSERT INTO `order_table` VALUES (0,1,2,'2017-04-23',4),(0,1,4,'2017-04-23',5),(0,1,5,'2017-04-23',1),(1,1,4,'2017-04-23',1),(2,1,1,'2017-04-24',1),(3,1,11,'2017-04-24',2),(4,1,12,'2017-04-24',2),(5,1,2,'2017-04-24',5),(5,1,5,'2017-04-24',1),(5,1,7,'2017-04-24',3),(6,1,2,'2017-04-24',5),(6,1,8,'2017-04-24',1),(7,1,1,'2017-04-24',2),(10,1,5,'2017-04-24',1),(10,1,12,'2017-04-24',1),(11,1,8,'2017-04-24',2),(12,1,4,'2017-04-25',1),(12,1,17,'2017-04-25',1),(13,1,2,'2017-04-26',4),(13,1,8,'2017-04-26',1),(14,1,1,'2017-04-26',1),(14,1,5,'2017-04-26',1),(14,1,7,'2017-04-26',1),(14,1,10,'2017-04-26',1),(14,1,11,'2017-04-26',1),(8,2,1,'2017-04-24',1),(8,2,2,'2017-04-24',5),(8,2,5,'2017-04-24',1),(21,2,2,'2017-04-26',100),(22,2,2,'2017-04-26',100),(23,2,2,'2017-04-26',120),(9,3,10,'2017-04-24',1),(9,3,12,'2017-04-24',1),(15,3,5,'2017-04-26',1),(15,3,7,'2017-04-26',5),(16,3,2,'2017-04-26',68),(17,3,2,'2017-04-26',68),(18,3,2,'2017-04-26',70),(19,3,2,'2017-04-26',100),(20,3,2,'2017-04-26',1),(24,3,4,'2017-04-26',50),(25,3,4,'2017-04-26',50),(26,4,4,'2017-04-26',50),(27,4,4,'2017-04-26',50),(28,4,4,'2017-04-26',1),(29,4,4,'2017-04-26',1),(30,4,4,'2017-04-26',3),(31,4,2,'2017-04-26',1),(32,4,2,'2017-04-26',1),(33,4,2,'2017-04-26',1),(34,4,2,'2017-04-26',1),(35,4,2,'2017-04-26',1),(36,4,2,'2017-04-26',0),(37,4,1,'2017-04-26',6),(38,4,1,'2017-04-26',2),(39,4,1,'2017-04-26',2),(40,4,2,'2017-04-26',3),(42,4,2,'2017-04-26',2),(41,5,4,'2017-04-26',50);
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_table`
--

DROP TABLE IF EXISTS `person_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_table` (
  `personID` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`personID`),
  UNIQUE KEY `personID` (`personID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_table`
--

LOCK TABLES `person_table` WRITE;
/*!40000 ALTER TABLE `person_table` DISABLE KEYS */;
INSERT INTO `person_table` VALUES (1,'c','k'),(2,'a','j'),(3,'v','g'),(4,'chintan','iubefqiuf'),(5,'abc','abc');
/*!40000 ALTER TABLE `person_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_table`
--

DROP TABLE IF EXISTS `product_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_table` (
  `productID` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL,
  `seller_sellerID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`productID`),
  UNIQUE KEY `productID` (`productID`),
  KEY `FK52A2BC7E954F4B9A` (`seller_sellerID`),
  CONSTRAINT `FK52A2BC7E954F4B9A` FOREIGN KEY (`seller_sellerID`) REFERENCES `seller_table` (`sellerID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_table`
--

LOCK TABLES `product_table` WRITE;
/*!40000 ALTER TABLE `product_table` DISABLE KEYS */;
INSERT INTO `product_table` VALUES (1,'tctuc','h4O8EJk.jpg','tcuv','40','96',1),(2,'yufty','casiowatch.jpg','uyf','3','62',1),(4,'tdtyuyf','book1.jpg','uyfuyf','76','50',1),(5,'uycuycu','book2.jpg','iyvuyv','51','6',1),(7,'ytctyc','delllap1.jpg','utdyt','43','4',1),(8,'iurgwiw','h4O8EJk.jpg','gwriug','100','10',3),(10,'iuwbgigqeb','delllap12jpg.jpg','iwugbg','38','90',2),(11,'yugwe','hplap1.jpg','gwiub','76','80',4),(12,'iugbwr','h4O8EJk.jpg','wiurgb','21','74',4),(13,'abcd','image-not-found.jpg','product1','10','100',4),(14,'giwe','radowatch.jpg','product2','97','90',4),(15,'gwiiu','image-not-found.jpg','product3','76','68',4),(16,'product4','dellpc.jpg','iubgw','65','10',4),(17,'prod5','hugobossdeo.jpg','grwui','78','89',4),(20,'validatedproduct','casual2016shoes.jpg','pro456','10.12','100',1);
/*!40000 ALTER TABLE `product_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_table`
--

DROP TABLE IF EXISTS `seller_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller_table` (
  `sellerID` bigint(20) NOT NULL AUTO_INCREMENT,
  `contactnumber` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sellerID`),
  UNIQUE KEY `sellerID` (`sellerID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_table`
--

LOCK TABLES `seller_table` WRITE;
/*!40000 ALTER TABLE `seller_table` DISABLE KEYS */;
INSERT INTO `seller_table` VALUES (1,'8577078609','chintankoticha@gmail.com','aa','aa','Approved','aa'),(2,'8577078609','chintankoticha@gmail.com','bb','bb','Approved','bb'),(3,'8577078609','dinesh.koticha@gmail.com','dinesh','dinesh','Approved','dinesh'),(4,'8577078609','chintankoticha@gmail.com','cc','cc','Approved','cc'),(5,'8965295731','wg@gwge.com','dd','dd','Approved','dd'),(6,'8965295731','wg@gwge.com','dd','dd','Approved','dd'),(7,'3464463643','qwgeget@gmail.com','ee','ee','Pending','ee'),(8,'34680724067','wgegwg@gmaige.com','ee','ee','Approved','ee'),(9,'0123456789','chintankoticha@gmail.com','chintan','chintandk10','Approved','chintan');
/*!40000 ALTER TABLE `seller_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_table` (
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `personID` bigint(20) NOT NULL,
  PRIMARY KEY (`personID`),
  KEY `FK7358465A7E966506` (`personID`),
  CONSTRAINT `FK7358465A7E966506` FOREIGN KEY (`personID`) REFERENCES `person_table` (`personID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table`
--

LOCK TABLES `user_table` WRITE;
/*!40000 ALTER TABLE `user_table` DISABLE KEYS */;
INSERT INTO `user_table` VALUES ('ck','ck',1),('aj','aj',2),('vg','vg',3),('sc','sc',4),('abc','abc',5);
/*!40000 ALTER TABLE `user_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-26 11:19:15
