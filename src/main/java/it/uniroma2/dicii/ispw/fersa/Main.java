package it.uniroma2.dicii.ispw.fersa;

import it.uniroma2.dicii.ispw.fersa.FXController.LoginFXController;
import it.uniroma2.dicii.ispw.fersa.Share.MessagesKeys;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.getMyBundle;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = getMyBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("FERSA -- LOGIN");
        primaryStage.setScene(new Scene(root, 1090, 600));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
