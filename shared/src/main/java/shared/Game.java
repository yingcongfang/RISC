package shared;

import javafx.util.Pair;

import java.util.ArrayList;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
  World world;
  ArrayList<Player> players;

  public Game() {
    World w = new World();
    this.world = w;
    this.players = new ArrayList<>();
  }

  public Game(int number_of_players) {
    this.players = new ArrayList<Player>();
    this.world = WorldInitialHelper.getWorld(number_of_players, this.players);
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World w) {
    this.world = w;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public boolean canMove(
      Territory t1,
      Territory t2,
      ArrayList<Pair<Integer, Integer>> units,
      Player lord,
      HashMap<Territory, ArrayList<Integer>> map,
      HashMap<Player, ArrayList<Integer>> resource) {
    // true means can move
    // check the right player
    if (t1.getLord() != lord || t2.getLord() != lord) {
      System.out.println("lord problem");
      return false;
    }
    // have path to destination and need to belong to same lord
    if (!world.hasPathBetween(t1, t2)) {
      System.out.println("path problem");
      return false;
    }
    // unit number should be larger than move number
    ArrayList<Integer> unit_in_terr = map.get(t1);
    Collections.sort(unit_in_terr);
    int need = 0;
    int size = 0;
    LinkedList<Territory> path = world.getPathBetween(t1, t2);
    for (Territory t : path) {
      size += t.getSize();
    }
    for (Pair<Integer, Integer> pair : units) {
      for (int i = 0; i < pair.getValue(); i++) {
        if (!unit_in_terr.contains(pair.getKey())) {
          System.out.println("Don't have units as ordered");
          return false;
        } else {
          unit_in_terr.remove(pair.getKey());
          map.get(t2).add(pair.getKey());
        }
      }
      need += size * pair.getValue();
    }
    if (need > resource.get(lord).get(0)) {
      System.out.println("Tech resource is not enough for upgrade");
      return false;
    }
    resource.get(lord).set(0, resource.get(lord).get(0) - need);
    return true;
  }

  public void move(Territory t1, Territory t2, ArrayList<Unit> to_move, Player lord) {
    System.out.println("Start move");
    t1.move(to_move, t2);
    LinkedList<Territory> path = world.getPathBetween(t1, t2);
    int need = 0;
    for (Territory t : path) {
      need += t.getSize();
    }
    need = need * to_move.size();
    lord.minusFood(need);
  }

  public boolean canAttack(
      Territory t1,
      Territory t2,
      ArrayList<Pair<Integer, Integer>> units,
      Player lord,
      HashMap<Territory, ArrayList<Integer>> map,
      HashMap<Player, ArrayList<Integer>> resource) {
    if (resource.get(lord).get(0) < 1) {
      System.out.println("don't have enough food");
      return false;
    }
    // check the right player
    if (t1.getLord() != lord || t2.getLord() == lord) {
      System.out.println("lord problem");
      return false;
    }
    // be adjacent to destination
    if (!t1.isAdjacentTo(t2)) {
      System.out.println("path problem");
      return false;
    }
    // unit number should be larger than attack number
    ArrayList<Integer> unit_in_terr = map.get(t1);
    Collections.sort(unit_in_terr);
    for (Pair<Integer, Integer> pair : units) {
      for (int i = 0; i < pair.getValue(); i++) {
        if (!unit_in_terr.contains(pair.getKey())) {
          System.out.println("Don't have the units as ordered");
          return false;
        }
        unit_in_terr.remove(pair.getKey());
      }
    }
    resource.get(lord).set(0, resource.get(lord).get(0) - 1);
    return true;
  }

  public void addAttackmap(
      Territory t1,
      Territory t2,
      ArrayList<Unit> units,
      Player lord,
      HashMap<Player, HashMap<Territory, ArrayList<Unit>>> attackmap) {
    if (attackmap.containsKey(lord)) {
      // this player already in the attackmap
      if (attackmap.get(lord).containsKey(t2)) {
        // this player already attack this territory, update the total attack units in this attack
        // movement
        for (Unit u : units) {
          attackmap.get(lord).get(t2).add(u);
        }
      } else {
        // player has never attack this territory, add the new attack into attackmap
        ArrayList<Unit> new_units = new ArrayList<>();
        for (Unit u : units) {
          new_units.add(u);
        }
        attackmap.get(lord).put(t2, new_units);
      }
    } else {
      // player is not in attackmap, add she and this attack movement in the map
      HashMap<Territory, ArrayList<Unit>> map = new HashMap<>();
      ArrayList<Unit> new_units = new ArrayList<>();
      for (Unit u : units) {
        new_units.add(u);
      }
      map.put(t2, new_units);
      attackmap.put(lord, map);
    }
    // decrease the number of units
    t1.minusUnits(units);
    lord.minusFood(1);
  }

  public void attack(HashMap<Player, HashMap<Territory, ArrayList<Unit>>> attackmap) {

    Dice dice = new Dice(players);
    // Get random player attack order
    dice.getRandomPlayer();
    for (Player p : players) {
      // whether this player have attack movement or not

      if (attackmap.containsKey(p)) {
        HashMap<Territory, ArrayList<Unit>> map = attackmap.get(p);
        // go through the territory player want to attack

        for (Territory key : map.keySet()) {
          ArrayList<Unit> attacker = map.get(key);
          ArrayList<Unit> defender = key.getUnits();
          System.out.println("attacker:" + attacker.size());
          System.out.println("defender:" + defender.size());
          Collections.sort(attacker);
          Collections.reverse(attacker);
          Collections.sort(defender);
          // start to fight
          while (defender.size() > 0 && attacker.size() > 0) {
            Unit att = attacker.get(0);
            Unit den = defender.get(0);
            System.out.println(
                "attacker bonus "
                    + attacker.get(0).getBonus()
                    + "defender bonus"
                    + defender.get(0).getBonus());
            if (dice.attackerWins(att.getBonus(), den.getBonus())) {
              // if attacker win
              defender.remove(den);
            } else {
              // if defender win
              attacker.remove(att);
            }
            Collections.reverse(attacker);
            Collections.reverse(defender);
          }

          if (defender.size() == 0) {
            // if the attacker win
            p.addTerr(key); // attacker add this territory
            key.getLord().deleteTerr(key); // defender delete this territory
            key.setLord(p); // this territory changes lord
            key.setUnits(attacker); // move the rest units after the fight on this territory
            System.out.println("Attack success!");
          } else {
            key.setUnits(defender);
            System.out.println("Attack failed!");
          }
        }
      }
    }
  }

  public boolean canUpgrade(
      Player player,
      Territory startplace,
      ArrayList<Pair<Integer, Integer>> units,
      HashMap<Player, ArrayList<Integer>> resource,
      HashMap<Territory, ArrayList<Integer>> map) {
    if (startplace.getLord() != player) {
      return false;
    }
    ArrayList<Integer> unit_in_terr = map.get(startplace);
    Collections.sort(unit_in_terr);
    int need = 0;
    for (Pair<Integer, Integer> pair : units) {
      int first = unit_in_terr.indexOf(pair.getKey());
      int last = unit_in_terr.lastIndexOf(pair.getKey());
      if (last - first + 1 < pair.getValue()) {
        System.out.println("Don't have such units as ordered");
        return false;
      }
      Unit unit = new Unit();
      unit.setLevel(pair.getKey());
      need += unit.needCost() * pair.getValue();
    }
    if (need > resource.get(player).get(1)) {
      System.out.println("Tech resource is not enough for upgrade");
      return false;
    }
    for (Pair<Integer, Integer> pair : units) {
      for (int i = 0; i < pair.getValue(); i++) {
        int pos = unit_in_terr.indexOf(pair.getKey());
        unit_in_terr.set(pos, unit_in_terr.get(pos) + 1);
      }
    }
    resource.get(player).set(1, resource.get(player).get(1) - need);
    return true;
  }

  public void upgrade(ArrayList<Unit> units, Player player) {
    for (Unit u : units) {
      player.minusTech(u.upgradeUnit());
      System.out.println(u.getLevel());
    }
  }

  private void handleOrder(Player player, Order order) {
    switch (order.getOrderType()) {
      case "upgrade":
        System.out.println("Upgrade");
        break;
      case "move":
        System.out.println("Move");
        break;
      case "attack":
        System.out.println("Attack");
        break;
      default:
        System.out.println("No such action");
        break;
    }
  }

  public void handleOrdersFromOnePlayer(OrdersList orders) {

    /* This method work with handleOrder method*/

    Player player = findPlayerByName(orders.getAssigneeName());
    for (Order order : orders.getOrdersList()) {
      this.handleOrder(player, order);
    }
  }

  public void handleAllOrders(List<OrdersList> all_orders) {
    HashMap<Player, HashMap<Territory, ArrayList<Unit>>> attackmap = new HashMap<>();
    for (OrdersList each_list : all_orders) {
      if (each_list == null) {
        continue;
      }
      // if all the orders in this list are valid
      System.out.println("Assignee Name: " + each_list.getAssigneeName());
      Player lord = findPlayerByName(each_list.getAssigneeName());
      each_list.sortOrder();
      ArrayList<Order> order_list = each_list.getOrdersList();
      // execution all the orders
      for (Order order : order_list) {
        String type = order.getOrderType();

        Territory start_place = findTerritoryByName(order.getStart_place());

        ArrayList<Unit> units = start_place.getUnitFromOrder(order.getUpgrade_list());
        if (type.equals("upgrade")) {
          upgrade(units, lord);
        }
        if (type.equals("move")) {
          Territory destination = findTerritoryByName(order.getDestination());
          move(start_place, destination, units, lord);
        }
        if (type.equals("attack")) {
          Territory destination = findTerritoryByName(order.getDestination());
          addAttackmap(start_place, destination, units, lord, attackmap);
        }
      }
    }
    attack(attackmap);
    world.addOneperRround();
    addResourcePerRound();
  }

  public boolean areValidOrders(OrdersList orders) {
    if (orders == null) {
      System.out.println("Null orderlist!");
      return false;
    }
    orders.sortOrder();
    HashMap<Territory, ArrayList<Integer>> map = world.getUnitMap();
    HashMap<Player, ArrayList<Integer>> resource_map = this.getPlayerSourceMap();
    Player lord = findPlayerByName(orders.getAssigneeName());
    ArrayList<Order> order_list = orders.getOrdersList();
    for (Order order : order_list) {
      // get information from order
      String type = order.getOrderType();
      Territory start_place = findTerritoryByName(order.getStart_place());

      ArrayList<Pair<Integer, Integer>> units = order.getUpgrade_list();
      if (start_place == null) {
        return false;
      }
      if (type.equals("upgrade")) {
        if (!canUpgrade(lord, start_place, units, resource_map, map)) {
          return false;
        }
      }

      if (type.equals("move")) {
        Territory destination = findTerritoryByName(order.getDestination());
        if (!canMove(start_place, destination, units, lord, map, resource_map)) {
          return false;
        }
      }
      if (type.equals("attack")) {
        Territory destination = findTerritoryByName(order.getDestination());
        if (!canAttack(start_place, destination, units, lord, map, resource_map)) {
          return false;
        }
      }
    }
    return true;
  }

  public HashMap<Player, ArrayList<Integer>> getPlayerSourceMap() {
    HashMap<Player, ArrayList<Integer>> map = new HashMap<>();
    for (Player lord : players) {
      ArrayList<Integer> resource = new ArrayList<>();
      // first one is food
      resource.add(lord.getFood());
      // second one is tech
      resource.add(lord.getTech_resource());
      map.put(lord, resource);
    }
    return map;
  }

  public Player hasWinner() {
    HashSet<Territory> territories = world.getTerritories();
    int num_of_terr = territories.size();
    if (this.players.isEmpty()) {
      return null;
    }
    for (Player lord : players) {
      // if the territory number of player equals to the total number of territory, this player win
      // the game
      int num = lord.getTerrNum();
      if (num == num_of_terr) {
        return lord;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    // print the game
    StringBuffer sb = new StringBuffer();
    for (Player player : players) {
      sb.append("\n" + player.getName() + "\n");
      sb.append("-----------------------" + "\n");
      HashSet<Territory> terrs = player.getTerritories();
      for (Territory t : terrs) {
        sb.append(t.toString() + "\n");
      }
    }
    return sb.toString();
  }

  public Player findPlayerByName(String target_name) {

    for (Player p : this.players) {
      if (p.getName().equals(target_name)) {
        System.out.println("Find player by name: " + p.getName());
        return p;
      }
    }
    System.out.println("Can't find this player in game");
    return null;
  }

  public Territory findTerritoryByName(String target_name) {
    HashSet<Territory> territories = world.getTerritories();
    for (Territory t : territories) {
      if (t.getName().toLowerCase().equals(target_name.toLowerCase())) {
        return t;
      }
    }
    System.out.println("Can't find this territory in game");
    return null;
  }

  public void addResourcePerRound() {
    for (Player player : players) {
      player.addResourcePerRound();
    }
  }
}
