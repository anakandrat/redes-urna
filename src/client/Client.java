//Classe principal do cliente

package client;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        String hostName = "cosmos.lasdpc.icmc.usp.br";
        int portNumber = 40104;

        Socket socket = new Socket(hostname, portNumber);
        
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        //Inicializa controlador
        Controller ctr = new Controller(in, out);


    }
}
