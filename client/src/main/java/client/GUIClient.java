package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIClient extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("fxml/playerInitializeView.fxml"));
    primaryStage.setTitle("Welcome to RISK game!");
    Scene base_scene = new Scene(root, 300, 185.4);
    primaryStage.setScene(base_scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
