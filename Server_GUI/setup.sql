CREATE DATABASE IF NOT EXISTS MapDB;				-- Ricrea il database MapDB
USE MapDB;											

DROP TABLE IF EXISTS exampleTab;					-- Elimina la tabella exampleTab se esiste
CREATE TABLE exampleTab(							-- Ricrea la tabella exampleTab
	X1 float, 
	X2 float, 
	X3 float
);

-- Inserisce esempi nella tabella exampleTab
INSERT INTO exampleTab VALUES(1,2,0); 
INSERT INTO exampleTab VALUES(0,1,-1); 
INSERT INTO exampleTab VALUES(1,3,5); 
INSERT INTO exampleTab VALUES(1,3,4); 
INSERT INTO exampleTab VALUES(2,2,0); 

