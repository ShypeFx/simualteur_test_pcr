import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread{
    ServerSocket listener;
    int lenSocket = 10;
    Socket[] socketServer = new Socket[lenSocket];

    public ListenerThread(int port) {
        try {
            listener = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
    public void run(){
        for(int i=0; i<lenSocket; i++){
            try{
                //socketServer[i] = null;
                socketServer[i] = listener.accept();
                System.out.print(" Accept a client : ");
                System.out.println(i+1);
            } catch (IOException e){
                System.out.println(e);
                System.exit(1);
            }

        }
    }
}
