CREATE TABLE `user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(10) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `role_id` INT NOT NULL DEFAULT 1,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    `deleted` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (`id`),
    INDEX `role_id` (`role_id` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE,
    UNIQUE INDEX `username_UNIQUE` (`username` ASC, `deleted` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Administrator accounts';

INSERT INTO `user` (`username`, `password`, `status`, `created`) VALUES ('admin', '$2a$10$IP8DHTYeqkCzijbDGlvc6eiePRqpgM2bCxhgFKWArIa9JurDZ0vCa', '1', NOW());

CREATE TABLE `role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    `deleted` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (`id`),
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC, `deleted` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

INSERT INTO `role` (`name`, `status`, `created`) VALUES ('admin', '1', NOW());
INSERT INTO `role` (`name`, `status`, `created`) VALUES ('test', '1', NOW());
INSERT INTO `role` (`name`, `status`, `created`) VALUES ('cs', '1', NOW());

CREATE TABLE `permission` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `page` VARCHAR(50) NOT NULL,
    `button` VARCHAR(50) NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    `deleted` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (`id`),
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE,
    INDEX `page` (`page` ASC) VISIBLE,
    UNIQUE INDEX `unique_id` (`page` ASC, `button` ASC, `deleted` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('member_page', '1', NOW());
INSERT INTO `permission` (`page`, `button`, `status`, `created`) VALUES ('member_page', 'update', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('permission_page', '1', NOW());
INSERT INTO `permission` (`page`, `button`, `status`, `created`) VALUES ('permission_page', 'update', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('role_page', '1', NOW());
INSERT INTO `permission` (`page`, `button`, `status`, `created`) VALUES ('role_page', 'update', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('game_page', '1', NOW());
INSERT INTO `permission` (`page`, `button`, `status`, `created`) VALUES ('game_page', 'update', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('payment_log_page', '1', NOW());
INSERT INTO `permission` (`page`, `button`, `status`, `created`) VALUES ('payment_log_page', 'export', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('statistics_page', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('notification_page', '1', NOW());
INSERT INTO `permission` (`page`, `button`, `status`, `created`) VALUES ('notification_page', 'update', '1', NOW());
INSERT INTO `permission` (`page`, `status`, `created`) VALUES ('operation_log_page', '1', NOW());

CREATE TABLE `role_permit` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `role_id` INT NOT NULL,
    `permit_id` INT NOT NULL,
    `status` TINYINT(2) NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE,
    UNIQUE INDEX `unique_id` (`role_id` ASC, `permit_id` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Page permission of administrator';

INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '1', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '2', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '3', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '4', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '5', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '6', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '7', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '8', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '9', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '10', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '11', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '12', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '13', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('1', '14', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '1', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '2', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '3', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '4', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '5', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '6', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '7', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '8', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '9', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '10', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '11', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '12', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '13', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('2', '14', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '1', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '2', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '3', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '4', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '5', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '6', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '7', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '8', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '9', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '10', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '11', '0', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '12', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '13', '1', NOW());
INSERT INTO `role_permit` (`role_id`, `permit_id`, `status`, `created`) VALUES ('3', '14', '0', NOW());

CREATE TABLE `member` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(13) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password` VARCHAR(100) NOT NULL COMMENT 'Encoded',
    `status` TINYINT(2) NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    `deleted` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (`id`),
    INDEX `email` (`email` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE,
    UNIQUE INDEX `username_UNIQUE` (`username` ASC, `deleted` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Membe login authentication';

CREATE TABLE `member_info` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `member_id` INT NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    `ip` VARCHAR(16) NULL,
    `point` DECIMAL(30,2) UNSIGNED NULL DEFAULT 0,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `member_id_UNIQUE` (`member_id` ASC) VISIBLE,
    INDEX `name` (`name` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Information of member';

CREATE TABLE `game` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(13) NOT NULL,
    `info` VARCHAR(30) NULL,
    `description` TEXT(3000) NULL,
    `price` DECIMAL(30,2) NOT NULL,
    `is_published` TINYINT(2) UNSIGNED NULL DEFAULT 0,
    `published` DATETIME NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    `deleted` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC, `deleted` ASC) VISIBLE,
    INDEX `is_published` (`is_published` ASC) VISIBLE,
    INDEX `published` (`published` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `game_type` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `code` VARCHAR(3) NOT NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

INSERT INTO `game_type` (`name`, `code`, `status`, `created`) VALUES ('角色扮演', 'RPG', '1', NOW());
INSERT INTO `game_type` (`name`, `code`, `status`, `created`) VALUES ('策略', 'SLG', '1', NOW());
INSERT INTO `game_type` (`name`, `code`, `status`, `created`) VALUES ('射擊', 'FPS', '1', NOW());
INSERT INTO `game_type` (`name`, `code`, `status`, `created`) VALUES ('動作', 'ACT', '1', NOW());
INSERT INTO `game_type` (`name`, `code`, `status`, `created`) VALUES ('冒險', 'AVG', '1', NOW());
INSERT INTO `game_type` (`name`, `code`, `status`, `created`) VALUES ('解謎', 'PZL', '1', NOW());

CREATE TABLE `picture` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `path` VARCHAR(50) NOT NULL,
    `reference_id` INT NULL,
    `reference_type` VARCHAR(13) NULL,
    `content_type` VARCHAR(30) NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `reference_id` (`reference_id` ASC) VISIBLE,
    INDEX `reference_type` (`reference_type` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE
)
COMMENT = 'Uploaded pictures';

CREATE TABLE `type_mapping` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `game_id` INT NOT NULL,
    `type_id` INT NOT NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_id` (`game_id` ASC, `type_id` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE `payment_log` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `member_id` INT NOT NULL,
    `member_name` VARCHAR(30) NOT NULL,
    `game_id` INT NOT NULL,
    `game_name` VARCHAR(13) NOT NULL,
    `point` DECIMAL(30,2) NOT NULL,
    `status` TINYINT(2) UNSIGNED NULL DEFAULT 1,
    `created` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_id` (`member_id` ASC, `game_id` ASC) VISIBLE,
    INDEX `status` (`status` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `member_name` (`member_name` ASC) VISIBLE,
    INDEX `game_name` (`game_name` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE `notification` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `member_id` INT NOT NULL,
    `is_new_game` TINYINT(2) UNSIGNED NULL DEFAULT 0,
    `created` DATETIME NOT NULL,
    `modified` DATETIME NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `unique_id` (`member_id` ASC, `is_new_game` ASC) VISIBLE,
    INDEX `member_id` (`member_id` ASC) VISIBLE,
    INDEX `is_new_game` (`is_new_game` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE,
    INDEX `modified` (`modified` ASC) VISIBLE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Notification for member';

CREATE TABLE `notification_log` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `member_id` INT NOT NULL,
    `member_name` VARCHAR(30) NOT NULL,
    `notify_type` VARCHAR(20) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `reference_id` INT NULL DEFAULT (0),
    `memo` VARCHAR(30) NULL,
    `created` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `member_id` (`member_id`),
    INDEX `notify_type` (`notify_type`),
    INDEX `reference_id` (`reference_id`),
    INDEX `created` (`created`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Notification Send Log';

CREATE TABLE `operation_log` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `username` VARCHAR(10) NOT NULL,
    `type` INT NOT NULL COMMENT '1:post, 2:put, 3:delete',
    `path` VARCHAR(30) NOT NULL,
    `parameter` TEXT(3000) NULL,
    `result` TEXT NOT NULL,
    `memo` VARCHAR(30) NULL,
    `created` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id` (`user_id` ASC) VISIBLE,
    INDEX `username` (`username` ASC) VISIBLE,
    INDEX `type` (`type` ASC) VISIBLE,
    INDEX `path` (`path` ASC) VISIBLE,
    INDEX `memo` (`memo` ASC) VISIBLE,
    INDEX `created` (`created` ASC) VISIBLE
)
ENGINE = MyISAM;
