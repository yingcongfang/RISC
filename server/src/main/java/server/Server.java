package server;

import shared.*;
import java.util.Collections;

import java.util.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.*;
import java.lang.*;

public class Server {
  static volatile boolean[] threads_status = {
    false, false, false, false, false,
  };
  private final int port; // port number
  private final Game game;
  private ServerSocket server_socket; // Server end socket

  public Server(int port, int number_of_players) {
    /*
     * This constructor take one parameter: the int port This constructor will set
     * the server_socket based on the port
     */

    this.port = port;
    this.game = new Game(number_of_players);
    try {
      this.server_socket = new ServerSocket(port);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    final int N = Server.chooseNumberOfPlayers(2, 5);
    final int PORT = 12345;
    Server server = new Server(PORT, N);

    System.out.println("Waiting for players...");
    server.initializePlayers(N);
    server.sentGameToAllPlayers();
    while (server.getGame().hasWinner() == null) {
      server.playOneRound(N);
    }
    server.sendWinnerToAllPlayers(server.getGame().hasWinner());
    server.shutDown();
  }

  public static void callBack(int thread_idx) {
    System.out.printf("Child thread %d finished\n", thread_idx);
    threads_status[thread_idx] = true;
  }

  public static int chooseNumberOfPlayers(int lower_bound, int upper_bound) {
    System.out.println("How many players will be in the game?");
    System.out.printf("Please input an integer in range [%d, %d]: ", lower_bound, upper_bound);
    System.out.println();
    int ans;
    while (true) {
      ans = InputHelper.getInputIntFromConsole();
      if (ans >= lower_bound && ans <= upper_bound) {
        return ans;
      } else {
        System.out.printf(
            "Invalid input, the number of player must be an integer between %d and %d, please input again:\n",
            lower_bound, upper_bound);
      }
    }
  }

  public void sendWinnerToAllPlayers(Player winner) {
    for (Player p : this.game.getPlayers()) {
      Socket socket_of_current_player = p.getSocket();

      // Don't need to create new thread here
      ThreadHandler t = new ThreadHandler(socket_of_current_player, this.game);
      String string = winner.getName() + " is winner!";
      t.sendMessageToClient(string);
    }
  }

  public void initializePlayers(int number_of_players) {

    /*
     * This method will wait for some players connecting to server and add them to
     * game
     */

    for (int i = 0; i < number_of_players; i++) {
      try {
        Socket new_socket = this.server_socket.accept();
        PlayersInitializer new_initializer = new PlayersInitializer(new_socket, this.getGame(), i);
        Thread new_thread = new Thread(new_initializer);
        new_thread.start();
        try {
          new_thread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        System.err.println("IO error when sever accept socket from client");
        e.printStackTrace();
      }
    }
  }

  public void sentGameToAllPlayers() {
    for (Player p : this.game.getPlayers()) {
      Socket socket_of_current_player = p.getSocket();

      // Don't need to create new thread here
      ThreadHandler t = new ThreadHandler(socket_of_current_player, this.game);
      t.sendObjectToClient(this.getGame());
    }
  }

  public void playOneRound(int number_of_players) {

    List<OrdersList> orders_of_current_round =
        Collections.synchronizedList(new ArrayList<OrdersList>());
    List<Thread> all_threads = Collections.synchronizedList(new ArrayList<Thread>());
    List<ValidOrdersFetcher> all_fetchers =
        Collections.synchronizedList(new ArrayList<ValidOrdersFetcher>());

    // step 1: add threads and fethchers
    for (int i = 0; i < number_of_players; i++) {
      this.threads_status[i] = false;
      Socket current_socket = this.game.getPlayers().get(i).getSocket();
      ValidOrdersFetcher orders_fetcher = new ValidOrdersFetcher(current_socket, this.getGame(), i);
      all_threads.add(new Thread(orders_fetcher));
      all_fetchers.add(orders_fetcher);
    }

    // step 2: start fetching
    for (Thread t : all_threads) {
      t.start();
    }

    // step 3: wait finish
    while (true) {

      boolean all_players_finished = true;
      for (int i = 0; i < number_of_players; i++) {
        all_players_finished = all_players_finished & this.threads_status[i];
      }
      if (all_players_finished) {
        break;
      }
    }
    // step 4. add orders and join threads
    for (ValidOrdersFetcher f : all_fetchers) {
      orders_of_current_round.add(f.getOrdersList());
    }
    for (Thread t : all_threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // step 5. handle orders and sent result to player
    for (OrdersList o : orders_of_current_round) {
      System.out.println(o);
    }
    this.game.handleAllOrders(orders_of_current_round);
    this.sentGameToAllPlayers();
  }

  public Game getGame() {
    return game;
  }

  public void shutDown() {

    /* This method will close the server_socket */

    try {
      server_socket.close();
    } catch (IOException e) {
      System.err.println("IO error, cannot close server socket:");
      System.err.println(e.getMessage());
    }
  }

  public void addNewPlayer(String new_player, int idx) {
    ArrayList<Player> current_players = this.game.getPlayers();
    Player current_player = current_players.get(idx);
    current_player.setName(new_player);
  }
}
