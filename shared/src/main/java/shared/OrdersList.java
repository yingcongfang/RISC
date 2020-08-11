package shared;

import java.io.Serializable;
import java.util.*;
/** Class Name: OrdersList; Class Function: the Collection of the order for one player per round */
public class OrdersList implements Serializable {
  // the player who give the order list
  private String assignee;
  // the order list containing all order one round for one player
  private ArrayList<Order> orders_list = new ArrayList<>();
  // the comparator will be used to compare the order
  private Comparator com = new OrderCompareHelper();

  /**
   * OrdersList: the order list constructor to set the assignee
   *
   * @param assignee
   */
  public OrdersList(String assignee) {
    this.assignee = assignee;
  }

  /**
   * return the player who own the order list
   *
   * @return String - the player name
   */
  public String getAssigneeName() {
    return assignee;
  }

  /**
   * set the assignee of the order list
   *
   * @param assignee - the owner of the order list
   */
  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  /**
   * return the order list of this round
   *
   * @return ArrayList<Order> the order list of this round
   */
  public ArrayList<Order> getOrdersList() {
    return orders_list;
  }

  /**
   * set the order list to given list
   *
   * @param orders_list - given order list
   */
  public void setOrdersList(ArrayList<Order> orders_list) {
    this.orders_list = orders_list;
  }

  /**
   * add one order to the order list
   *
   * @param new_order - an order list to be added
   */
  public void addOrder(Order new_order) {
    this.orders_list.add(new_order);
  }

  /** sort the order */
  public void sortOrder() {
    Collections.sort(orders_list, com);
  }

  /** clear the order list for next round */
  public void clearOrderList() {
    orders_list.clear();
  }

  /**
   * get one order from player
   *
   * @return true if successfully get one order, return false if not
   */
  public boolean getOneOrder() {
    System.out.println(
        "You are the " + assignee + " player, what would you like to do? (D for done)");
    System.out.println("\t(M)ove");
    System.out.println("\t(A)ttack");
    System.out.println("\t(D)one");

    char action_type;
    while (true) {
      String input = InputHelper.getInputStringFromConsole();
      action_type = Character.toLowerCase(input.charAt(0));
      if (input.length() != 1 || (action_type != 'm' && action_type != 'a' && action_type != 'd')) {
        System.out.println("You input is not legal, please choose your order again.");
      } else {
        break;
      }
    }
    // add order to list here
    switch (action_type) {
      case 'm':
        this.addOrder(new Order("Move"));
        return true;
      case 'a':
        this.addOrder(new Order("Attack"));
        return true;
      default:
        return false;
    }
  }

  /** update all orders for one round */
  public void updateAllOrdersOfOneRound() {
    this.clearOrderList();
    boolean end_order = true;
    while (end_order) {
      end_order = getOneOrder();
    }
  }

  @Override
  public String toString() {
    int order_number[] = new int[3];
    for (Order ele : orders_list) {
      if (ele.getOrderType() == "upgrade") {
        order_number[0]++;
      } else if (ele.getOrderType() == "move") {
        order_number[1]++;
      } else {
        order_number[2]++;
      }
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      int number = order_number[i];
      if (i == 0) {
        sb.append("upgrade order number: " + number);
      } else if (i == 1) {
        sb.append("move order number: " + number);
      } else {
        sb.append("attack order number: " + number);
      }
      sb.append(";\n");
    }
    return sb.toString();
  }
}
