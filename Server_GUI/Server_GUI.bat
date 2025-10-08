@echo off 
mysql -u root -p < setup.sql
start javaw -jar Server_GUI.jar 8081
exit