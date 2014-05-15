# Users schema
 
# --- !Ups
 
CREATE TABLE Session (
    idTime BIGINT NOT NULL,
    idRandom BIGINT NOT NULL,
    isAuthenticated BOOLEAN DEFAULT FALSE NOT NULL,
    userId VARCHAR(255),
    PRIMARY KEY (idTime, idRandom)
);
 
# --- !Downs
 
DROP TABLE Session;
