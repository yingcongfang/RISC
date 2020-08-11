package shared;

import java.io.Serializable;

public class Resource implements Serializable {
  private String type; // food,tech
  private int amount;

  public Resource(String type, int amount) {
    this.type = type;
    this.amount = amount;
  }

  public Resource() {
    type = "";
    amount = 0;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
