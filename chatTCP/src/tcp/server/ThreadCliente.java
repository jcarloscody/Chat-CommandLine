package tcp.server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ThreadCliente extends Thread {


    public Socket cliente;
    public String nome;

    public int key = 0;
    public  ObjectInputStream entrada;
    public  ObjectOutputStream saida;

    public ThreadCliente(Socket cliente) throws IOException {
        this.cliente = cliente;
        entrada = new ObjectInputStream(cliente.getInputStream());
        saida = new ObjectOutputStream(cliente.getOutputStream());
    }

    @Override
    public void run() {
        try {

            String conectado = "";
            int quantidadeConectados = 0;
            for (int i = 0; i < ServerTCP.clients.size()-1; i++) {
                conectado += ServerTCP.clients.get(i).nome + ", ";
                quantidadeConectados++;
            }
            if (quantidadeConectados>0){
                saida.writeObject(" Conectado \n " + key + " é a sua chave. \n Temos " + quantidadeConectados + " conectados. " + conectado);
            }else {
                saida.writeObject(" Conectado \n " + key + " é a sua chave. \n Temos apenas voce conectado no momento." );
            }
            while (true) {
                String arquivo = (String) entrada.readObject();
                String[] msg = arquivo.split("-");
                if (msg[0].equals("new")) {
                    this.nome = msg[1];
                    ServerTCP.notifierEnter(msg[1]);
                } else if (msg[0].equals("sair")) {
                ServerTCP.notifierOut(this.nome, key);
                    entrada.close();
                    saida.close();
                    cliente.close();
                ServerTCP.clients.remove(key);
                Thread.interrupted();
                break;
                } else {
                    ServerTCP.notifierM(nome + " Disse: " + arquivo, key);
                }
            }
        } catch (Exception e) {
            System.out.println("Excecao ocorrida na thread: " + e.getMessage());
            try {
                cliente.close();
            }
            catch(Exception ec) {
                System.out.println("Excecao ocorrida na thread: ---------- " + e.getMessage());
            }
        }

    }


}
