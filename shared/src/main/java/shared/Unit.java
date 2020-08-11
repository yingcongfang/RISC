package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Unit implements Comparable<Unit>, Serializable {
  private int level;
  // private Player lord;
  private int[] cost = new int[] {0, 3, 8, 19, 25, 35, 50};
  private String[] type =
      new String[] {"ant", "bee", "wasps", "squirrel", "rabbit", "dog", "tiger"};
  private int[] bonus_array = new int[] {0, 1, 3, 5, 8, 11, 15};

  public Unit() {
    this.level = 0;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int l) {
    this.level = l;
  }

  public int needCost() {
    return cost[level + 1];
  }

  public int getBonus() {
    return bonus_array[level];
  }

  public int upgradeUnit() {
    this.level++;
    return cost[level];
  }

  @Override
  public int compareTo(Unit compare) {
    return this.getBonus() - compare.getBonus();
  }
}
