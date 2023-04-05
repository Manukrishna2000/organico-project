/*
SQLyog Community Edition- MySQL GUI v8.03 
MySQL - 5.6.12-log : Database - organic
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`organic` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `organic`;

/*Table structure for table `assign` */

DROP TABLE IF EXISTS `assign`;

CREATE TABLE `assign` (
  `assignid` int(11) NOT NULL AUTO_INCREMENT,
  `o_id` varchar(225) NOT NULL,
  `delivary_boy_id` varchar(25) NOT NULL,
  `status` varchar(25) NOT NULL,
  PRIMARY KEY (`assignid`,`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `assign` */

insert  into `assign`(`assignid`,`o_id`,`delivary_boy_id`,`status`) values (5,'2','10','pending');

/*Table structure for table `bank` */

DROP TABLE IF EXISTS `bank`;

CREATE TABLE `bank` (
  `bank_name` varchar(30) DEFAULT NULL,
  `account` varchar(30) DEFAULT NULL,
  `pin` varchar(30) DEFAULT NULL,
  `balance` int(11) DEFAULT '10000'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `bank` */

insert  into `bank`(`bank_name`,`account`,`pin`,`balance`) values ('icici','1234','1234',9000),('axis','1234','1234',10000),('sbi','1234','1234',6844);

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cart` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `category` */

insert  into `category`(`cat_id`,`cat_name`) values (2,'vegetables'),(5,'oil123'),(10,'soap'),(11,'paste'),(12,'organic'),(14,'masala'),(16,'pappaya');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `complaint` varchar(40) DEFAULT NULL,
  `replay` varchar(30) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`u_id`,`date`,`complaint`,`replay`,`status`) values (1,3,'2021-01-16','tyhhiokjhg','thank you','replied'),(2,3,'2021-03-12','sdfghjkol','ok','replied'),(3,3,'0000-00-00','sdfg','good','replied'),(4,4,'0000-00-00','sdfgkbb',NULL,'pending');

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  `post` varchar(20) DEFAULT NULL,
  `pin` bigint(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `conatct` bigint(20) DEFAULT NULL,
  `u_id` int(11) DEFAULT NULL,
  `photo` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `customer` */

insert  into `customer`(`c_id`,`name`,`place`,`post`,`pin`,`email`,`conatct`,`u_id`,`photo`) values (1,'sowmya','calicut','calicut',673600,'aggg',988888888,8,NULL),(2,'rahul','wynd','panamaram',345765,'rahul@gmail.com',8765432,4,NULL);

/*Table structure for table `delivary_boy` */

DROP TABLE IF EXISTS `delivary_boy`;

CREATE TABLE `delivary_boy` (
  `delivery_boy_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `contact_number` bigint(20) NOT NULL,
  `place` varchar(25) NOT NULL,
  `post` varchar(25) NOT NULL,
  `pin` bigint(20) NOT NULL,
  `city` varchar(25) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `login_id` int(11) DEFAULT NULL,
  `seller_lid` int(11) DEFAULT NULL,
  PRIMARY KEY (`delivery_boy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

/*Data for the table `delivary_boy` */

insert  into `delivary_boy`(`delivery_boy_id`,`name`,`contact_number`,`place`,`post`,`pin`,`city`,`email`,`login_id`,`seller_lid`) values (9,'sai',3456789,'3768900','45677',432987654,'432987654',NULL,NULL,NULL),(17,'prajul',0,'567890','chvyr',7560863456,'calicut',NULL,NULL,NULL),(19,'prajul',567890,'567890','7560863456',0,'chvyr',NULL,NULL,NULL),(21,'prajul',4567890,'567890','7560863456',0,'chvyr',NULL,NULL,NULL),(22,'yuioo',0,'65432','nit',2345678,'calicut',NULL,NULL,NULL),(23,'vishnu',1234567890,'medical college','2345678',345678,'calicut',NULL,NULL,NULL),(24,'rohith',2132313,'malayamma','dsffsafs',123,'xccv','rohith@gmail.com',9,2),(25,'Ajay',7560897623,'kuttikatur','kunnamangalam',2345678,'calicut','ajay1234@gmail.com',10,2),(26,'vivek p s',4567890987,'koyilandi','koyilandi',456780,'calicut','vivek@gmail.com',11,2);

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL,
  `longitude` varchar(25) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `location` */

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `loginid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`loginid`,`username`,`password`,`type`) values (1,'skjb','khjjh','seller'),(2,'asni@gmail.com','2522410','seller'),(3,'soumya','soumya','seller'),(4,'admin','admin','admin'),(5,'keerthana','keeru','seller'),(6,'srekha','sree','customer'),(7,'manu','manu','seller'),(8,'qq','ww','user'),(9,'rohith@gmail.com','7525','delivery'),(10,'ajay1234@gmail.com','9567','delivery'),(11,'vivek@gmail.com','3224','delivery'),(12,'lakshmi@gmail.com','123456789','customer');

/*Table structure for table `order_main` */

DROP TABLE IF EXISTS `order_main`;

CREATE TABLE `order_main` (
  `o_id` int(25) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(25) NOT NULL,
  `date` date NOT NULL,
  `total` int(11) NOT NULL,
  `status` varchar(25) NOT NULL,
  `seller_id` varchar(25) NOT NULL,
  `delivary_address` varchar(25) NOT NULL,
  PRIMARY KEY (`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `order_main` */

insert  into `order_main`(`o_id`,`user_id`,`date`,`total`,`status`,`seller_id`,`delivary_address`) values (1,'12','2021-03-11',3156,'pending','2','yeeeee');

/*Table structure for table `order_sub` */

DROP TABLE IF EXISTS `order_sub`;

CREATE TABLE `order_sub` (
  `s_id` varchar(25) NOT NULL,
  `o_id` varchar(25) NOT NULL,
  `product_id` varchar(25) NOT NULL,
  `quantity` int(11) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `order_sub` */

insert  into `order_sub`(`s_id`,`o_id`,`product_id`,`quantity`,`amount`) values ('','1','8',20,2880),('','1','3',12,276);

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `account_no` varchar(30) DEFAULT NULL,
  `bank_name` varchar(30) DEFAULT NULL,
  `amount` varchar(30) DEFAULT NULL,
  `date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`pay_id`,`order_id`,`account_no`,`bank_name`,`amount`,`date`) values (1,1,'1234','sbi','3156','2021-03-11');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(25) NOT NULL,
  `quantity` varchar(25) NOT NULL,
  `category` varchar(25) NOT NULL,
  `price` varchar(25) NOT NULL,
  `photo` varchar(200) DEFAULT NULL,
  `seller_lid` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`product_name`,`quantity`,`category`,`price`,`photo`,`seller_lid`) values (2,'mango','10','vegitables','10','static/product/60-3.jpg',2),(3,'chilli powder','24','powder','23',NULL,2),(6,'tomato','7','vegitables','50','static/product/1-1.jpg',2),(8,'chilly','45','vegitables','144','static/product/10-1.jpg',2);

/*Table structure for table `rating_review` */

DROP TABLE IF EXISTS `rating_review`;

CREATE TABLE `rating_review` (
  `ratingid` int(11) NOT NULL AUTO_INCREMENT,
  `review` varchar(25) NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `rating` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ratingid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `rating_review` */

insert  into `rating_review`(`ratingid`,`review`,`userid`,`rating`,`date`) values (1,'very good',8,NULL,NULL),(2,'nice',8,NULL,NULL),(3,'uud',12,'5.0','2021-03-11'),(4,'',12,'5.0','2021-03-11'),(5,'',12,'0.5','2021-03-11');

/*Table structure for table `seller` */

DROP TABLE IF EXISTS `seller`;

CREATE TABLE `seller` (
  `sel_id` int(11) NOT NULL AUTO_INCREMENT,
  `sel_name` varchar(30) DEFAULT NULL,
  `sel_place` varchar(30) DEFAULT NULL,
  `sel_pin` varchar(30) DEFAULT NULL,
  `sel_post` varchar(30) DEFAULT NULL,
  `sel_phn` bigint(20) DEFAULT NULL,
  `sel_image` varchar(250) DEFAULT NULL,
  `sel_email` varchar(150) DEFAULT NULL,
  `sel_lid` int(11) DEFAULT NULL,
  `status` varchar(25) DEFAULT 'pending',
  PRIMARY KEY (`sel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `seller` */

insert  into `seller`(`sel_id`,`sel_name`,`sel_place`,`sel_pin`,`sel_post`,`sel_phn`,`sel_image`,`sel_email`,`sel_lid`,`status`) values (1,'MANU','nit','222','rec',9048335993,'/static/seller_image/49501ba1b4fa42cdc27e613bf68554ff.jpg','majhggujh@gmail.com',2,'accept'),(2,'soumya','chathamangalam','225','chatha',895988995565,'/static/seller_image/7-1.jpg','kjkhasgjf@gmail.com',3,'reject'),(3,'anjanana','palath','3457','palath',344567899,'/static/seller_image/1-1.jpg','dfghj@gmail.com',4,'accept'),(4,'ammu','kzd','5678','mavoor',98765421,'/static/seller_image/99814_adapted_720x1280.jpg','ammu3568@gmail.com',5,'pending'),(5,'chinnu','kannor','444','thalassery',6789765432,'/static/seller_image/99814_adapted_720x1280.jpg','fghjk@gmail.com',6,'reject'),(6,'asni','thamarasery','23456','odakunn',2345678,'/static/seller_image/2-1.jpg','asni@gmail.com',7,'pending'),(7,'lakshmi','chathmangalam','673601','chathamangalam',345677899,'/static/seller_image/1Z6A5712.JPG','lakshmi@gmail.com',12,'pending');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
