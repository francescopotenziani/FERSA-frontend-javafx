package it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import it.uniroma2.dicii.ispw.fersa.Bean.BedBean;
import it.uniroma2.dicii.ispw.fersa.Bean.RoomBean;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import static it.uniroma2.dicii.ispw.fersa.Share.BundleSingleton.getMyBundle;

public class DataListRoomCellFXController {
    @FXML
    private HBox hbox;
    @FXML
    private Label roomType, room, bed;
    @FXML
    private TextField roomFee;
    @FXML
    private TextField bedFee;


    public DataListRoomCellFXController(BedBean bedBean){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/listRoomCellItem.fxml"),getMyBundle());
        fxmlLoader.setController(this);
        try {
            hbox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bedFee.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try {
                    if (!newValue.matches("\\d*") || newValue=="") {
                        bedFee.setText(newValue.replaceAll("[^\\d]", ""));
                    }else{
                        bedBean.setBedFee(Integer.parseInt(newValue));
                    }
                } catch (java.lang.NumberFormatException e){
                    bedFee.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        roomFee.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               try {
                   if (!newValue.matches("\\d*") || newValue=="") {
                       roomFee.setText(newValue.replaceAll("[^\\d]", " "));
                   }else{
                       bedBean.getRoom().setRoomFee(Integer.parseInt(newValue));
                   }
               } catch (java.lang.NumberFormatException e){
                   roomFee.setText(newValue.replaceAll("[^\\d]", " "));
               }
            }
        });
    }

    public void setInfo(BedBean bedBean, ApartmentBean apt){

        if (bedBean.getRoom().getBeds().size() > 1){
            if(bedBean.getBedFee() != null){
                bedFee.setText(bedBean.getBedFee().toString());
            }
            roomFee.setDisable(true);
            room.setText(bedBean.getRoom().getRoomNumber().toString());
            bed.setText(bedBean.getBedNumber().toString());
            roomType.setText(getMyBundle().getString("room.type.multiple"));
        } else{
            if (bedBean.getRoom().getRoomFee() != null){
                roomFee.setText(bedBean.getRoom().getRoomFee().toString());
            }
            room.setText(bedBean.getRoom().getRoomNumber().toString());
            bed.setText(bedBean.getBedNumber().toString());
            bedFee.setDisable(true);
            roomType.setText(getMyBundle().getString("room.type.single"));
        }
    }

    public HBox getBox(){
        return hbox;
    }

    public Integer getRoomNumber(ApartmentBean a, BedBean b){
        Integer count = 0;
        for(RoomBean r: a.getRooms()){
            count++;
            if (r.equals(b.getRoom())){
                return count;
            }
        }
        return count;
    }

    public Integer getBedNumber(ApartmentBean a, BedBean b){
        Integer count = 0;
        for (BedBean bed: b.getRoom().getBeds()){
            count++;
            if (bed.equals(b)){
               return count;
            }
        }
        return count;
    }
}