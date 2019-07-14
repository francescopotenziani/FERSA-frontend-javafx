package it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.getMyBundle;

public class DataListCellFXController {
    @FXML
    HBox hBox;
    @FXML
    Label apartmentID, lessorID, address, insertDate, price;

    public DataListCellFXController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/listCellItem.fxml"),getMyBundle());
        fxmlLoader.setController(this);
        try {
           hBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(ApartmentBean apartmentBean)
    {
        apartmentID.setText(String.valueOf(apartmentBean.getId()));
        lessorID.setText(apartmentBean.getLessor().getUsername());
        address.setText(apartmentBean.getAddress());
        insertDate.setText(apartmentBean.getInsert_date().toString());
        price.setText(String.valueOf(apartmentBean.getRentalFee()));
    }

    public HBox getBox()
    {
        return hBox;
    }
}
