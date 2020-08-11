package shared;

import java.io.Serializable;
import java.util.*;

public class World implements Serializable {
  HashSet<Territory> territories;

  public World() {
    territories = new HashSet<Territory>();
  }

  public World(int number_of_territories) {
    this.territories = new HashSet<Territory>();
    for (int i = 0; i < number_of_territories; i++) {
      this.territories.add(new Territory());
    }
  }

  public HashMap<Territory, ArrayList<Integer>> getUnitMap() {
    // return a map contains the unit numbers of every territory for validation check
    HashMap<Territory, ArrayList<Integer>> map = new HashMap<>();
    for (Territory t : territories) {
      ArrayList<Integer> bonus = new ArrayList<>();
      for (Unit u : t.getUnits()) {
        bonus.add(u.getBonus());
      }
      map.put(t, bonus);
    }
    return map;
  }

  public void addOneperRround() {
    // after every round, unit in every territory need to be add one
    for (Territory t : territories) {
      Unit unit = new Unit();
      ArrayList<Unit> units = new ArrayList<>();
      units.add(unit);
      t.addUnits(units);
    }
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    Iterator it = territories.iterator();
    for (Territory terr : territories) {
      sb.append(terr.toString() + "\n");
    }
    return sb.toString();
  }

  public HashSet<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(Territory terr) {
    this.territories.add(terr);
  }

  public void setTerritories(HashSet<Territory> terr) {
    this.territories = terr;
  }

  public boolean belongToSameLord(Territory t1, Territory t2) {
    return t1.getLord() == t2.getLord();
  }

  public LinkedList<Territory> getPathBetween(Territory t1, Territory t2) {
    LinkedList<Territory> path_between_territories = new LinkedList<>();
    if (!hasPathBetween(t1, t2) || t1 == t2) {
      return path_between_territories;
    }
    for (Territory t : territories) {
      t.parent_Territory = null;
      t.min_sum = Integer.MAX_VALUE;
    }
    Stack<Territory> stack = new Stack<Territory>();
    HashSet<Territory> visited = new HashSet<>();
    t1.min_sum = t1.getSize();
    stack.push(t1);
    while (!stack.isEmpty()) {
      Territory cur = stack.pop();
      HashSet<Territory> adjacent_set = cur.getAdjacentTerritories();
      for (Territory t : adjacent_set) {
        if (t.getLord() == t1.getLord() && t.parent_Territory != cur && cur.parent_Territory != t) {
          if (t.min_sum > t.getSize() + cur.min_sum) {
            t.parent_Territory = cur;
            t.min_sum = t.getSize() + cur.min_sum;
          }
          if (!stack.contains(t) && !visited.contains(t)) {
            stack.push(t);
          }
        }
      }
      visited.add(cur);
    }
    // put territory path between them.
    Territory cur = t2;
    while (cur.getName() != t1.getName()) {
      path_between_territories.addLast(cur);
      cur = cur.parent_Territory;
    }
    path_between_territories.add(t1);
    return path_between_territories;
  }

  // search the path for move
  public boolean hasPathBetween(Territory t1, Territory t2) {
    // base case
    if (t1.getLord() != t2.getLord()) {
      return false;
    }
    // base case
    if (t1.isAdjacentTo(t2)) {
      return true;
    }

    HashSet<Territory> visited = new HashSet<>();
    Stack<Territory> toSearch = new Stack<>();
    visited.add(t1);
    for (Territory t : t1.getAdjacentTerritories()) {
      if (t.getLord() == t1.getLord()) {
        toSearch.push(t);
        visited.add(t);
      }
    }
    while (!toSearch.empty()) {
      Territory tmp = toSearch.pop();
      if (tmp == t2) {
        return true;
      } else {
        for (Territory t : tmp.getAdjacentTerritories()) {
          if (t.getLord() == tmp.getLord() && !visited.contains(t)) {
            toSearch.push(t);
          }
        }
      }
      visited.add(tmp);
    }
    return false;
  }
}
