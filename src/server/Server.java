package server;

import java.net.*;
import java.sql.SQLException;
import java.io.*;

import server.db.JdbcSQLiteConnection;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {

        int portNumber = 40104;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            JdbcSQLiteConnection.createTable();

            while (true) {

                new ServerThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}