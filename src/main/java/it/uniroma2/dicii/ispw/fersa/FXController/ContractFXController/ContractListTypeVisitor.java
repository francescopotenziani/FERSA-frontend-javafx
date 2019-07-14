package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.*;

public class ContractListTypeVisitor implements Visitor, it.uniroma2.dicii.ispw.fersa.Visitor.Visitor {

    private DataListCell dataListCell;

    public void  visit(ApartmentBean apartmentBean){
        this.dataListCell = new DataListApartmentContractCellFXController();
    }

    public void  visit(RoomBean roomBean){
        this.dataListCell = new DataListRoomContractCellFXController();
    }
    public void visit(BedBean apartmentBean){
        this.dataListCell = new DataListBedContractCellFXController();
    }
    public void visit(RentableBean rentableBean){
        this.dataListCell = new DataListCell();
    }

    public DataListCell getDataListCell() {
        return dataListCell;
    }
}
