package shared;

import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerritoryTest {
  Player player1 = new Player();
  Player player2 = new Player();
  Territory Narnia = new Territory("Narnia", 10, player1);
  Territory Midke = new Territory("Midkemia", 12, player1);
  Territory Oz = new Territory("Oz", 8, player1);
  Territory Gondor = new Territory("Gondor", 13, player2);
  Territory Hog = new Territory("Hogwarts", 3, player1);

  @Test
  public void testHasPath() {
    Narnia.addAdjacent(Midke);
    Midke.addAdjacent(Oz);
    Oz.addAdjacent(Gondor);
    boolean Na_Oz = Narnia.hasPathTo(Oz);
    assertEquals(Na_Oz, true);
    boolean Na_Gondor = Narnia.hasPathTo(Gondor);
    assertEquals(Na_Gondor, false);
    boolean Na_Hog = Narnia.hasPathTo(Hog);
    assertEquals(Na_Hog, false);
  }

  @Test
  public void testToString() {
    Narnia.addAdjacent(Midke);
    Midke.addAdjacent(Oz);
    Oz.addAdjacent(Gondor);
    Territory a = new Territory();
    Territory b = new Territory();
    Territory c = new Territory();
    a.setName("TerryA");
    b.setName("TerryB");
    c.setName("TerryC");
    a.addAdjacent(b);
    a.addAdjacent(c);
    b.addAdjacent(a);
    b.addAdjacent(c);
    c.addAdjacent(a);
    c.addAdjacent(b);
    //    a.setUnits(5);
    //    b.setUnits(6);
    //    c.setUnits(7);
    String resultA = "5 units in TerryA (next to: TerryB, TerryC)";
    String resultB = "6 units in TerryB (next to: TerryC, TerryA)";
    String resultC = "7 units in TerryC (next to: TerryB, TerryA)";
    assertEquals(resultA, a.toString());
    System.out.println(a.toString());
    System.out.println(b.toString());
    System.out.println(c.toString());
  }
}
