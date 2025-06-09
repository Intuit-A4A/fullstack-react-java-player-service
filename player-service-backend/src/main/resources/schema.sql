DROP TABLE IF EXISTS PLAYERS;

-- Create a table from the csv
CREATE TABLE PLAYERS AS SELECT * FROM CSVREAD('player-service-backend/Player.csv');