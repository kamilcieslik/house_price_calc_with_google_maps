package app;

import house_calc_library.PricesCalculator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HousePriceCalc extends Application {
    public static PricesCalculator pricesCalculator;
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        HousePriceCalc.mainStage = mainStage;
    }

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            HousePriceCalc.mainStage = primaryStage;
            loader.setLocation(getClass().getClassLoader().getResource("fxml/welcome_banner.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            mainStage.setTitle("Wishes Reminder");
            mainStage.getIcons().add(new Image("/image/icon.png"));
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.resizableProperty().setValue(Boolean.FALSE);
            mainStage.setScene(new Scene(root, 819, 325));
            WelcomeBannerController loaderController = loader.getController();
            mainStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> {
                Thread windowShownListener = new Thread(loaderController::initMainScene);
                windowShownListener.start();
            });
            mainStage.centerOnScreen();
            mainStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(HousePriceCalc.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        pricesCalculator = new PricesCalculator("AIzaSyBEmx5P3vl4ox4OU6nPgwTbU9k-_0Zm6Lo");
        launch(args);
    }
}
