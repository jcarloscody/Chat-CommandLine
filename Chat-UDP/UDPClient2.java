import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClient2 {
  private DatagramSocket socket;
  private InetAddress address;
  private int port;

  public UDPClient2(String address, int port) throws SocketException, UnknownHostException {
    this.address = InetAddress.getByName(address);
    this.port = port;
    this.socket = new DatagramSocket();
  }

  public void send(String message) throws IOException {
    byte[] buffer = message.getBytes();
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
    socket.send(packet);
    buffer = new byte[256];
    packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet);
    String received = new String(packet.getData(), 0, packet.getLength());
    System.out.println("Received message: " + received);
  }

  public void close() {
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    UDPClient2 client = new UDPClient2("localhost", 1234);
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Enter message to send (end to exit): ");
      String message = scanner.nextLine();
      client.send(message);
      if (message.equals("end")) {
        break;
      }
    }

    scanner.close();
    client.close();
  }
}
