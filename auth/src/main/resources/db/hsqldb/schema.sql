CREATE FUNCTION UTB(_uuid BINARY(36))
    RETURNS BINARY(16)
    LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
    RETURN
        UNHEX(CONCAT(
                SUBSTR(_uuid, 1, 8),
                SUBSTR(_uuid, 10, 4),
                SUBSTR(_uuid, 15, 4),
                SUBSTR(_uuid, 20, 4),
                SUBSTR(_uuid, 25) ));

CREATE FUNCTION UFB(_bin BINARY(16))
    RETURNS BINARY(36)
    LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
    RETURN
        LCASE(CONCAT_WS('-',
                        HEX(SUBSTR(_bin,  1, 4)),
                        HEX(SUBSTR(_bin,  5, 2)),
                        HEX(SUBSTR(_bin,  7, 2)),
                        HEX(SUBSTR(_bin,  9, 2)),
                        HEX(SUBSTR(_bin, 11))
            ));

-- 계정 테이블
CREATE TABLE `account` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `email` VARCHAR(200) NOT NULL COMMENT 'Email',
    `password` VARCHAR(200) NOT NULL COMMENT 'Password',
    `name` VARCHAR(20) DEFAULT NULL COMMENT '계정 사용자명',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`email`)
) COMMENT '계정정보';

-- 역할 테이블
CREATE TABLE `role` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT comment '역할 ID',
    `name` ENUM('ROLE_ADMIN', 'ROLE_USER'),
    PRIMARY KEY (`id`)
) COMMENT '역할';

-- 권한 테이블
CREATE TABLE `authority` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT comment '권한 ID',
    `name` ENUM('ACCOUNT_READ', 'ACCOUNT_WRITE') NOT NULL,
    PRIMARY KEY (`id`)
) COMMENT '권한';

-- role authority
CREATE TABLE `role_authority` (
    `role_id` BIGINT UNSIGNED NOT NULL comment '역할 ID',
    `authority_id` BIGINT UNSIGNED NOT NULL comment '권한 ID',
    PRIMARY KEY (`role_id`, `authority_id`),
    CONSTRAINT `role_authority_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `role_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`)
) COMMENT '역할별 계정';

-- 사용자 role
CREATE TABLE `account_role` (
    `account_id` BIGINT UNSIGNED NOT NULL comment '계정 ID',
    `role_id` BIGINT UNSIGNED NOT NULL comment '역할 ID',
    PRIMARY KEY (`account_id`, `role_id`),
    CONSTRAINT `account_role_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `account_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) COMMENT '계정별 역할';