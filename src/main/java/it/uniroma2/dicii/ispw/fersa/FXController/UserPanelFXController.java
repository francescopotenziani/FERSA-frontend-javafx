package it.uniroma2.dicii.ispw.fersa.FXController;

import it.uniroma2.dicii.ispw.fersa.Bean.UserBean;
import it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController.ApartmentPanelFXController;
import it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController.ContractPanelFXController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.*;

public class UserPanelFXController {
    private UserBean user;
    @FXML private Button apartmentButton, enButton, itButton;
    @FXML private Button contractButton;
    @FXML private Button fullScreenButton;
    @FXML private Button backButton;

    public void initialize(UserBean userSession){
        user = userSession;
    }

    public void apartmentPanel(){
        Stage stage = (Stage)apartmentButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentPanel.fxml"),getMyBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApartmentPanelFXController controller = fxmlLoader.<ApartmentPanelFXController>getController();
        controller.initialize(this.user);
        stage.setTitle("FERSA -- APARTMENT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void contractPanel() throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contractPanel.fxml"),getMyBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContractPanelFXController controller = fxmlLoader.<ContractPanelFXController>getController();
        controller.initialize(user);
        Stage stage = (Stage)contractButton.getScene().getWindow();
        stage.setTitle("FERSA -- CONTRACT PANEL");
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/userPanel.fxml"),getMyEnglishBundle());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)enButton.getScene().getWindow();
        stage.setTitle("FERSA -- USER PANEL");
        UserPanelFXController controller = fxmlLoader.<UserPanelFXController>getController();
        controller.initialize(this.user);
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1200, 600));
        }
        stage.show();
    }

    public void changeToItalianLanguage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/userPanel.fxml"),getMyItalianBundle());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)itButton.getScene().getWindow();
        stage.setTitle("FERSA -- USER PANEL");
        UserPanelFXController controller = fxmlLoader.<UserPanelFXController>getController();
        controller.initialize(user);
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void loginPage() throws IOException {
        ResourceBundle bundle = getMyBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)backButton.getScene().getWindow();
        stage.setTitle("FERSA -- LOGIN");
        stage.setScene(new Scene(root, 1090, 600));
        stage.show();
    }
}
