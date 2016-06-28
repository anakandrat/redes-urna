package client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        String hostName = "cosmos.lasdpc.icmc.usp.br";
        int portNumber = 40104;

        Socket socket = new Socket("127.0.0.1", portNumber);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        Controller ctr = new Controller(in, out);


    }
}
