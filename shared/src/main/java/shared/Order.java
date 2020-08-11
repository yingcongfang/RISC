package shared;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

// class order
public class Order implements Serializable {
  private String order_type; // Either "move" or "attack" "upgrade"
  private String start_place;
  private String destination;
  private ArrayList<Pair<Integer, Integer>> upgrade_list; // key is level, value is number of units

  public Order() {}

  public Order(String type, String s, String d) {
    this.order_type = type;
    this.start_place = s;
    this.destination = d;
  }

  public Order(String action_type) {
    this.order_type = action_type.toLowerCase();
    // System.out.println("Please input the name of the source territory:");
    // this.start_place = InputHelper.getInputStringFromConsole().toLowerCase();
    // System.out.println("Please input the name of the target territory:");
    // this.destination = InputHelper.getInputStringFromConsole().toLowerCase();
    // System.out.println("Please input the number of the number you want to move.");
  }

  public String getStart_place() {
    return start_place;
  }

  public void setStart_place(String start_place) {
    this.start_place = start_place;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getOrderType() {
    return order_type;
  }

  public void setOrderType(String order_type) {
    this.order_type = order_type.toLowerCase();
  }

  public ArrayList<Pair<Integer, Integer>> getUpgrade_list() {
    return upgrade_list;
  }

  public void setUpgrade_list(ArrayList<Pair<Integer, Integer>> list) {
    this.upgrade_list = list;
  }
}
