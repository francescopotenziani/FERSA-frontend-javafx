package it.uniroma2.dicii.ispw.fersa.FXController;

import it.uniroma2.dicii.ispw.fersa.Bean.UserBean;
import it.uniroma2.dicii.ispw.fersa.Controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.util.locale.BaseLocale;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static it.uniroma2.dicii.ispw.fersa.Share.AlertHelper.showAlert;
import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.*;


public class LoginFXController {

    @FXML private Button loginButton, fullScreenButton, enButton, itButton;
    @FXML private TextField username;
    @FXML private PasswordField pass;
    private UserController controller;
    private UserBean user;
    public void login() {

        if (username.getText().isEmpty()) {
            Window owner = loginButton.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Error", getMyBundle().getString("login.username"));
            return;
        }
        if (pass.getText().isEmpty()) {
            Window owner = loginButton.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Error", getMyBundle().getString("login.password"));
            return;
        }

        user = new UserBean();
        user.setUsername(username.getText());
        user.setPassword(pass.getText());

        this.controller = new UserController();
        user = controller.Login(user);
        if (user == null){
            Window owner = loginButton.getScene().getWindow();
            showAlert(Alert.AlertType.ERROR, owner, "Error", getMyBundle().getString("login.error"));
            return;
        } else{
            Window owner = loginButton.getScene().getWindow();
            showAlert(Alert.AlertType.INFORMATION, owner, "Success",  getMyBundle().getString("login.success")
                    + user.getName() + " " + user.getSurname());
        }
        Stage stage = (Stage)loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/userPanel.fxml"),getMyBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserPanelFXController controller = fxmlLoader.<UserPanelFXController>getController();
        controller.initialize(user);

        stage.setTitle("FERSA -- USER PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }
    public void setFullScreen(){
        Stage stage = (Stage)fullScreenButton.getScene().getWindow();
        stage.setFullScreen(true);
    }
    public void changeToEnglishLanguage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),getMyEnglishBundle());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)enButton.getScene().getWindow();
        stage.setTitle("FERSA -- LOGIN");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void changeToItalianLanguage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),getMyItalianBundle());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)itButton.getScene().getWindow();
        stage.setTitle("FERSA -- LOGIN");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

}