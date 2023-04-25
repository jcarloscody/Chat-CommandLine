package tcp.clients;

import tcp.server.ServerTCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class GeralPrompt {
    public static void main(String[] args) throws IOException {


        Socket socket = new Socket("localhost", ServerTCP.PORT);

        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());

        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite uma mensagem: codigo-mensagem");
            String mensagem = scanner.nextLine();

            outToServer.writeObject(mensagem);

        }

    }
}
