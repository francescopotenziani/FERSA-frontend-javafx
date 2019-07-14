package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ContractBean;
import it.uniroma2.dicii.ispw.fersa.Bean.UserBean;
import it.uniroma2.dicii.ispw.fersa.Controller.ContractUpdateController;
import it.uniroma2.dicii.ispw.fersa.Entity.StateEnum;
import it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController.ApartmentPanelFXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static it.uniroma2.dicii.ispw.fersa.Share.AlertHelper.showAlert;
import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.*;

public class ContractEditFXController {
    @FXML
    private Button enButton, itButton;
    @FXML
    private Button fullScreenButton, submitButton;
    @FXML
    private Button backButton;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private TextField rentalFeeTextField;
    private UserBean user;
    private ContractBean contractSelected;
    private ContractUpdateController controller;

    public void initialize(UserBean userBean, ContractBean contractBean, ContractUpdateController controller) {
        user = userBean;
        contractSelected = contractBean;
        this.controller = controller;
        startDatePicker.setValue(contractSelected.getStart_date());
        endDatePicker.setValue(contractSelected.getEnd_date());
        rentalFeeTextField.setText(contractSelected.getRentable().getFee().toString());
    }

    public void updateContract() throws SQLException {
        if (endDatePicker.getValue().isBefore(startDatePicker.getValue())){
            showAlert(Alert.AlertType.ERROR, backButton.getScene().getWindow(), "Error",
                    getMyBundle().getString("contract.edit.date.error"));
            return;
        }
        if (endDatePicker.getValue().isBefore(LocalDateTime.now().toLocalDate()) | startDatePicker.getValue().isBefore(LocalDateTime.now().toLocalDate())){
            showAlert(Alert.AlertType.ERROR, backButton.getScene().getWindow(), "Error",
                    getMyBundle().getString("contract.edit.date.error"));
            return;
        }
        contractSelected.setStart_date(startDatePicker.getValue());
        contractSelected.setEnd_date(endDatePicker.getValue());
        contractSelected.getRentable().setFee(Integer.parseInt(rentalFeeTextField.getText()));
        contractSelected.setState(StateEnum.WAITING);
        controller.updateContract(contractSelected);
        contractPanel();
    }

    public void contractPanel() throws SQLException {
        Stage stage = (Stage)backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contractPanel.fxml"),getMyBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContractPanelFXController controller = fxmlLoader.<ContractPanelFXController>getController();
        controller.initialize(user);
        stage.setTitle("FERSA -- CONTRACT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void changeToEnglishLanguage () {
        Stage stage = (Stage) enButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentPanel.fxml"), getMyEnglishBundle());
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApartmentPanelFXController controller = fxmlLoader.<ApartmentPanelFXController>getController();
        controller.initialize(this.user);
        stage.setTitle("FERSA -- APARTMENT PANEL");
        if (stage.isFullScreen()) {
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        } else {
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void changeToItalianLanguage () {
        Stage stage = (Stage) itButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentPanel.fxml"), getMyItalianBundle());
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApartmentPanelFXController controller = fxmlLoader.<ApartmentPanelFXController>getController();
        controller.initialize(this.user);
        stage.setTitle("FERSA -- APARTMENT PANEL");
        if (stage.isFullScreen()) {
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        } else {
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void setFullScreen () {
        Stage stage = (Stage) fullScreenButton.getScene().getWindow();
        stage.setFullScreen(true);
    }


}

