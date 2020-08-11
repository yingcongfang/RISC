package shared;

import org.junit.jupiter.api.Test;

import java.util.*;

class orderComparableTest {
  Comparator com = new OrderCompareHelper();
  OrdersList order = new OrdersList("aaa");

  @Test
  public void test_orderCompare() {
    order.addOrder(new Order("Attack"));
    order.addOrder(new Order("upgrade"));
    order.addOrder(new Order("Move"));
    order.addOrder(new Order("Attack"));
    order.addOrder(new Order("upgrade"));
    order.addOrder(new Order("Move"));
    /*
    Collections.sort(order.getOrdersList(), com);
    for (Order o : order.getOrdersList()) {
      System.out.println(o.getOrderType());
    }
    */
    order.sortOrder();
    for (Order o : order.getOrdersList()) {
      System.out.println(o.getOrderType());
    }
  }
}
