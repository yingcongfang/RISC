package shared;

import javafx.util.Pair;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameTest {
  private final ByteArrayOutputStream out_content = new ByteArrayOutputStream();
  private final ByteArrayOutputStream err_content = new ByteArrayOutputStream();
  private final PrintStream original_out = System.out;
  private final PrintStream original_err = System.err;
  Game game = null;

  public void restoreStreams() {
    System.setOut(original_out);
    System.setErr(original_err);
  }

  void test_construct() {
    int number_of_players = game.getPlayers().size();
    int number_of_territories = game.getWorld().getTerritories().size();
    assertEquals(3, number_of_players);
    assertEquals(12, number_of_territories);
  }

  void test_handleOrders() {
    OrdersList orders = Mockito.mock(OrdersList.class);

    Order move_action = new Order();
    move_action.setOrderType("move");

    Order attack_action = new Order();
    attack_action.setOrderType("attack");

    Order error_action = new Order();
    error_action.setOrderType("Error Action");

    Player test_player = new Player();
    test_player.setName("Mingxiang Dai");

    ArrayList<Order> test_orders =
        new ArrayList<Order>() {
          {
            add(move_action);
            add(attack_action);
            add(error_action);
          }
        };

    when(orders.getOrdersList()).thenReturn(test_orders);
    when(orders.getAssigneeName()).thenReturn(test_player.getName());
    game.handleOrdersFromOnePlayer(orders);
    String expected_output = "Move\nAttack\nNo such action\n";
    assertEquals(expected_output, out_content.toString());
  }

  @Test
  public void canmove_method_test() {
    Game game = new Game();
    Player A = new Player();
    A.setName("Drew");
    Player B = new Player();
    B.setName("Andrew");
    Territory t1 = new Territory("Elantris", 8, 8, A);
    Territory t2 = new Territory("Roshar", 7, 7, A);
    Territory t3 = new Territory("Scadrial", 15, 15, B);
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
    t1.addAdjacent(t3);
    t3.addAdjacent(t2);
    t2.addAdjacent(t3);
    t3.addAdjacent(t2);
    World w = game.getWorld();
    LinkedList<Territory> path = w.getPathBetween(t1, t2);
    for (Territory t : path) {
      System.out.println(t.getName());
    }
    w.setTerritories(t1);
    w.setTerritories(t2);
    w.setTerritories(t3);
    HashMap<Territory, ArrayList<Integer>> map = w.getUnitMap();
    game.addPlayer(A);
    game.addPlayer(B);
    HashMap<Player, ArrayList<Integer>> resource = game.getPlayerSourceMap();
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    ArrayList<Pair<Integer, Integer>> to_move = new ArrayList<>();
    Pair<Integer, Integer> p1 = new Pair<>(0, 4);
    to_move.add(p1);
    assertTrue(game.canMove(t1, t2, to_move, A, map, resource));
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    assertTrue(game.canMove(t1, t2, to_move, A, map, resource));
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    assertFalse(game.canMove(t1, t2, to_move, A, map, resource));
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    ArrayList<Unit> units = t1.getUnitFromOrder(to_move);
    System.out.println("units number:" + units.size());
    game.move(t1, t2, units, A);
    assertEquals(t1.getUnitsNum(), 4);
    assertEquals(t2.getUnitsNum(), 11);
    assertEquals(A.getFood(), 44);
    assertEquals(B.getFood(), 50);
  }

  @Test
  public void canattack_method_test() {
    Game game = new Game();
    Player A = new Player();
    A.setName("Drew");
    Player B = new Player();
    B.setName("Andrew");
    Territory t1 = new Territory("Elantris", 8, 8, A);
    Territory t2 = new Territory("Roshar", 7, 7, A);
    Territory t3 = new Territory("Scadrial", 15, 15, B);
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
    t1.addAdjacent(t3);
    t3.addAdjacent(t2);
    t2.addAdjacent(t3);
    t3.addAdjacent(t2);
    World w = game.getWorld();
    LinkedList<Territory> path = w.getPathBetween(t1, t2);
    for (Territory t : path) {
      System.out.println(t.getName());
    }
    w.setTerritories(t1);
    w.setTerritories(t2);
    w.setTerritories(t3);
    HashMap<Territory, ArrayList<Integer>> map = w.getUnitMap();
    game.addPlayer(A);
    game.addPlayer(B);
    HashMap<Player, ArrayList<Integer>> resource = game.getPlayerSourceMap();
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    ArrayList<Pair<Integer, Integer>> to_move = new ArrayList<>();
    Pair<Integer, Integer> p1 = new Pair<>(0, 4);
    to_move.add(p1);
    assertTrue(game.canAttack(t1, t3, to_move, A, map, resource));
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    assertTrue(game.canAttack(t1, t3, to_move, A, map, resource));
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    assertFalse(game.canAttack(t1, t3, to_move, A, map, resource));
    HashMap<Player, HashMap<Territory, ArrayList<Unit>>> attackmap = new HashMap<>();
    ArrayList<Unit> units = t1.getUnitFromOrder(to_move);
    game.addAttackmap(t1, t3, units, A, attackmap);
    game.attack(attackmap);
  }

  @Test
  public void canupdate_method_test() {
    Game game = new Game();
    Player A = new Player();
    A.setName("Drew");
    Player B = new Player();
    B.setName("Andrew");
    Territory t1 = new Territory("Elantris", 8, 8, A);
    Territory t2 = new Territory("Roshar", 7, 7, A);
    Territory t3 = new Territory("Scadrial", 15, 15, B);
    Territory t4 = new Territory("Elantris", 8, 8, new Resource("food", 30));
    t4.setLord(B);
    System.out.println(t4.getDetail());
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
    t1.addAdjacent(t3);
    t3.addAdjacent(t2);
    t2.addAdjacent(t3);
    t3.addAdjacent(t2);
    World w = game.getWorld();
    LinkedList<Territory> path = w.getPathBetween(t1, t2);
    for (Territory t : path) {
      System.out.println(t.getName());
    }
    w.setTerritories(t1);
    w.setTerritories(t2);
    w.setTerritories(t3);
    HashMap<Territory, ArrayList<Integer>> map = w.getUnitMap();
    game.addPlayer(A);
    game.addPlayer(B);
    HashMap<Player, ArrayList<Integer>> resource = game.getPlayerSourceMap();
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    ArrayList<Pair<Integer, Integer>> to_move = new ArrayList<>();
    Pair<Integer, Integer> p1 = new Pair<>(0, 4);
    to_move.add(p1);
    assertTrue(game.canUpgrade(A, t1, to_move, resource, map));
    Pair<Integer, Integer> p2 = new Pair<>(1, 2);
    to_move.add(p2);
    assertTrue(game.canUpgrade(A, t1, to_move, resource, map));
    System.out.println(resource.get(A).get(0) + "," + resource.get(A).get(1));
    to_move.remove(p2);
    ArrayList<Unit> units = t1.getUnitFromOrder(to_move);
    game.upgrade(units, A);
    to_move.remove(p1);
    to_move.add(p2);
    units = t1.getUnitFromOrder(to_move);
    game.upgrade(units, A);
    ArrayList<Unit> u1 = t1.getUnits();
    for (Unit u : u1) {
      System.out.println(u.getLevel());
    }
    assertEquals(A.getTech_resource(), 22);
  }

  @Test
  public void test_hashmap() {
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("Andrew", 77);
    map.put("Kay", 72);
    map.put("Iter", 222);
    map.put("brain", 456);
    for (String key : map.keySet()) {

      System.out.println("Key = " + key);
    }
  }

  @Test
  public void attack_test() {
    Game game = new Game();
    Player A = new Player("Drew");
    Player B = new Player("Andrew");
    game.addPlayer(A);
    game.addPlayer(B);
    Territory t1 = new Territory();
    Territory t2 = new Territory();
    t1.setLord(A);
    t2.setLord(B);
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
    //    t1.setUnits(15);
    //    t2.setUnits(10);
    World w = new World();
    w.setTerritories(t1);
    w.setTerritories(t2);
    game.setWorld(w);
    //    HashMap<Territory, Integer> map = w.getUnitMap();
    //    if (game.canAttack(t1, t2, 10, A, map)) {
    //      // game.addAttackmap(t1, t2, 10, A);
    //      // game.attack();
    //      System.out.println(t2.getLord().getName());
    //      System.out.println("t1 " + t1.getUnits());
    //      System.out.println("t2 " + t2.getUnits());
    //    }
  }
}
