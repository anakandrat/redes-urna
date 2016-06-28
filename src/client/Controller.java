//Esta classe tem o objetivo de ser uma interface entre a interface gráfica da urna
//e o servidor, é responsável por controlar as requisições e mandar para a interface

package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.*;


public class Controller {

	//Objetos para a comunicação
    private ObjectInputStream in;
    private ObjectOutputStream out;
    //Instancia da urna
    private Urna urna;
    //lista de votos atualizada a cada voto
    List<Integer> votos = new ArrayList<Integer>();

    //inicialização
    Controller(ObjectInputStream in, ObjectOutputStream out) {

        this.in = in;
        this.out = out;
        this.urna = new Urna(this);
        urna.setVisible(true);
        urna.setTitle("Urna");

    }

    //Processa a operação vinda da urna
    public void processVote(String voto) {

    	//Requisita a lista de candidatos
        if (voto.equalsIgnoreCase("999")) {

            try {
            	//envia o código de requisição
            	out.writeObject(voto);
            } catch (IOException ex) {
            	System.out.println("IOException");
            }

            try {
            	//Imprime a resposta no campo apropriado da urna
            	urna.getjTextArea1().setText((String)in.readObject());
            } catch (IOException ex) {
            	System.out.println("IOException");
            } catch ( ClassNotFoundException e){
            	System.out.println("ClassNotFoundException");
            }
        //Avisa que vai encerrar a votação da urna
        } else if (voto.equalsIgnoreCase("888")) {

            try {
            	//Envia código de encerramento
                out.writeObject(voto);
                //Envia os votos
                out.writeObject(votos);
            } catch (IOException ex) {
            	System.out.println("IOException");
            }
            
            urna.jTextFieldVoto.setText("Votação encerrada");

        } else {
        	//armazena votos
        	//no banco de dados, os votos para candidatos não registrados serão descartados como nulos.
        	votos.add(Integer.parseInt(voto));
        }

    }


}