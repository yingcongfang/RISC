package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Pair;
import shared.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainBoardController implements Initializable {
  public static Client client;
  public static Game game;

  // texts and Messages
  @FXML public Text territory_details;
  @FXML public Text all_orders_text;
  @FXML public Text status_message;
  // Territories
  @FXML public Polygon territory00;
  @FXML public Polygon territory01;
  @FXML public Polygon territory02;
  @FXML public Polygon territory03;
  @FXML public Polygon territory04;
  @FXML public Polygon territory05;
  @FXML public Polygon territory06;
  @FXML public Polygon territory07;
  @FXML public Polygon territory08;
  @FXML public Polygon territory09;
  @FXML public Polygon territory10;
  @FXML public Polygon territory11;
  @FXML public Polygon territory12;
  @FXML public Polygon territory13;
  @FXML public Polygon territory14;
  // User input
  @FXML public TextField number_of_1;
  @FXML public TextField number_of_2;
  @FXML public TextField number_of_3;
  @FXML public TextField number_of_4;
  @FXML public TextField number_of_5;
  @FXML public TextField number_of_6;
  @FXML public TextField number_of_7;
  // buttons
  @FXML public Button add_to_list_button;
  @FXML public Button send_orders_list_button;
  // Source, target text field
  @FXML public TextField attack_source_text_field;
  @FXML public TextField attack_target_text_field;
  @FXML public TextField move_source_text_field;
  @FXML public TextField move_target_text_field;
  @FXML public TextField upgrade_target_text_field;
  // Tab pane
  @FXML public TabPane actions_tab_pane;
  @FXML public Tab attack_tab;
  @FXML public Tab move_tab;
  @FXML public Tab upgrade_tab;
  // message pane
  @FXML public TitledPane message_pane;
  private OrdersList orders_of_one_round = new OrdersList(client.player_name);
  // vector for easy maintaining
  private ArrayList<Polygon> all_polygons = new ArrayList<Polygon>();
  private ArrayList<TextField> all_text_fields = new ArrayList<TextField>();
  private ArrayList<Paint> theme_color0 =
      new ArrayList<Paint>() {
        {
          /*0*/ add(Color.color(228.0 / 255, 153.0 / 255, 143.0 / 255));
          /*1*/ add(Color.color(211.0 / 255, 144.0 / 255, 237.0 / 255));
          /*2*/ add(Color.color(170.0 / 255, 217.0 / 255, 237.0 / 255));
          /*3*/ add(Color.color(160.0 / 255, 228.0 / 255, 167.0 / 255));
          /*4*/ add(Color.color(174.0 / 255, 165.0 / 255, 254.0 / 255));
        }
      };

  private void hideExtraTerritories() {
    int number_of_players = this.game.getPlayers().size();
    for (int i = number_of_players * 3; i < this.all_polygons.size(); i++) {
      this.all_polygons.get(i).getParent().setVisible(false);
    }
  }

  private void putPolygonsInArrayList() {
    this.all_polygons.add(this.territory00);
    this.all_polygons.add(this.territory01);
    this.all_polygons.add(this.territory02);
    this.all_polygons.add(this.territory03);
    this.all_polygons.add(this.territory04);
    this.all_polygons.add(this.territory05);
    this.all_polygons.add(this.territory06);
    this.all_polygons.add(this.territory07);
    this.all_polygons.add(this.territory08);
    this.all_polygons.add(this.territory09);
    this.all_polygons.add(this.territory10);
    this.all_polygons.add(this.territory11);
    this.all_polygons.add(this.territory12);
    this.all_polygons.add(this.territory13);
    this.all_polygons.add(this.territory14);
  }

  private void setNamesOfTerritories() {

    ArrayList<String> name_of_all_territories =
        new ArrayList<String>() {
          {
            /* 0*/ add("Elantris");
            /* 1*/ add("Roshar");
            /* 2*/ add("Scadrial");
            /* 3*/ add("Narnia");
            /* 4*/ add("Midkemia");
            /* 5*/ add("Oz");
            /* 6*/ add("Hogwarts");
            /* 7*/ add("Mordor");
            /* 8*/ add("Condor");
            /* 9*/ add("Wayne");
            /*10*/ add("Durham");
            /*11*/ add("Yuland");
            /*12*/ add("Arcadia");
            /*13*/ add("Dailand");
            /*14*/ add("Fangland");
          }
        };
    for (int i = 0; i < this.all_polygons.size(); i++) {
      Polygon current_polygon = this.all_polygons.get(i);
      Group current_group = (Group) current_polygon.getParent();
      Label current_name = (Label) current_group.getChildren().get(1); // Label is the #1 element
      current_name.setText(name_of_all_territories.get(i));
    }
  }

  private Polygon findPolygonByName(String name) {
    for (int i = 0; i < this.all_polygons.size(); i++) {
      Polygon current_polygon = this.all_polygons.get(i);
      Group current_group = (Group) current_polygon.getParent();
      Label current_label = (Label) current_group.getChildren().get(1);
      String current_name = current_label.getText();
      if (current_name.equals(name)) {
        return current_polygon;
      }
    }
    return null;
  }

  public void updateOverview() {
    Player current_player = this.game.findPlayerByName(this.client.player_name);
    this.message_pane.setText(current_player.getOverview());
  }

  public void updateColorsOfPolygon() {
    for (Territory territory : this.game.getWorld().getTerritories()) {
      String territory_name = territory.getName();
      Player lord = territory.getLord();
      int user_id = lord.getUserId();
      Polygon current_polygon = this.findPolygonByName(territory_name);
      current_polygon.setFill(this.theme_color0.get(user_id));
    }
    this.updateOverview();
  }

  public void addAnOrderToList(ActionEvent actionEvent) {
    Tab selected_tab = this.actions_tab_pane.getSelectionModel().getSelectedItem();
    Order new_order = new Order();

    // fetch numbers of all levels
    ArrayList<Pair<Integer, Integer>> units_number = new ArrayList<Pair<Integer, Integer>>();
    for (int i = 0; i < 7; i++) {
      int number_of_current_level = Integer.parseInt(this.all_text_fields.get(i).getText());
      if (number_of_current_level != 0) {
        units_number.add(new Pair<Integer, Integer>(i, number_of_current_level));
      }
    }
    if (selected_tab.equals(this.attack_tab)) {
      new_order.setOrderType("attack");
      new_order.setStart_place(attack_source_text_field.getText());
      new_order.setDestination(attack_target_text_field.getText());
    } else if (selected_tab.equals(this.move_tab)) {
      new_order.setOrderType("move");
      new_order.setStart_place(move_source_text_field.getText());
      new_order.setDestination(move_target_text_field.getText());
    } else if (selected_tab.equals(this.upgrade_tab)) {
      new_order.setOrderType("upgrade");
      new_order.setStart_place(upgrade_target_text_field.getText());
    } else {
      throw new IllegalStateException(
          "Unexpected selected tab: "
              + this.actions_tab_pane.getSelectionModel().getSelectedItem());
    }
    new_order.setUpgrade_list(units_number);
    this.orders_of_one_round.addOrder(new_order);
    this.all_orders_text.setText(orders_of_one_round.toString());

    // reset text field
    for (TextField t : this.all_text_fields) {
      t.setText("0");
    }

    this.attack_source_text_field.setText("Drag or type source territory");
    this.attack_target_text_field.setText("Drag or type target territory");
    this.move_source_text_field.setText("Drag or type source territory");
    this.move_target_text_field.setText("Drag or type target territory");
    this.upgrade_target_text_field.setText("Drag or type target territory");
  }

  public void handleMouseEnterPolygon(MouseEvent mouse_event) {
    Polygon current_polygon = (Polygon) mouse_event.getSource();
    Group current_group = (Group) current_polygon.getParent();
    Label corresponding_label = (Label) current_group.getChildren().get(1);
    String corresponding_name = corresponding_label.getText();
    System.out.println(corresponding_name);
    Territory mouse_enter_territory = this.game.findTerritoryByName(corresponding_name);
    System.out.println(mouse_enter_territory);
    current_polygon.setStroke(Color.YELLOW);
    this.territory_details.setText(mouse_enter_territory.getDetail());
  }

  public void handleMouseExitPolygon(MouseEvent mouse_event) {
    Polygon current_polygon = (Polygon) mouse_event.getSource();

    current_polygon.setStroke(Color.BLACK);
    this.territory_details.setText("Put cursor on a territory");
  }

  public void polygonDragDetected(MouseEvent event) {
    /* drag was detected, start a drag-and-drop gesture*/
    /* allow any transfer mode */
    Polygon current_polygon = (Polygon) event.getSource();
    Group current_group = (Group) current_polygon.getParent();
    Label corresponding_label = (Label) current_group.getChildren().get(1);
    Dragboard db = current_polygon.startDragAndDrop(TransferMode.ANY);

    /* Put a string on a dragboard */
    ClipboardContent content = new ClipboardContent();
    content.putString(corresponding_label.getText());
    System.out.println(corresponding_label.getText());
    db.setContent(content);

    event.consume();
  }

  public void textFieldDragDropped(DragEvent event) {
    TextField current_text_flied = (TextField) event.getSource();
    Dragboard db = event.getDragboard();
    boolean success = false;
    if (db.hasString()) {
      current_text_flied.setText(db.getString());
      success = true;
    }
    event.setDropCompleted(success);

    event.consume();
  }

  public void textFieldDragOver(DragEvent event) {

    if (event.getDragboard().hasString()) {
      event.acceptTransferModes(TransferMode.MOVE);
    }

    event.consume();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.message_pane.setText("Message to " + this.client.getName());
    this.putElementsInUnits();
    this.setNamesOfTerritories();
    this.hideExtraTerritories();
    this.updateColorsOfPolygon();
  }

  private void putElementsInUnits() {
    this.putPolygonsInArrayList();
    this.putTextFieldsInArrayList();
  }

  private void putTextFieldsInArrayList() {
    this.all_text_fields.add(number_of_1);
    this.all_text_fields.add(number_of_2);
    this.all_text_fields.add(number_of_3);
    this.all_text_fields.add(number_of_4);
    this.all_text_fields.add(number_of_5);
    this.all_text_fields.add(number_of_6);
    this.all_text_fields.add(number_of_7);
  }

  public void sendOrdersListToServer(ActionEvent actionEvent) {
    this.client.sendObjectToServer(this.orders_of_one_round);
    this.orders_of_one_round = new OrdersList(this.client.player_name);
    String massage_from_server = this.client.receiveMessageFromServer();
    this.status_message.setText(massage_from_server);
    if (massage_from_server.contains("accepted")) {
      this.game = this.client.receiveGameFromServer();
      this.updateColorsOfPolygon();
    }
    if (this.game.hasWinner() != null) {
      String finish = client.receiveMessageFromServer();
      this.status_message.setText(finish);
    }

    // reset
    this.orders_of_one_round = new OrdersList(this.client.player_name);
    this.all_orders_text.setText("");
  }
}
