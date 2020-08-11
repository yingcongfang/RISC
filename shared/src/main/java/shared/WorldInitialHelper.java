package shared;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class WorldInitialHelper {
  /**
   * return the initilization of the world according to how many players play the game.
   *
   * @param number_of_players - how many players
   * @param players - the player list
   * @return the initial world
   */
  public static World getWorld(int number_of_players, ArrayList<Player> players) {
    final int M = 3;
    final int num_of_territory = M * number_of_players;
    final boolean T = true;
    final boolean F = false;
    ArrayList<Territory> all_territories =
        new ArrayList<Territory>() {
          {
            // resource type:  food, tech
            // TODO no units initialized now
            /*0*/ add(new Territory("Elantris", 8, 8, new Resource("food", 15)));
            /*1*/ add(new Territory("Roshar", 7, 7, new Resource("food", 15)));
            /*2*/ add(new Territory("Scadrial", 15, 15, new Resource("tech", 15)));
            /*3*/ add(new Territory("Narnia", 10, 10, new Resource("food", 15)));
            /*4*/ add(new Territory("Midkemia", 12, 12, new Resource("food", 15)));
            /*5*/ add(new Territory("Oz", 8, 8, new Resource("tech", 15)));
            /*6*/ add(new Territory("Hogwarts", 6, 6, new Resource("food", 15)));
            /*7*/ add(new Territory("Mordor", 12, 12, new Resource("food", 15)));
            /*8*/ add(new Territory("Condor", 12, 12, new Resource("tech", 15)));
            /*9*/ add(new Territory("Wayne", 9, 9, new Resource("food", 15)));
            /*10*/ add(new Territory("Durham", 7, 7, new Resource("food", 15)));
            /*11*/ add(new Territory("Yuland", 14, 14, new Resource("tech", 15)));
            /*12*/ add(new Territory("Arcadia", 13, 13, new Resource("food", 15)));
            /*13*/ add(new Territory("Dailand", 6, 6, new Resource("food", 15)));
            /*14*/ add(new Territory("Fangland", 11, 11, new Resource("tech", 15)));
          }
        };
    // adjacent list
    boolean[][] all_adjacent = {
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
    ArrayList<Territory> territories = new ArrayList<>();
    for (int i = 0; i < num_of_territory; i++) {
      territories.add(
          new Territory(
              all_territories.get(i).getName(),
              all_territories.get(i).getUnitsNum(),
              all_territories.get(i).getSize(),
              all_territories.get(i).getResource()));
    }
    for (int i = 0; i < num_of_territory; i++) {
      // set correct adjacent list
      HashSet<Territory> adjacent_territories = new HashSet<>();
      for (int j = 0; j < num_of_territory; j++) {
        if (all_adjacent[i][j]) {
          adjacent_territories.add(territories.get(j));
        }
      }
      territories.get(i).setAdjacentTerritories(adjacent_territories);
    }

    for (int i = 0; i < number_of_players; i++) {
      Player new_player = new Player("Player" + ((Integer) i).toString());
      for (int j = 0; j < M; j++) {
        new_player.addTerritory(territories.get(M * i + j));
      }
      players.add(new_player);
    }

    World initial_world = new World();
    HashSet<Territory> toadd = new HashSet<>();
    for (int i = 0; i < num_of_territory; i++) {
      Territory current_territory = territories.get(i);
      current_territory.setLord(players.get(i / M));
      toadd.add(territories.get(i));
    }
    initial_world.setTerritories(toadd);
    return initial_world;
  }
}
