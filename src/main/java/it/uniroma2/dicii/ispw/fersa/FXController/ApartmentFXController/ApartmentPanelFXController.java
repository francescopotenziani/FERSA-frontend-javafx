package it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import it.uniroma2.dicii.ispw.fersa.Bean.UserBean;
import it.uniroma2.dicii.ispw.fersa.Controller.ApartmentController;
import it.uniroma2.dicii.ispw.fersa.FXController.UserPanelFXController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.*;

public class ApartmentPanelFXController {
    @FXML
    private ListView lvapartment;
    private ApartmentBean apartmentBeanSelected;
    @FXML private Button backButton, fullScreenButton, editButton, enButton, itButton;
    private UserBean user;
    private ApartmentController controller;
    @FXML
    private ArrayList<ApartmentBean> apartmentBeanList;
    ObservableList observableList = FXCollections.observableArrayList();

    public void initialize(UserBean userSession) {
        this.user = userSession;
        this.controller = new ApartmentController();

        editButton.setDisable(true);
        lvapartment.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ApartmentBean>() {

            @Override
            public void changed(ObservableValue<? extends ApartmentBean> observable, ApartmentBean oldValue, ApartmentBean newValue) {
                apartmentBeanSelected = newValue;
                editButton.setDisable(false);
            }
        });

        apartmentBeanList = controller.findApartments(user);
        observableList.addAll(apartmentBeanList);
        lvapartment.setItems(observableList);
        lvapartment.setCellFactory(listView -> new CustomListView());

    }
    public void editApartment(){
        Stage stage = (Stage)editButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentEdit.fxml"),getMyBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApartmentEditFXController editController = fxmlLoader.<ApartmentEditFXController>getController();
        editController.initialize(user,controller, apartmentBeanSelected);

        stage.setTitle("FERSA -- APARTMENT EDIT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1300, 800));
        }
        stage.show();
    }
    public void userPanel(){
        Stage stage = (Stage)backButton.getScene().getWindow();
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

    public void changeToEnglishLanguage(){
        Stage stage = (Stage)enButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentPanel.fxml"),getMyEnglishBundle());
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

    public void changeToItalianLanguage(){
        Stage stage = (Stage)itButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentPanel.fxml"),getMyItalianBundle());
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

    public void setFullScreen(){
        Stage stage = (Stage)fullScreenButton.getScene().getWindow();
        stage.setFullScreen(true);
    }
}