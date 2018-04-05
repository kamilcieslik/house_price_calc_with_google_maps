package javafx.controller;

import app.HousePriceCalc;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomeBannerController implements Initializable {
    private static final int PROGRESS_BAR_SECONDS = 3;

    @FXML
    private ProgressBar progressBarAppLoading;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initMainScene() {
        try {
            runProgressBar(PROGRESS_BAR_SECONDS);
            loadMainScene();
        } catch (InterruptedException e) {
            loadMainScene();
        }
    }

    private void runProgressBar(int seconds) throws InterruptedException {
        Double progress = 1.0 / (seconds * 2);
        for (int i = 0; i < seconds * 2; i++) {
            Thread.sleep(500);
            progressBarAppLoading.progressProperty().set(progress + progressBarAppLoading.progressProperty().get());
        }
        Thread.sleep(100);
    }

    private void loadMainScene() {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));
                loader.load();
                Parent parent = loader.getRoot();
                Stage primaryStage = new Stage();
                HousePriceCalc.setMainStage(primaryStage);
                primaryStage.initStyle(StageStyle.DECORATED);
                primaryStage.setTitle("House Price Calc");
                primaryStage.setMinWidth(950);
                primaryStage.setMinHeight(950);
                primaryStage.getIcons().add(new Image("/image/icon.png"));
                primaryStage.setScene(new Scene(parent, 1600, 950));

                Stage stage = (Stage) progressBarAppLoading.getScene().getWindow();
                stage.hide();
                primaryStage.show();
            } catch (IOException ioEcx) {
                Logger.getLogger(WelcomeBannerController.class.getName()).log(Level.SEVERE, null, ioEcx);
            }
        });
    }
}
