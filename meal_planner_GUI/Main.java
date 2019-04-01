package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class Main extends Application {
    public PrimaryGUI primaryGUI;

    @Override public void start(Stage primaryStage) {
        try {
            primaryGUI = new PrimaryGUI(new FoodData(), primaryStage);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
