CREATE TABLE IF NOT EXISTS `scheduler`
(
    `id`            bigint(5)    NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `name`          varchar(128) NOT NULL COMMENT '名称',
    `cron`          varchar(32) COMMENT '定时规则',
    `parent_name`   varchar(64)  NOT NULL COMMENT '父级名称',
    `execute_count` Integer(5)   NOT NULL DEFAULT 1 COMMENT '执行次数',
    `status`        Integer(5)   NOT NULL DEFAULT 0 COMMENT '状态',
    `gmt_update`    timestamp(0) NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `gmt_create`    timestamp(0) NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;