package lk.ijse.fitnessCenter.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IntroController implements Initializable{

    @FXML
    private ProgressBar pbIntro;
    @FXML
    private Text loadingText;
    @FXML
    private AnchorPane rootPane;
    LoadingScreen loadingScreen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingScreen = new LoadingScreen(pbIntro, loadingText);

        Thread thread = new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    //Loading screen runnable class
    public class LoadingScreen implements Runnable {

        ProgressIndicator pbIntro;
        Text loadingText;

        public LoadingScreen(ProgressIndicator progressIndicator, Text loadingText) {
            this.pbIntro = progressIndicator;
            this.loadingText = loadingText;
        }

        int load =0;

        @Override
        public void run() {
            while(pbIntro.getProgress() <= 1) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pbIntro.setProgress(pbIntro.getProgress() + 0.1);
                    }
                });
                synchronized (this){
                    try{
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }

                if (load == 0){
                    loadingText.setText(String.valueOf(load) + " %");
                    load = load + 10;
                }
                else {
                    load = load + 10;
                    loadingText.setText(String.valueOf(load) + " %");
                }
            }

            // Load to Login
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Parent parent = null;
                    try {
                        parent = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    rootPane.getScene().getWindow().hide();
                }
            });
        }
    }
}