package it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import it.uniroma2.dicii.ispw.fersa.Bean.BedBean;
import javafx.scene.control.ListCell;

public class CustomListViewRoom extends ListCell<BedBean> {

    private ApartmentBean apt;

    public  CustomListViewRoom(ApartmentBean a){
        this.apt = a;
    }

    @Override
    public void updateItem(BedBean bedBean, boolean empty)
    {
        super.updateItem(bedBean,empty);
        if (empty || bedBean == null) {
            setGraphic(null);
        } else{
            DataListRoomCellFXController dataListRoomCellFXController = new DataListRoomCellFXController(bedBean);
            dataListRoomCellFXController.setInfo(bedBean,apt);

            setGraphic(dataListRoomCellFXController.getBox());
        }
    }
}
