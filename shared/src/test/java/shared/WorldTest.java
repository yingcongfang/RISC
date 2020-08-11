package shared;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTest {
  @Test
  void test_get_path_between() {
    Player player1 = new Player();
    Territory Narnia = new Territory("Narnia", 10, 10, player1);
    Territory Midke = new Territory("Midkemia", 12, 12, player1);
    Territory Oz = new Territory("Oz", 8, 8, player1);
    Territory Gondor = new Territory("Gondor", 12, 12, player1);
    Territory Dayland = new Territory("dayland", 6, 6, player1);

    Narnia.addAdjacent(Midke);
    Midke.addAdjacent(Narnia);
    Midke.addAdjacent(Oz);
    Midke.addAdjacent(Dayland);
    Oz.addAdjacent(Gondor);
    Oz.addAdjacent(Midke);
    Gondor.addAdjacent(Dayland);
    Gondor.addAdjacent(Oz);
    Dayland.addAdjacent(Midke);
    Dayland.addAdjacent(Gondor);

    World w = new World();
    HashSet<Territory> world = new HashSet<>();
    world.add(Narnia);
    world.add(Midke);
    world.add(Oz);
    world.add(Gondor);
    world.add(Dayland);
    w.setTerritories(world);
    LinkedList<Territory> path = w.getPathBetween(Gondor, Narnia);
    // System.out.println(w.hasPathBetween(Gondor, Narnia));
    // System.out.println(Midke.parent_Territory.getName());
    System.out.println(path.size());
    for (int i = 0; i < path.size(); i++) {
      System.out.println(path.get(i).getName());
    }
  }

  @Test
  void test_has_path_between() {
    Player player1 = new Player();
    Player player2 = new Player();
    Territory Narnia = new Territory("Narnia", 10, player1);
    Territory Midke = new Territory("Midkemia", 12, player1);
    Territory Oz = new Territory("Oz", 8, player1);
    Territory Gondor = new Territory("Gondor", 13, player2);
    Territory Hog = new Territory("Hogwarts", 3, player1);
    Narnia.addAdjacent(Midke);
    Midke.addAdjacent(Oz);
    Oz.addAdjacent(Gondor);
    World w = new World();
    boolean Na_Oz = w.hasPathBetween(Narnia, Oz);
    // assertEquals(Na_Oz, true);
    boolean Na_Gondor = w.hasPathBetween(Narnia, Gondor);
    assertEquals(Na_Gondor, false);
    boolean Na_Hog = w.hasPathBetween(Narnia, Hog);
    assertEquals(Na_Hog, false);
  }

  @Test
  void test_belong_to_same_lord() {
    World w = new World();
    Player A = new Player();
    A.setName("Drew");
    Territory t1 = new Territory();
    Territory t2 = new Territory();
    t1.setLord(A);
    t2.setLord(A);
    assertTrue(w.belongToSameLord(t1, t2));
  }

  @Test
  void test_toString() {
    World w = new World();
    Player A = new Player();
    A.setName("Drew");
    Territory t1 = new Territory();
    Territory t2 = new Territory();
    t1.setName("T1");
    t2.setName("T2");
    t1.addAdjacent(t1);
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
    t2.addAdjacent(t2);
    t1.setLord(A);
    t2.setLord(A);
    w.setTerritories(t1);
    w.setTerritories(t2);
    System.out.println(w.toString());
  }
}
