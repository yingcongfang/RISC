package shared;

import java.io.Serializable;
import java.net.Socket;
import java.util.HashSet;
/** Class Name: Player; Class Function: the Player of the game */
public class Player implements Serializable {
  // the territories belong to this player
  HashSet<Territory> territories;
  // the name of the player
  private String name;
  // the socket of the player to comunicate with server
  private transient Socket socket;
  private int user_id;

  private int tech_resource;
  private int food;

  /** the constructor of the player takes no parameter */
  public Player() {
    this.territories = new HashSet<Territory>();
    this.name = "";
    this.socket = null;
    this.tech_resource = 50;
    this.food = 50;
  }

  /**
   * the constructor of the player takes one parameter - the name of the player
   *
   * @param new_name - the name of the player
   */
  public Player(String new_name) {
    this.territories = new HashSet<Territory>();
    this.name = new_name;
    this.socket = null;
    this.tech_resource = 50;
    this.food = 50;
  }

  public int getUserId() {
    return user_id;
  }

  public void setUserId(int user_id) {
    this.user_id = user_id;
  }

  /**
   * delete specific territory
   *
   * @param territory - the territory to delete
   */
  public void deleteTerr(Territory territory) {
    territories.remove(territory);
  }

  /**
   * add one territory
   *
   * @param territory - the territory to add
   */
  public void addTerr(Territory territory) {
    territories.add(territory);
  }

  /**
   * return the socket of the player
   *
   * @return the socket of the player
   */
  public Socket getSocket() {
    return socket;
  }

  /**
   * set the socket of the player
   *
   * @param socket - the socket of the player
   */
  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  /**
   * return the name of the player
   *
   * @return the name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * set the name of the player
   *
   * @param new_name - the name of the player
   */
  public void setName(String new_name) {
    this.name = new_name;
  }

  /**
   * return how many territories the player has
   *
   * @return how many territories
   */
  public int getTerrNum() {
    return territories.size();
  }

  /**
   * add one territory to the player's land
   *
   * @param t - the land to add
   */
  public void addTerritory(Territory t) {
    this.territories.add(t);
  }

  /**
   * return all territories belong to the player
   *
   * @return one player's lands
   */
  public HashSet<Territory> getTerritories() {
    return territories;
  }

  public boolean minusTech(int num) {
    if (tech_resource < num) {
      return false;
    } else {
      tech_resource -= num;
    }
    return true;
  }

  public String getOverview() {
    StringBuilder sb = new StringBuilder();
    sb.append(name + " : " + tech_resource + " tech; " + food + " food;");
    return sb.toString();
  }

  public int getTech_resource() {
    return tech_resource;
  }

  public int getFood() {
    return food;
  }

  public void minusFood(int num) {
    this.food -= num;
  }

  public void addResourcePerRound() {
    for (Territory t : territories) {
      if (t.getResource().getType() == "food") {
        food += t.getResource().getAmount();
      } else {
        tech_resource += t.getResource().getAmount();
      }
    }
  }
}
