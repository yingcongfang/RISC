package shared;

import java.util.ArrayList;
import java.util.Random;

public class Dice {
  private ArrayList<Player> players;

  public Dice() {}

  public Dice(ArrayList<Player> ps) {
    this.players = ps;
  }

  public void getRandomPlayer() {
    Random random = new Random();
    int size = players.size();
    for (int i = 0; i < size; i++) {
      // give random int to player to have a random sort of players
      int p = random.nextInt(i + 1);
      Player tmp = players.get(i);
      players.set(i, players.get(p));
      players.set(p, tmp);
    }
  }

  public int rollDiceAndGetResult() {
    // random number from 1 to 20
    Random rand = new Random();
    int randnum = rand.nextInt((20 - 1) + 1) + 1;
    return randnum;
  }

  public boolean attackerWins(int attacker_bon, int defender_bon) {
    // attacker and defender roll dice, if the attacker's number is bigger than defender's, return
    // true
    int attacker = rollDiceAndGetResult() + attacker_bon;
    int defender = rollDiceAndGetResult() + defender_bon;
    while (true) {
      if (attacker > defender) {
        return true;
      } else if (attacker == defender) {
        attacker = rollDiceAndGetResult() + attacker_bon;
        defender = rollDiceAndGetResult() + defender_bon;
      } else {
        return false;
      }
    }
  }
}
