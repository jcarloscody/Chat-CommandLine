package tcp.clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Timer;
import java.util.TimerTask;

public class ClienteGeral implements Runnable {
    ObjectInputStream objectInputStream;
    public ClienteGeral(ObjectInputStream objectInputStream){
      this.objectInputStream = objectInputStream;
    }
    @Override
    public void run() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                String serverResponse = null;
                try {
                    serverResponse = (String) objectInputStream.readObject();
                    if (serverResponse!="" || serverResponse!=null || serverResponse!=" "){
                        System.out.println( serverResponse + "\n Digite sua Mensagem: \n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 2000);


    }
}
