/*
SQLyog Ultimate v8.71 
MySQL - 5.5.43-MariaDB : Database - zeus
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zeus` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `zeus`;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` varchar(32) NOT NULL,
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '编码',
  `resource_id` varchar(32) DEFAULT NULL COMMENT '资源ID',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(40) NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `url` varchar(4000) DEFAULT NULL COMMENT 'URL',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '权限类型表(0链接,1操作按钮)',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级ID(如果为0，则为顶级)',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

/*Data for the table `resource` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(40) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `role` */

/*Table structure for table `role_perminssion_rel` */

DROP TABLE IF EXISTS `role_perminssion_rel`;

CREATE TABLE `role_perminssion_rel` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(32) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

/*Data for the table `role_perminssion_rel` */

/*Table structure for table `role_resource_rel` */

DROP TABLE IF EXISTS `role_resource_rel`;

CREATE TABLE `role_resource_rel` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `role_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `resource_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '权限ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源表';

/*Data for the table `role_resource_rel` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `username` varchar(40) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(40) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0:正常1禁用)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(255) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `locked_date` datetime DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_failure_count` int(11) NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`real_name`,`status`,`create_time`,`email`,`is_locked`,`locked_date`,`login_date`,`login_failure_count`,`login_ip`) values ('402882e74f88439d014f8843a3fb0000','admin','21232f297a57a5a743894a0e4a801fc3','admin',0,'2015-09-02 01:35:16','9999@qq.com','\0',NULL,'2015-09-01 17:37:09',0,NULL);

/*Table structure for table `user_role_rel` */

DROP TABLE IF EXISTS `user_role_rel`;

CREATE TABLE `user_role_rel` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `user_role_rel` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
