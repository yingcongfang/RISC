package shared;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class Name: OrderCompareHelper; Class Function: this method can compare the order to make move
 * order before attack order
 */
public class OrderCompareHelper implements Comparator<Order>, Serializable {
  /**
   * The method compare the order and return negtive number if o1 is smaller than o2, return
   * positive number if o1 is larger than o2, return 0 if they are equal.
   *
   * @param o1 - the order 1 to be compared
   * @param o2 - the order 2 to be compared
   * @return the compare result of the order
   */
  @Override
  public int compare(Order o1, Order o2) {
    if (o1.getOrderType().toLowerCase().equals("move")
        && o2.getOrderType().toLowerCase().equals("move")) {
      return 0;
    } else if (o1.getOrderType().toLowerCase().equals("attack")
        && o2.getOrderType().toLowerCase().equals("attack")) {
      return 0;
    } else if (o1.getOrderType().toLowerCase().equals("upgrade")
        && o2.getOrderType().toLowerCase().equals("upgrade")) {
      return 0;
    } else if (o1.getOrderType().toLowerCase().equals("upgrade")
        && (o2.getOrderType().toLowerCase().equals("attack")
            || o2.getOrderType().toLowerCase().equals("move"))) {
      return -1;
    } else if (o1.getOrderType().toLowerCase().equals("move")
        && o2.getOrderType().toLowerCase().equals("attack")) {
      return -1;
    }
    return 1;
  }
}
