-- dlock table

CREATE TABLE `d_lock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `lock_time` varchar(45) DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL commont 'lock, unlock',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;