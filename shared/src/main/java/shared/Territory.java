package shared;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/** Class Name: Territory; Class Function: The territory of the world */
public class Territory implements Serializable {
  Territory parent_Territory;
  int min_sum = Integer.MAX_VALUE;
  private HashSet<Territory> adjacent_territories;
  private String name;
  private Player lord;
  private ArrayList<Unit> units;
  private int size;
  private Resource resource;

  /** default constructor */
  public Territory() {
    this.name = null;
    this.units = new ArrayList<>();
    this.lord = null;
    this.adjacent_territories = new HashSet<Territory>();
    resource = new Resource();
  }

  public Territory(String name, int unit, int size, Resource resource) {
    units = new ArrayList<Unit>();
    for (int i = 0; i < unit; i++) {
      units.add(new Unit());
    }
    this.name = name;
    this.size = size;
    this.resource = resource;
    this.adjacent_territories = new HashSet<>();
  }

  public Territory(String name, int unit, int size, Player player) {
    units = new ArrayList<Unit>();
    this.lord = player;
    for (int i = 0; i < unit; i++) {
      units.add(new Unit());
    }
    this.name = name;
    this.size = size;
    this.adjacent_territories = new HashSet<>();
  }

  /**
   * constructor takes parameters of the territory name and the units of the territory
   *
   * @param name - the lord name
   * @param units - the units in the land
   */
  public Territory(String name, int units) {
    this.units = new ArrayList<>();
    this.name = name;
    this.lord = null;
    for (int i = 0; i < units; i++) {
      Unit unit = new Unit();
      this.units.add(unit);
    }
    this.adjacent_territories = new HashSet<Territory>();
    this.resource = new Resource();
  }

  /**
   * constructor takes name, units and the lord of this land
   *
   * @param name - the land name
   * @param units - the unit number
   * @param lord - the lord of the land
   */
  public Territory(String name, int units, Player lord) {
    this.name = name;
    this.lord = lord;
    for (int i = 0; i < units; i++) {
      Unit unit = new Unit();
      this.units.add(unit);
    }
    this.adjacent_territories = new HashSet<Territory>();
    this.resource = new Resource();
  }

  /**
   * constructor sets all parameters
   *
   * @param name
   * @param units
   * @param lord
   * @param adTerr
   */
  public Territory(String name, int units, Player lord, HashSet<Territory> adTerr) {
    this.name = name;
    for (int i = 0; i < units; i++) {
      Unit unit = new Unit();
      this.units.add(unit);
    }
    this.lord = lord;
    this.adjacent_territories = adTerr;
    this.resource = new Resource();
  }

  public String getDetail() {
    int level_number[] = new int[7];
    StringBuilder detail = new StringBuilder();
    detail.append("The name of this territory: " + name + "\n");
    detail.append("The lord of this territory: " + lord.getName() + "\n");
    detail.append("The number of each index: ");
    for (int i = 0; i < units.size(); i++) {
      int level = units.get(i).getLevel();
      level_number[level]++;
    }
    for (int i = 0; i < 7; i++) {
      int index = i + 1;
      if (level_number[i] != 0) {
        detail.append("level" + index + ":" + level_number[i] + " units;");
      }
    }
    detail.append("\n");
    detail.append("The size of this territory: " + this.size + "\n");
    detail.append(
        "The resource of this territory: "
            + this.resource.getAmount()
            + " "
            + this.resource.getType()
            + "\n");
    return detail.toString();
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  /**
   * return the lord of the land
   *
   * @return the lord of the land
   */
  public Player getLord() {
    return lord;
  }

  /**
   * set the lord of the land
   *
   * @param new_lord - the new lord of the land
   */
  public void setLord(Player new_lord) {
    this.lord = new_lord;
  }

  /**
   * return the name of the territory
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * set the name of the territory
   *
   * @param n
   */
  public void setName(String n) {
    this.name = n;
  }

  /**
   * return the unites of the land
   *
   * @return units
   */
  public int getUnitsNum() {
    return units.size();
  }

  public ArrayList<Unit> findUnits(ArrayList<Integer> nums) {
    ArrayList<Unit> new_unit = new ArrayList<>();
    for (int n : nums) {
      new_unit.add(units.get(n));
    }
    return new_unit;
  }

  public ArrayList<Unit> getUnits() {
    return this.units;
  }

  /**
   * set the unites of the land
   *
   * @param new_units - the number
   */
  public void setUnits(ArrayList<Unit> new_units) {
    this.units = new_units;
  }

  /**
   * decrease the unit of the land by num
   *
   * @param units - the number
   */
  public void minusUnits(ArrayList<Unit> units) {
    for (Unit unit : units) {
      this.units.remove(unit);
    }
  }

  /**
   * increase the unit of the land by num
   *
   * @param units - the number
   */
  public void addUnits(ArrayList<Unit> units) {
    for (Unit unit : units) {
      this.units.add(unit);
    }
  }

  /**
   * return adjacent territories of this land
   *
   * @return the adjacent list
   */
  public HashSet<Territory> getAdjacentTerritories() {
    return adjacent_territories;
  }

  /**
   * set adjacent territories of this land
   *
   * @param adjacent - the adjacent list
   */
  public void setAdjacentTerritories(HashSet<Territory> adjacent) {
    this.adjacent_territories = adjacent;
  }

  /**
   * check if a territory is adjacent to this land
   *
   * @param t - the land to check
   * @return is adjacent to the land or not
   */
  public boolean isAdjacentTo(Territory t) {
    for (Territory ele : adjacent_territories) {
      if (t == ele) {
        return true;
      }
    }
    return false;
  }

  /**
   * add one adjacent territory to this land
   *
   * @param t add to the adjacent list
   */
  public void addAdjacent(Territory t) {
    adjacent_territories.add(t);
  }

  /**
   * move some unites to another territory, move command use this function
   *
   * @param to_move move some units to another one
   * @param t the destination
   */
  public void move(ArrayList<Unit> to_move, Territory t) {
    this.minusUnits(to_move);
    System.out.println(this.units.size());
    t.addUnits(to_move);
    System.out.println(t.getUnitsNum());
  }

  /**
   * return description of the territory
   *
   * @return string
   */
  @Override
  public String toString() {
    StringBuilder st = new StringBuilder();
    st.append(units);
    st.append(" units in " + name);
    st.append(" (next to: ");
    for (Territory t : adjacent_territories) {
      st.append(t.name + ", ");
    }
    st.deleteCharAt(st.length() - 1);
    st.deleteCharAt(st.length() - 1);
    st.append(")");
    return st.toString();
  }

  /**
   * check if there is a path bettween this land and another territory
   *
   * @param dist - the territory to check
   * @return whether the territory and the land has path between
   */
  public boolean hasPathTo(Territory dist) {
    if (dist.lord != this.lord) {
      return false;
    }
    if (isAdjacentTo(dist)) {
      return true;
    }
    for (Territory t : adjacent_territories) {
      if (t.hasPathTo(dist) && t.lord == this.lord) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<Unit> getUnitFromOrder(ArrayList<Pair<Integer, Integer>> upgrade_list) {
    ArrayList<Unit> new_units = new ArrayList<>();
    for (Pair<Integer, Integer> p : upgrade_list) {
      int level = p.getKey();
      int num = p.getValue();
      System.out.println("pair is:" + level + "," + num);
      for (Unit u : units) {
        if (num == 0) {
          break;
        }
        if (u.getLevel() == level) {
          new_units.add(u);
          num--;
        }
      }
    }
    return new_units;
  }
}
