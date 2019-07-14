package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.*;
import it.uniroma2.dicii.ispw.fersa.Entity.StateEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.getMyBundle;

public class DataListApartmentContractCellFXController extends DataListCell {
    @FXML
    VBox vBox;
    @FXML
    Label addressLabel,renterLabel,startDateLabel,endDateLabel, stateLabel;

    public DataListApartmentContractCellFXController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/listContractCellApartment.fxml"),getMyBundle());
        fxmlLoader.setController(this);
        try {
            vBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(ContractBean contract) {
        ApartmentBean apartmentBean = (ApartmentBean) contract.getRentable();
        addressLabel.setText(apartmentBean.getAddress());
        renterLabel.setText(contract.getRenter().getUsername());
        startDateLabel.setText(contract.getStart_date().toString());
        endDateLabel.setText(contract.getEnd_date().toString());
        if (contract.getState().equals(StateEnum.UNAPPROVED)){
            stateLabel.setText(getMyBundle().getString("contract.state") + " " +getMyBundle().getString("contract.unapproved"));
        } else if (contract.getState().equals(StateEnum.APPROVED)){
            stateLabel.setText(getMyBundle().getString("contract.state") + " " + getMyBundle().getString("contract.approved"));
        } else{
            stateLabel.setText(getMyBundle().getString("contract.state") + " " + getMyBundle().getString("contract.waiting"));
        }
    }

    public VBox getBox() {
        return vBox;
    }

}
