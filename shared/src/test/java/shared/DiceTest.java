package shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DiceTest {
  @Test
  public void roll_dice_test() {
    Dice dice = new Dice();
    for (int i = 0; i < 20; i++) {
      System.out.println(dice.rollDiceAndGetResult());
    }
  }

  @Test
  public void random_player_test() {
    Game game = new Game();
    Player A = new Player("Drew");
    Player B = new Player("Andrew");
    Player C = new Player("Alex");
    Player D = new Player("Kate");
    Player E = new Player("Nancy");
    game.addPlayer(A);
    game.addPlayer(B);
    game.addPlayer(C);
    game.addPlayer(D);
    game.addPlayer(E);
    ArrayList<Player> p1 = game.getPlayers();
    for (Player p : p1) {
      System.out.println(p.getName());
    }
    Dice dice = new Dice(game.getPlayers());
    dice.getRandomPlayer();
    System.out.println("=======================================================");
    for (Player p : p1) {
      System.out.println(p.getName());
    }
  }
}
