package server;

import java.io.*;
import java.net.Socket;

import shared.*;
class ThreadHandler {
  protected final Game the_latest_game;
  protected final Socket client_socket;
  protected OrdersList orders_list;

  public ThreadHandler(Socket init_socket, Game init_game) {
    this.client_socket = init_socket;
    this.the_latest_game = init_game;
  }

  //  @Override
  //  public void run() {
  //    String hello_string = this.receiveMessageFromClient();
  //    System.out.println(hello_string);
  //    this.sendObjectToClient(this.the_latest_game);
  //    this.updateOrdersFromClient();
  //  }

  public Object receiveObjectFromClient() {

    /* This method will keep waiting for client to sent an Object and return that object*/

    try {
      InputStream client_socket_is = this.client_socket.getInputStream();
      BufferedInputStream client_socket_bis = new BufferedInputStream(client_socket_is);
      ObjectInputStream object_stream_from_client = new ObjectInputStream(client_socket_bis);
      return object_stream_from_client.readObject();
    } catch (ClassNotFoundException e) {
      System.err.println("Cannot find class when receiving object from client:");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("IO error when receiving object from client:");
      e.printStackTrace();
    }
    return null;
  }

  public String receiveMessageFromClient() {
    return (String) this.receiveObjectFromClient();
  }

  public void updateOrdersFromClient() {

    /* This method will cast the object received to an ArrayList of Order
     *  and assign this.player to the player
     * */

    this.orders_list = (OrdersList) this.receiveObjectFromClient();
  }

  public OrdersList getOrdersList() {
    return this.orders_list;
  }

  public void sendObjectToClient(Object object_to_client) {

    /* This object will take an Object object_to_client
     * and send this object to the target_client
     */

    try {
      OutputStream output_stream_to_client = this.client_socket.getOutputStream();
      BufferedOutputStream buffered_stream_to_client =
          new BufferedOutputStream(output_stream_to_client);
      ObjectOutputStream object_stream_to_client =
          new ObjectOutputStream(buffered_stream_to_client);
      object_stream_to_client.writeObject(object_to_client);
      object_stream_to_client.flush();
    } catch (IOException e) {
      System.err.println("IO error when sending object to client:");
      e.printStackTrace();
    }
  }

  public void sendMessageToClient(String massage) {
    this.sendObjectToClient(massage);
  }
}
