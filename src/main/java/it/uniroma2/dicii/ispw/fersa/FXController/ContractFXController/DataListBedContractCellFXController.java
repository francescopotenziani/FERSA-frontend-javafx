package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.BedBean;
import it.uniroma2.dicii.ispw.fersa.Bean.ContractBean;
import it.uniroma2.dicii.ispw.fersa.Entity.StateEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.getMyBundle;

public class DataListBedContractCellFXController extends DataListCell {
    @FXML
    VBox vBox;
    @FXML
    Label bedNumberLabel, roomNumberLabel,renterLabel,addressLabel,startDateLabel,endDateLabel, stateLabel;
    public DataListBedContractCellFXController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/listContractCellBed.fxml"),getMyBundle());
        fxmlLoader.setController(this);
        try {
            vBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void setInfo(ContractBean contractBean) {
        BedBean bedBean = (BedBean) contractBean.getRentable();
        bedNumberLabel.setText(getMyBundle().getString("bed.number") + ": " + bedBean.getBedNumber().toString());
        roomNumberLabel.setText(getMyBundle().getString("room.number") + ": " + bedBean.getRoom().getRoomNumber().toString());
        renterLabel.setText(contractBean.getRenter().getUsername());
        addressLabel.setText(bedBean.getRoom().getApartment().getAddress());
        startDateLabel.setText(contractBean.getStart_date().toString());
        endDateLabel.setText(contractBean.getEnd_date().toString());

        if (contractBean.getState().equals(StateEnum.UNAPPROVED)){
            stateLabel.setText(getMyBundle().getString("contract.state") + " " + getMyBundle().getString("contract.unapproved"));
        } else if (contractBean.getState().equals(StateEnum.APPROVED)){
            stateLabel.setText(getMyBundle().getString("contract.state") + " " + getMyBundle().getString("contract.approved"));
        } else{
            stateLabel.setText(getMyBundle().getString("contract.state") + " " + getMyBundle().getString("contract.waiting"));
        }

    }

    @Override
    public VBox getBox() {
        return vBox;
    }
}
