package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ApartmentBean;
import it.uniroma2.dicii.ispw.fersa.Bean.BedBean;
import it.uniroma2.dicii.ispw.fersa.Bean.RentableBean;
import it.uniroma2.dicii.ispw.fersa.Bean.RoomBean;

public interface Visitor {
     void visit(ApartmentBean apartmentBean);
     void visit(RoomBean roomBean);
     void visit(BedBean bedBean);
     void visit(RentableBean rentableBean);
}
