package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ContractBean;
import javafx.scene.control.ListCell;

public class CustomListViewContract extends ListCell<ContractBean> {
    @Override
    public void updateItem(ContractBean contractBean, boolean empty)
    {
        super.updateItem(contractBean,empty);
        if (contractBean != null)
        {
            ContractListTypeVisitor visitor = new ContractListTypeVisitor();
            contractBean.getRentable().accept(visitor);
            DataListCell dataListCell = visitor.getDataListCell();
            dataListCell.setInfo(contractBean);
            setGraphic(dataListCell.getBox());
        }
    }
}
