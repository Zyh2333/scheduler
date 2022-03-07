CREATE TABLE IF NOT EXISTS `scheduler`
(
    `id`            bigint(5)    NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `name`          varchar(128) NOT NULL COMMENT '名称',
    `cron`          varchar(32) COMMENT '定时规则',
    `parent_name`   varchar(64)  NOT NULL COMMENT '父级名称',
    `execute_count` Integer(5)   NOT NULL DEFAULT 1 COMMENT '执行次数',
    `status`        Integer(5)   NOT NULL DEFAULT 0 COMMENT '状态',
    `special`       Integer(5)   NOT NULL DEFAULT 0 COMMENT '是否为特殊任务(0为false)',
    `distributed`   Integer(5)   NOT NULL DEFAULT 0 COMMENT '是否为分布式任务(0为false)',
    `sync_async`    Integer(5)   NOT NULL DEFAULT 0 COMMENT '同步异步任务(0为sync)',
    `gmt_update`    timestamp(0) NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `gmt_create`    timestamp(0) NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `scheduler_lock`
(
    `id`          bigint(5)    NOT NULL AUTO_INCREMENT COMMENT '任务锁ID',
    `scheduler_id` bigint(5)    NOT NULL COMMENT '任务ID用于上分布式锁',
    `gmt_update`  timestamp(0) NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `gmt_create`  timestamp(0) NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`scheduler_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;