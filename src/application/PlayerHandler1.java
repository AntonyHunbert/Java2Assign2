package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerHandler1 extends Thread{
  //each playerHandler handles a game.
  private Socket player1;
  private InputStream is1;
  private OutputStream os1;
  private Socket player2;
  private InputStream is2;
  private OutputStream os2;

  private boolean playerTurn = true;

  public PlayerHandler1(Socket player1, Socket player2) throws IOException {
    this.player1 = player1;
    this.player2 = player2;
    is1 = this.player1.getInputStream();
    is2 = this.player2.getInputStream();
    os1 = this.player1.getOutputStream();
    os2 = this.player2.getOutputStream();

  }

  @Override
  public void run() {
    System.out.println("new game:@");
    Scanner sc1 = new Scanner(is1);
    Scanner sc2 = new Scanner(is2);
    PrintWriter pw1 = new PrintWriter(os1);
    PrintWriter pw2 = new PrintWriter(os2);

    String fromPlayer = "";

//    该线程用于处理player1的信息
    new Thread(() -> {
      String toPlayer = "";
      String fp2 = "";
      while (sc2.hasNextLine()){
        if (playerTurn){
          System.out.println("wait for fp2...");
          fp2 = sc2.nextLine();
          System.out.println("fp2:" + fp2);

          //        由收到玩家2的消息开始启动游戏
          System.out.println("from player2:" + fp2);
//        包装收到的信息，并提交给玩家1，此时需要判断，如果为11或10说明游戏结束，9说明开局
//        如果游戏结束，需要结束线程？
          int playerContent = Integer.parseInt(fp2);
          int playerCode = playerContent % 10;
          int playStatus = playerContent / 10;
          System.out.println("play status: " + playStatus);

          if(playStatus == 0){
//          1代表游戏进行中，将玩家2落子的位置发给1
            toPlayer = "1@" + playerCode;
            pw1.println(toPlayer);
            pw1.flush();
            System.out.println("to player1: " + toPlayer);
            playerTurn = !playerTurn;
          }
          else if(playStatus == 1){
//          0代表游戏进行中，1代表检测到自己胜利，2代表平局
            toPlayer = "2@" + playerCode;
            pw1.println(toPlayer);
            pw1.flush();

          }
          else {
//          0代表游戏结束，10代表1检测到平局
            toPlayer = "3@" + playerCode;
//          pw2.println(toPlayer);
//          pw2.flush();
            pw1.println(toPlayer);
            pw1.flush();
          }
          System.out.println("+++++++++++++++++++");
        }
      }
    }).start();

    new Thread(() -> {
      String fp1 = "";
      String toPlayer = "";
      while (sc1.hasNextLine()){
        if (!playerTurn){
          System.out.println("wait for fp1...");
          fp1 = sc1.nextLine();
          System.out.println("fp1:"+ fp1);

          //        由收到玩家1的消息开始启动游戏
          System.out.println("from player1:" + fp1);

//        包装收到的信息，并提交给玩家1，此时需要判断，如果为9或10说明游戏结束
//        如果游戏结束，需要结束线程？
          int playerContent = Integer.parseInt(fp1);
          int playerCode = playerContent % 10;
          int playStatus = playerContent / 10;
          if(playStatus == 0){
//          1代表游戏进行中，将玩家1落子的位置发给2，2处理后返回自己的落子
            toPlayer = "1@" + playerCode;
            pw2.println(toPlayer);
            pw2.flush();
            playerTurn = !playerTurn;
          }
          else if(playStatus == 1){
//          0代表游戏进行中，1代表1检测到自己胜利
            toPlayer = "2@" + playerCode;
            pw2.println(toPlayer);
            pw2.flush();
          }
          else {
//          0代表游戏结束，10代表1检测到平局
            toPlayer = "3@" + playerCode;
            pw2.println(toPlayer);
            pw2.flush();
          }
          System.out.println("-----------------");
        }
      }
    }).start();

//     new Thread(() -> {
//       String fp1 = "";
//       String toPlayer = "";
//       while (sc1.hasNextLine()){
//         if (!playerTurn){
//           System.out.println("wait for fp1...");
//           fp1 = sc1.nextLine();
//           System.out.println("fp1:"+ fp1);

//           //        由收到玩家1的消息开始启动游戏
//           System.out.println("from player1:" + fp1);

// //        包装收到的信息，并提交给玩家1，此时需要判断，如果为9或10说明游戏结束
// //        如果游戏结束，需要结束线程？
//           int playerContent = Integer.parseInt(fp1);
//           int playerCode = playerContent % 10;
//           int playStatus = playerContent / 10;
//           if(playStatus == 0){
// //          1代表游戏进行中，将玩家1落子的位置发给2，2处理后返回自己的落子
//             toPlayer = "1@" + playerCode;
//             pw2.println(toPlayer);
//             pw2.flush();
//             playerTurn = !playerTurn;
//           }
//           else if(playStatus == 1){
// //          0代表游戏进行中，1代表1检测到自己胜利
//             toPlayer = "2@" + playerCode;
//             pw2.println(toPlayer);
//             pw2.flush();
//           }
//           else {
// //          0代表游戏结束，10代表1检测到平局
//             toPlayer = "3@" + playerCode;
//             pw2.println(toPlayer);
//             pw2.flush();
//           }
//           System.out.println("-----------------");
//         }
//       }
//     }).start();



  }
}
