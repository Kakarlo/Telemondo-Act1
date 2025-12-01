
CREATE TABLE notes (
    id         BIGINT NOT NULL auto_increment,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    content    VARCHAR(255),
    username   VARCHAR(255),
    PRIMARY KEY (id)
)
engine=innodb;