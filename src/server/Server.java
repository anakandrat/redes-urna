//Classe principal do servidor

package server;

import java.net.*;
import java.sql.SQLException;
import java.io.*;

import server.db.JdbcSQLiteConnection;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {

        int portNumber = 40104;
        
        //Cria banco de dados
        JdbcSQLiteConnection.createTable();

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            
            while (true) {
                //Novas threads por conexao
                new ServerThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.out.println("Erro");
            System.exit(-1);
        }
    }
}