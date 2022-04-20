-- dlock table

--version 0.2
CREATE TABLE `database_lock` (
  `id`     int(11)       NOT NULL AUTO_INCREMENT,
  `name`   varchar(100)  NOT NULL DEFAULT '' COMMENT '锁名称',
  `status` varchar(20)   NOT NULL DEFAULT '' COMMENT '锁状态,lock和unlock',
  `time`   varchar(45)   NOT NULL DEFAULT '' COMMENT '锁时间,字符串存储,计算在程序中',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='基于数据库的分布式锁';


--version 0.1
--CREATE TABLE `d_lock` (
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
--  `lock_time` varchar(45) DEFAULT NULL,
--  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL commont 'lock, unlock',
--  PRIMARY KEY (`id`),
--  UNIQUE KEY `unique_name` (`name`) USING HASH
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


