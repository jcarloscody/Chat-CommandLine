import java.io.*;
import java.net.*;

public class UDPServer {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public UDPServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public void start() throws IOException {
        running = true;
        System.out.println("Server started...");

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message from " + packet.getAddress().getHostAddress() + ":" + packet.getPort()
                    + ": " + received);

            if (received.equals("end")) {
                running = false;
                continue;
            }

            // Envie uma mensagem de boas-vindas ao cliente
            String welcome = "Welcome to the chat!";
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] response = welcome.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(response, response.length, address, port);
            socket.send(responsePacket);

            // Ecoar a mensagem de volta para o cliente
            response = received.getBytes();
            responsePacket = new DatagramPacket(response, response.length, address, port);

            // Medir o tempo necessário para enviar a resposta
            long startTime = System.nanoTime();
            socket.send(responsePacket);
            long endTime = System.nanoTime();

            // Calcule a taxa de transferência em bytes por segundo
            double transferRate = (response.length / (double) (endTime - startTime)) * 1e9;
            System.out.println("Transfer rate: " + transferRate + " bytes/s");

            // Mostra uma mensagem de status quando um cliente se conecta
            System.out.println("Client connected: " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        }

        socket.close();
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) throws IOException {
        UDPServer server = new UDPServer(1234);
        server.start();
    }
}
