package server;

import shared.*;

import java.net.Socket;
import java.util.ArrayList;

class PlayersInitializer extends ThreadHandler implements Runnable {
  private int index;

  public PlayersInitializer(Socket init_socket, Game init_game, int init_index) {
    super(init_socket, init_game);
    this.index = init_index;
  }

  @Override
  public void run() {
    // String hello_string = this.receiveMessageFromClient();
    // System.out.println(hello_string);
    OrdersList init_empyt_list = (OrdersList) this.receiveObjectFromClient();
    ArrayList<Player> players = this.the_latest_game.getPlayers();
    Player player = players.get(index);
    System.out.println(init_empyt_list.getAssigneeName());
    player.setUserId(index);
    player.setName(init_empyt_list.getAssigneeName());
    player.setSocket(this.client_socket);
    // this.sendObjectToClient(this.the_latest_game);
    // this.updateOrdersFromClient();
  }
}
