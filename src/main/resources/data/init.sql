CREATE TABLE pokemon AS SELECT * FROM CSVREAD('classpath:data/csv/pokemons.csv');
CREATE TABLE type AS SELECT * FROM CSVREAD('classpath:data/csv/types.csv');
CREATE TABLE pokemon_type AS SELECT * FROM CSVREAD('classpath:data/csv/pokemon-types.csv');
