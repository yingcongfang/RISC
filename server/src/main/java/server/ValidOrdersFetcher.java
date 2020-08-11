package server;

import shared.*;

import java.io.IOException;
import java.net.Socket;

public class ValidOrdersFetcher extends ThreadHandler implements Runnable {
  int thread_idx;

  public ValidOrdersFetcher(Socket init_socket, Game init_game, int init_thread_idx) {

    super(init_socket, init_game);
    this.thread_idx = init_thread_idx;
  }

  @Override
  public void run() {

    while (true) {
      this.updateOrdersFromClient();
      // System.out.println(this.getOrdersList());
      if (this.the_latest_game.areValidOrders(this.orders_list)) {
        System.out.print(this.client_socket);
        System.out.println(" valid orders");
        this.sendMessageToClient("Orders accepted, waiting for other players");
        break;
      } else {
        System.out.print(this.client_socket);
        System.out.println(" invalid orders");
        this.sendMessageToClient("Some of your Orders are invalid, please change your orders");
      }
    }
    Server.callBack(this.thread_idx);
  }
}
