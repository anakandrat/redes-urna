//Esta classe implementa um thread no nervidor que vai gerenciar uma conexao com um cliente
//tratando as requisições e fazendo interface com o banco de dados
// para recuperar candidatos e computar votos

package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import server.db.JdbcSQLiteConnection;
import java.util.List;

public class ServerThread extends Thread {
    
    private Socket socket = null;
    
    Candidato candidato = new Candidato(); //Objeto Candidato
    List<Integer> votos;                   //Lista de votos recebidos quando a urna é finalizada 

    //Inicializa a classe associando um socket
    public ServerThread(Socket socket) {

        this.socket = socket;
    
    }

    //Gerenciamento das requisições
    public void run() {

        try (
                //Inicialização dos objetos para a comunicaçao
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {

            while (true) {
                //recupera o input do usuario no cliente
                String theInput = (String) in.readObject();
                
                System.out.println(theInput);

                //codigo para envio de lista de candidatos
                if (theInput.equals("999")) {
                    System.out.println("Enviando Lista de Candidatos...");
                    try{
                        //Recupera lista de candidatos do banco de dados e envia para o cliente
                        if (candidato.getCandidatos() != null) {
                            
                            out.writeObject(candidato.getCandidatos());

                        }
                    }catch(SQLException e){
                        System.out.println("SQLException");
                    }
                    System.out.println("Enviado");


                //codigo para recebimento de fechamento da urna
                } else if (theInput.equals("888")) {
                    
                    System.out.println("Recebendo votos...");
                    //Recebe os votos da urna finalizada
                    votos = (List<Integer>)in.readObject();
                    System.out.println(votos);
                    System.out.println("Finalizando conexao...");
                    
                    //Fecha a conexao com o socket, urna no cliente já não pode se comunicar
                    socket.close();
                    break;

                }
            }

            try{
                //Envia votos para o banco de dados 
                System.out.println("Computando Votos...");
                candidato.computarMultiplosVotos(votos);
                //Exibe as parciais
                System.out.println("\nParciais:");
                System.out.println(candidato.getParciais());
            }catch(SQLException se){
                System.out.println("SQLException");                    
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
