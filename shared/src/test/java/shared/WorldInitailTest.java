package shared;

import java.util.ArrayList;

import org.junit.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldInitailTest {
  @Test
  public void worldInitialTest() {
    final boolean T = true;
    final boolean F = false;
    boolean[][] df = {
      //      0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14
      /*0 */ {F, T, T, T, T, F, F, F, F, F, F, F, F, F, F},
      /*1 */ {T, F, T, F, F, F, T, F, F, F, F, F, F, F, F},
      /*2 */ {T, T, F, F, T, F, T, T, F, F, F, F, F, F, F},
      /*3 */ {T, F, F, F, T, F, F, F, F, F, F, F, T, F, F},
      /*4 */ {T, F, T, T, F, T, F, T, F, F, F, F, T, T, F},
      /*5 */ {F, F, F, F, T, F, F, T, T, F, F, F, F, F, F},
      /*6 */ {F, T, T, F, F, F, F, T, F, T, F, F, F, F, F},
      /*7 */ {F, F, T, F, T, T, T, F, T, F, T, F, F, F, F},
      /*8 */ {F, F, F, F, F, T, F, T, F, F, T, T, F, T, F},
      /*9 */ {F, F, F, F, F, F, T, F, F, F, T, F, F, F, F},
      /*10*/ {F, F, F, F, F, F, F, T, T, T, F, T, F, F, F},
      /*11*/ {F, F, F, F, F, F, F, F, T, F, T, F, F, T, T},
      /*12*/ {F, F, F, T, T, F, F, F, F, F, F, F, F, T, F},
      /*13*/ {F, F, F, F, T, F, F, F, T, F, F, T, T, F, T},
      /*14*/ {F, F, F, F, F, F, F, F, F, F, F, T, F, T, F},
    };
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        assertEquals(df[i][j], df[j][i]);
      }
    }
  }

  @Test
  public void test_worldInitial() {
    World test = WorldInitialHelper.getWorld(3, new ArrayList<Player>());
    System.out.println(test.toString());
  }

  @Test
  public void test_size() {
    World test = WorldInitialHelper.getWorld(3, new ArrayList<Player>());
    HashSet<Territory> world = test.getTerritories();
    for (Territory t : world) {
      System.out.println(t.getSize());
    }
  }
}
