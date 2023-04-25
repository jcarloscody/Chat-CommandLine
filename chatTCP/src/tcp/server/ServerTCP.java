package tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerTCP {
    public static String msg = "";

    public static final int PORT = 15898;
    public static List<ThreadCliente> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Features:::  \n  identificar quem estar conectado  \n  notificar quem entrou   \n notificar quem saiu  \n Notificar das mensagem enviadas");
        try {
            System.out.println("Incializando o servidor...");
            ServerSocket serv = new ServerSocket(PORT);
            System.out.println("Servidor iniciado, ouvindo a porta " + PORT );
            while(true) {
                Socket clie = serv.accept();
                ThreadCliente threadCliente = new ThreadCliente(clie);
                clients.add(threadCliente);
                threadCliente.key = clients.size()-1;
                threadCliente.start();
            }
        } catch(Exception e) {
            System.out.println("ERRO NO SERVIDOR DO TCP --------------------");
            e.printStackTrace();
        }
    }

    public static void  notifierEnter(String nome) throws IOException {
        for (int i = 0; i < clients.size()-1; i++) {
            clients.get(i).saida.writeObject( nome + " Entrou");
            clients.get(i).saida.flush();
        }
    }

    public static void  notifierOut(String name, int key) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).key!=key){
                clients.get(i).saida.writeObject(name + "  Saiu" );
            }
        }
    }

    public static void  notifierM(String mensagem, int key) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).key!=key){
                clients.get(i).saida.writeObject(mensagem);
            }
        }
    }
}
