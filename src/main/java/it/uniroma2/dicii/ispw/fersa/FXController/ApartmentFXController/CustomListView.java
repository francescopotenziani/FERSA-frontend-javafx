package it.uniroma2.dicii.ispw.fersa.FXController.ApartmentFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import javafx.scene.control.ListCell;

public class CustomListView extends ListCell<ApartmentBean> {
    @Override
    public void updateItem(ApartmentBean apartmentBean, boolean empty)
    {

        super.updateItem(apartmentBean,empty);
        if(apartmentBean != null)
        {
            DataListCellFXController dataListCellFXController = new DataListCellFXController();
            dataListCellFXController.setInfo(apartmentBean);
            setGraphic(dataListCellFXController.getBox());
        }
    }

}
