#!/bin/bash
javac server/*.java
java -classpath .:../lib/sqlite-jdbc-3.8.11.2/sqlite-jdbc-3.8.11.2.jar server.Server

