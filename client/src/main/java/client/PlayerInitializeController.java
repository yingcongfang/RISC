package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shared.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.IOException;

public class PlayerInitializeController {

  @FXML public Label init_prompt;
  @FXML public TextField init_username;
  @FXML public Button init_ok_button;

  public void getPlayerName(ActionEvent event) throws Exception {

    if (event.getSource() == init_ok_button) {

      String input_username = this.init_username.getText();
      System.out.println("before");
      init_username.clear();
      init_username.setVisible(false);
      init_prompt.setText("Waiting for other players...");
      System.out.println("after");

      MainBoardController.client = new Client(12345, input_username);
      MainBoardController.client.sendEmptyOrdersListToServer();
      MainBoardController.game = MainBoardController.client.receiveGameFromServer();

      Node source = (Node) event.getSource();
      Stage old_stage = (Stage) source.getScene().getWindow();
      old_stage.close();
      // FXMLLoader main_loader = new FXMLLoader(getClass().getResource("fxml/mainBoardView.fxml"));
      Parent root = FXMLLoader.load(getClass().getResource("fxml/mainBoardView.fxml"));
      Stage stage = new Stage();
      stage.setTitle("RISK");
      stage.setScene(new Scene(root, 1200, 750));
      stage.show();
    }
  }
}
