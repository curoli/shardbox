# Users schema
 
# --- !Ups
 
CREATE TABLE Session (
    idTime Long NOT NULL,
    idRandom Long NOT NULL,
    PRIMARY KEY (idTime, idRandom)
);
 
# --- !Downs
 
DROP TABLE Session;
