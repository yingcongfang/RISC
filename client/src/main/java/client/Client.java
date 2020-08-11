package client;

import shared.*;

import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;

public class Client {
  final int port;
  String player_name;
  Socket client_socket;

  public Client(int port) {

    /*
     * This constructor take one parameter: the int port This constructor will set
     * the client_socket based on the port
     */
    this.player_name = InputHelper.getInputStringFromConsole();
    System.out.println(this.player_name);
    this.port = port;
    try {
      String host = "vcm-12521.vm.duke.edu";
      this.client_socket = new Socket(host, port);
    } catch (IOException e) {
      System.err.println("IO error, cannot set up client socket:");
      e.printStackTrace();
    }
  }

  public Client(int init_port, String init_name) {
    this.player_name = init_name;
    System.out.println(this.player_name);
    this.port = init_port;
    try {
      String host = "vcm-12521.vm.duke.edu";
      this.client_socket = new Socket(host, port);
    } catch (IOException e) {
      System.err.println("IO error, cannot set up client socket:");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    int port = 12345;
    Client client = new Client(port);
    client.sendEmptyOrdersListToServer();
    System.out.println("Waiting for other players...");

    while (true) { // Loop until somebody win
      Game the_latest_game = client.receiveGameFromServer();
      // try {
      // client.setPlayer(the_latest_game.findPlayerByName(client.player.getName()));
      // } catch (Exception e) {
      // e.printStackTrace();
      // }
      System.out.println(the_latest_game.toString());
      if (the_latest_game.hasWinner() != null) {
        String finish = client.receiveMessageFromServer();
        System.out.println(finish);
        break;
      } else {
        while (true) { // Loop until valid
          OrdersList orders_of_current_round = new OrdersList(client.player_name);
          orders_of_current_round.updateAllOrdersOfOneRound();
          client.sendObjectToServer(orders_of_current_round);
          System.out.println("sent");
          String massage_from_server = client.receiveMessageFromServer();
          System.out.println(massage_from_server);
          if (massage_from_server.contains("accepted")) {
            break;
          }
        }
      }
    }
    client.shutDown();
  }

  public String getName() {
    return this.player_name;
  }

  // public Player getPlayer() {
  // return player;
  // }

  public void sendObjectToServer(Object object_to_server) {

    /*
     * This object will take an Object object_to_server and send this object to the
     * server
     */

    try {
      OutputStream client_socket_os = this.client_socket.getOutputStream();
      BufferedOutputStream client_socket_bos = new BufferedOutputStream(client_socket_os);
      ObjectOutputStream client_socket_oos = new ObjectOutputStream(client_socket_bos);
      client_socket_oos.writeObject(object_to_server);
      client_socket_oos.flush();
    } catch (IOException e) {
      System.err.println("IO error: cannot send object to server:");
      System.err.println(e.getMessage());
    }
  }

  public void sendEmptyOrdersListToServer() {

    /*
     * This method should only be called one time That is when the client first
     * connect to server This method will send an empty list to server in order to
     * let server know the player's information
     */

    OrdersList empty_orders_list = new OrdersList(this.player_name);
    empty_orders_list.setAssignee(this.player_name);
    this.sendObjectToServer(empty_orders_list);
  }

  public Object receiveObjectFromServer() {

    /*
     * This method will keep waiting for client to sent an Object and return that
     * object
     */

    try {
      InputStream input_stream_from_server = this.client_socket.getInputStream();
      BufferedInputStream read_buffer_for_server =
          new BufferedInputStream(input_stream_from_server);
      ObjectInputStream object_stream_from_server = new ObjectInputStream(read_buffer_for_server);
      return object_stream_from_server.readObject();
    } catch (ClassNotFoundException e) {
      System.err.println("Cannot find class when receiving object from server:");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("IO error: cannot receive class from server:");
      e.printStackTrace();
    }
    return null;
  }

  public Game receiveGameFromServer() {

    /* This method will cast the object received to a Game */

    return (Game) this.receiveObjectFromServer();
  }

  public String receiveMessageFromServer() {

    /* This method will cast the object received to a String */

    return (String) this.receiveObjectFromServer();
  }

  public void shutDown() {

    /* This method will close the client_socket */

    try {
      this.client_socket.close();
    } catch (IOException e) {
      System.err.println("IO error: cannot close client socket:");
      e.printStackTrace();
    }
  }
}
