CREATE TABLE `t_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `file_no` varchar(50) DEFAULT NULL COMMENT '文件编号',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
