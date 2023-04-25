package tcp.clients;


import tcp.server.ServerTCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Cliente2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",ServerTCP.PORT);
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome \n");
        String nome = scanner.nextLine();
        outToServer.writeObject("new-" + nome);
        String serverResponse = (String) inFromServer.readObject();
        System.out.println( serverResponse);

        ClienteGeral clienteGeral =   new ClienteGeral(inFromServer);
        clienteGeral.run();
        while (true){
            System.out.print("Digite uma mensagem: \n");
            String mensagem = scanner.nextLine();
            if (mensagem.equals("r")){
                System.out.println( (String) inFromServer.readObject());
            } else if(mensagem.equals("sair")){
                outToServer.writeObject(mensagem);
                socket.close();
                System.exit(0);
            }else {
                outToServer.writeObject(mensagem);
            }
            if (false){
                break;
            }
        }
        socket.close();
    }
}
