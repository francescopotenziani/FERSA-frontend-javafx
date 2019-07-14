package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ContractBean;
import it.uniroma2.dicii.ispw.fersa.Bean.RoomBean;
import it.uniroma2.dicii.ispw.fersa.Entity.StateEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.getMyBundle;

public class DataListRoomContractCellFXController extends DataListCell {
    @FXML
    VBox vBox;
    @FXML
    Label roomNumberLabel, addressLabel, renterLabel, startDateLabel, endDateLabel, stateLabel;


    public DataListRoomContractCellFXController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/listContractCellRoom.fxml"),getMyBundle());
        fxmlLoader.setController(this);
        try {
            vBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(ContractBean contractBean) {
        RoomBean room = (RoomBean) contractBean.getRentable();
        roomNumberLabel.setText(getMyBundle().getString("room.number") + ": "  + room.getRoomNumber().toString());
        addressLabel.setText(room.getApartment().getAddress());
        renterLabel.setText(contractBean.getRenter().getUsername());
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

    public VBox getBox() {
        return vBox;
    }
}
