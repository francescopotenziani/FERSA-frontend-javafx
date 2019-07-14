package it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import it.uniroma2.dicii.ispw.fersa.Bean.BedBean;
import it.uniroma2.dicii.ispw.fersa.Bean.UserBean;
import it.uniroma2.dicii.ispw.fersa.Controller.ApartmentController;
import it.uniroma2.dicii.ispw.fersa.Controller.ContractUpdateController;
import it.uniroma2.dicii.ispw.fersa.Share.AlertHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;

import static it.uniroma2.dicii.ispw.fersa.Share.AlertHelper.showAlert;
import static it.uniroma2.dicii.ispw.fersa.Share.AlertHelper.showConfirmationAlert;
import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.*;

public class ApartmentEditFXController {
    public Button submitButton, backButton, enButton, itButton;
    private ApartmentBean apartmentBeanSelected;
    private UserBean user;
    private ApartmentController controller;
    ObservableList<BedBean> observableList = FXCollections.observableArrayList();
    private BedBean bedBeanSelected;
    @FXML private Button fullScreenButton, deleteButton, addButton;
    @FXML private VBox vBox1;
    @FXML ListView lvroom;
    @FXML private ComboBox<String> heatingTypeBox,utilityBillsType,rentTypeBox;
    @FXML private CheckBox airConditionBox, tvBox, roomSpaceBox, wifiBox, furnishedBox, washingMachineBox, dryerBox,
            dishwasherBox;
    @FXML private TextField rentalFeeTextField, condominiumFeeTextField, newBedFeeTextField;


    public void initialize(UserBean userSession, ApartmentController parentController, ApartmentBean apt) {
        user = userSession;
        this.controller = parentController;
        apartmentBeanSelected = apt;
//        controller.findRoom(apartmentBeanSelected);
//        controller.findBeds(apartmentBeanSelected);
        deleteButton.setDisable(true);
        addButton.setDisable(true);
        observableList.addAll(apartmentBeanSelected.getBeds());
        lvroom.setItems(observableList);
        lvroom.setCellFactory(listView -> new CustomListViewRoom(apartmentBeanSelected));

        this.rentTypeBox.getItems().addAll(
                getMyBundle().getString("apartmentEdit.rentType.allApartmentRent"),
                getMyBundle().getString("apartmentEdit.rentType.roomRent"),
                getMyBundle().getString("apartmentEdit.rentType.notSpecified")
        );

        this.heatingTypeBox.getItems().addAll(
                getMyBundle().getString("apartmentEdit.heatingType.Shared"),
                getMyBundle().getString("apartmentEdit.heatingType.Indipendent")
        );
        this.utilityBillsType.getItems().addAll(
                getMyBundle().getString("apartmentEdit.condominiumFee.Included"),
                getMyBundle().getString("apartmentEdit.condominiumFee.NotIncluded")
        );
        this.airConditionBox.setSelected(apartmentBeanSelected.getAirConditioning());
        this.tvBox.setSelected(apartmentBeanSelected.getTv());
        this.roomSpaceBox.setSelected(apartmentBeanSelected.getShared_room_space());
        this.wifiBox.setSelected(apartmentBeanSelected.getWifi());
        this.furnishedBox.setSelected(apartmentBeanSelected.getFurnished());
        this.washingMachineBox.setSelected(apartmentBeanSelected.getWashingMachine());
        this.dryerBox.setSelected(apartmentBeanSelected.getDryer());
        this.dishwasherBox.setSelected(apartmentBeanSelected.getDishWasher());
        this.rentalFeeTextField.setText(apartmentBeanSelected.getRentalFee().toString());
        this.condominiumFeeTextField.setText(apartmentBeanSelected.getCondominiumFee().toString());
        this.heatingTypeBox.setValue(getMyBundle().getString(apartmentBeanSelected.getHeating_type()));
        this.utilityBillsType.setValue(getMyBundle().getString(apartmentBeanSelected.getUtilityBillsType()));
        this.rentTypeBox.setValue(getMyBundle().getString(apartmentBeanSelected.getRentType()));

        rentTypeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (getKey(newValue).equals("apartmentEdit.rentType.allApartmentRent")){
                    vBox1.setDisable(true);
                    rentalFeeTextField.setDisable(false);
                } else if(getKey(newValue).equals("apartmentEdit.rentType.roomRent")){
                    vBox1.setDisable(false);
                    rentalFeeTextField.setDisable(true);
                } else{
                    vBox1.setDisable(false);
                    rentalFeeTextField.setDisable(false);
                }
            }
        });

        lvroom.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BedBean>() {
            @Override
            public void changed(ObservableValue observable, BedBean oldValue, BedBean newValue) {
                bedBeanSelected = newValue;
                deleteButton.setDisable(false);
                addButton.setDisable(false);
            }
        });

        newBedFeeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (!newValue.matches("\\d*") || newValue=="") {
                        newBedFeeTextField.setText(newValue.replaceAll("[^\\d]", " "));
                    }
                } catch (NumberFormatException e){
                    newBedFeeTextField.setText(newValue.replaceAll("[^\\d]", " "));
                }
            }
        });

    }

    public void updateApartment(){
        try{
            Integer.parseInt(this.condominiumFeeTextField.getText());
            Integer.parseInt(this.rentalFeeTextField.getText());
        } catch (java.lang.NumberFormatException e){
            AlertHelper.showAlert(Alert.AlertType.ERROR,submitButton.getScene().getWindow(),"Error",
                    getMyBundle().getString("submit.error"));
            return;
        }

        this.apartmentBeanSelected.setAirConditioning(this.airConditionBox.isSelected());
        this.apartmentBeanSelected.setTv(this.tvBox.isSelected());
        this.apartmentBeanSelected.setShared_room_space(this.roomSpaceBox.isSelected());
        this.apartmentBeanSelected.setWifi(this.wifiBox.isSelected());
        this.apartmentBeanSelected.setFurnished(this.furnishedBox.isSelected());
        this.apartmentBeanSelected.setWashingMachine(this.washingMachineBox.isSelected());
        this.apartmentBeanSelected.setDryer(this.dryerBox.isSelected());
        this.apartmentBeanSelected.setDishWasher(this.dishwasherBox.isSelected());
        this.apartmentBeanSelected.setRentalFee(Integer.parseInt(this.rentalFeeTextField.getText()));
        this.apartmentBeanSelected.setCondominiumFee(Integer.parseInt(this.condominiumFeeTextField.getText()));
        this.apartmentBeanSelected.setHeating_type(getKey(this.heatingTypeBox.getValue()));
        this.apartmentBeanSelected.setUtilityBills_Type(getKey(this.utilityBillsType.getValue()));
        this.apartmentBeanSelected.setRentType(getKey(this.rentTypeBox.getValue()));


        this.controller.updateApartment(apartmentBeanSelected);
        Window owner = fullScreenButton.getScene().getWindow();
        showAlert(Alert.AlertType.INFORMATION, owner, "Success",  getMyBundle().getString("update.success"));
        apartmentPanel();
    }

    public void setFullScreen(){
        Stage stage = (Stage)fullScreenButton.getScene().getWindow();
        stage.setFullScreen(true);
    }

    public void apartmentPanel() {
        Stage stage = (Stage)backButton.getScene().getWindow();
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

    public void deleteBed(){
        ContractUpdateController contractController = new ContractUpdateController();
        if(bedBeanSelected != null) {
            if (bedBeanSelected.getRoom().getBeds().size() <= 1){
                showAlert(Alert.AlertType.ERROR, deleteButton.getScene().getWindow(), "Error",
                        getMyBundle().getString("list.room.invalid.selection"));
            } else if ( contractController.checkForContract(bedBeanSelected.getBedID())) {
                showAlert(Alert.AlertType.ERROR, deleteButton.getScene().getWindow(), "Error",
                        getMyBundle().getString("list.room.bed.with.contract"));
            }

            else {
                if (!showConfirmationAlert(getMyBundle().getString("delete.bed.confirmation")
                        ,deleteButton.getScene().getWindow())){
                    return;
                }
                bedBeanSelected.getRoom().getBeds().remove(bedBeanSelected);
                ArrayList<BedBean> bedList = bedBeanSelected.getRoom().getBeds();
                for (int i=0; i<bedList.size(); i++){
                    bedList.get(i).setBedNumber(i+1);
                }
                controller.updateBedsNumber(bedList);
                controller.deleteBed(bedBeanSelected);

                controller.updateBedsAndRoomsApartment(apartmentBeanSelected);
                observableList.clear();
                observableList.addAll(apartmentBeanSelected.getBeds());
            }

            lvroom.setItems(observableList);
            lvroom.setCellFactory(listView -> new CustomListViewRoom(apartmentBeanSelected));
            deleteButton.setDisable(true);
            addButton.setDisable(true);
        }
    }

    public void addBed(){
        if(newBedFeeTextField.getText() != null & newBedFeeTextField.getText() != "" ){
            if (!showConfirmationAlert(getMyBundle().getString("add.bed.confirmation")
                    ,addButton.getScene().getWindow())){
                return;
            }

            BedBean newBed = new BedBean();
            newBed.setRoom(bedBeanSelected.getRoom());
            newBed.setBedNumber(bedBeanSelected.getRoom().getBeds().size()+1);
            newBed.setBedFee(Integer.parseInt(newBedFeeTextField.getText()));
            controller.insertBed(newBed);
            controller.findRoom(apartmentBeanSelected);
            controller.findBeds(apartmentBeanSelected);
            observableList.clear();
            observableList.addAll(apartmentBeanSelected.getBeds());
            lvroom.setItems(observableList);
            lvroom.setCellFactory(listView -> new CustomListViewRoom(apartmentBeanSelected));
            lvroom.setItems(observableList);
            lvroom.setCellFactory(listView -> new CustomListViewRoom(apartmentBeanSelected));
            addButton.setDisable(true);
            deleteButton.setDisable(true);
        } else{
            showAlert(Alert.AlertType.ERROR, deleteButton.getScene().getWindow(), "Error",
                    getMyBundle().getString("add.bed.error"));
        }
    }

    public void changeToEnglishLanguage(){
        Stage stage = (Stage)enButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentEdit.fxml"),getMyEnglishBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApartmentEditFXController controller = fxmlLoader.<ApartmentEditFXController>getController();
        controller.initialize(this.user, this.controller, this.apartmentBeanSelected);
        stage.setTitle("FERSA -- APARTMENT EDIT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1300, 800));
        }
        stage.show();
    }

    public void changeToItalianLanguage(){
        Stage stage = (Stage)itButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apartmentEdit.fxml"),getMyItalianBundle());
        Parent root = null;
        try{
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApartmentEditFXController controller = fxmlLoader.<ApartmentEditFXController>getController();
        controller.initialize(this.user, this.controller, this.apartmentBeanSelected);
        stage.setTitle("FERSA -- APARTMENT EDIT PANEL");
        if (stage.isFullScreen()){
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        }else{
            stage.setScene(new Scene(root, 1300, 800));
        }
        stage.show();
    }
}
