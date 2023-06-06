CREATE TABLE user_settings
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted BIT(1) DEFAULT 0      NOT NULL,
    _uid       VARCHAR(255)          NULL,
    email      VARCHAR(255)          NULL,
    location   VARCHAR(255)          NULL,
    education  VARCHAR(255)          NULL,
    CONSTRAINT pk_user_settings PRIMARY KEY (id)
);