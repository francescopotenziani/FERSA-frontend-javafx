package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;


import it.uniroma2.dicii.ispw.fersa.Bean.ContractBean;
import it.uniroma2.dicii.ispw.fersa.Bean.UserBean;
import it.uniroma2.dicii.ispw.fersa.Controller.ContractDeleteController;
import it.uniroma2.dicii.ispw.fersa.Controller.ContractUpdateController;
import it.uniroma2.dicii.ispw.fersa.Entity.Contract;
import it.uniroma2.dicii.ispw.fersa.Entity.StateEnum;
import it.uniroma2.dicii.ispw.fersa.FXController.UserPanelFXController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.uniroma2.dicii.ispw.fersa.Share.AlertHelper.showAlert;
import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.*;

public class ContractPanelFXController {
    @FXML
    Button backButton,enButton,itButton,fullScreenButton,submitButton,deleteButton;
    @FXML
    private ListView contractList;
    private UserBean user;
    ObservableList observableList = FXCollections.observableArrayList();
    private ContractUpdateController controller;
    private ContractBean contractSelected;
    private ArrayList<ContractBean> contractArrayList;
    private volatile boolean tExit = false;
    ContractPanelThread thread;


    public void initialize(UserBean userSession) throws SQLException {
        user = userSession;
        this.controller = new ContractUpdateController();
        submitButton.setDisable(true);
        deleteButton.setDisable(true);


        contractList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContractBean>() {

            @Override
            public void changed(ObservableValue<? extends ContractBean> observable, ContractBean oldValue, ContractBean newValue) {
                contractSelected = newValue;
                submitButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
        contractArrayList = controller.findContract(user);
        observableList.addAll(contractArrayList);
        contractList.setItems(observableList);
        contractList.setCellFactory(List -> new CustomListViewContract());
        thread = new ContractPanelThread(contractArrayList,controller,observableList,contractList);
        Thread thread1 = new Thread(thread);
        thread1.start();
        //        new Thread(() -> {
//            while (!tExit){
//                for (ContractBean c: contractArrayList){
//                    if (controller.checkContract(c)){
//                        Platform.runLater(() -> {
//                            observableList.clear();
//                            observableList.addAll(contractArrayList);
//                            contractList.setItems(observableList);
//                            contractList.setCellFactory(List -> new CustomListViewContract());
//                            return;
//                    });
//                    }
//                }
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Thread.currentThread().interrupt();
//        }).start();
    }

    public void deleteContract() {
        thread.interruptThread();
        if (contractSelected.getState() == StateEnum.UNAPPROVED){
            ContractDeleteController controllerEditContract = new ContractDeleteController();
            if (controllerEditContract.deleteContract(contractSelected)){
            observableList.remove(contractSelected);
            contractList.setItems(observableList);
            contractList.setCellFactory(List -> new CustomListViewContract());
            return;
            } else{
                showAlert(Alert.AlertType.ERROR, submitButton.getScene().getWindow(), "Error",
                        getMyBundle().getString("list.contract.delete.error"));
                return;
            }

        } else{
            showAlert(Alert.AlertType.ERROR, submitButton.getScene().getWindow(), "Error",
                    getMyBundle().getString("list.contract.delete.error"));
            return;
        }
    }

    public void contractEdit(){
        thread.interruptThread();
        if (contractSelected.getState() != StateEnum.UNAPPROVED){
            showAlert(Alert.AlertType.ERROR, submitButton.getScene().getWindow(), "Error",
                    getMyBundle().getString("list.contract.already.approved"));
            return;
        }

        Stage stage = (Stage)submitButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contractEdit.fxml"),getMyBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContractEditFXController controller = fxmlLoader.<ContractEditFXController>getController();
        controller.initialize(user,contractSelected,this.controller);
        stage.setTitle("FERSA -- CONTRACT EDIT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void userPanel(){
        thread.interruptThread();
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

    public void changeToEnglishLanguage() throws SQLException {
        Stage stage = (Stage)enButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contractPanel.fxml"),getMyEnglishBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContractPanelFXController controller = fxmlLoader.<ContractPanelFXController>getController();
        controller.initialize(this.user);
        stage.setTitle("FERSA -- CONTRACT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1090, 600));
        }
        stage.show();
    }

    public void changeToItalianLanguage() throws SQLException {
        Stage stage = (Stage)itButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contractPanel.fxml"),getMyItalianBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContractPanelFXController controller = fxmlLoader.<ContractPanelFXController>getController();
        controller.initialize(this.user);
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


}
