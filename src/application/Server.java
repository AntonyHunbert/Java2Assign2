package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private ServerSocket serverSocket;

  public static void main(String[] args) throws Exception {
    new Server().run();
  }
  
  public void run() throws IOException {
    serverSocket = new ServerSocket(8081);
    System.out.println("Server start working!");

    Socket player1 = null;



    
    while (true) {
      Socket player2 = serverSocket.accept();
      System.out.println("new player: " + player2.getRemoteSocketAddress());

      if (player1 == null) {
        player1 = player2;



      } else {
        System.out.println("matched, enjoy the game!");
        PrintWriter p = new PrintWriter(player2.getOutputStream());
        p.println("0@welcome!");
        p.flush();

        
        PlayerHandler1 ph = new PlayerHandler1(player1, player2);
        ph.start();
        player1 = null;
      }


    }
  }
  

}
