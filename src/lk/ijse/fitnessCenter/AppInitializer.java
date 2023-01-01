package lk.ijse.fitnessCenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class  AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("view/Intro.fxml")); //LoginForm
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(940);

        Image image = new Image("Logo.png");
        primaryStage.getIcons().add(image);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
}
