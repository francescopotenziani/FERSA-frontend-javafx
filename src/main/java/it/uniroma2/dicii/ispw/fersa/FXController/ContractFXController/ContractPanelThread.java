package it.uniroma2.dicii.ispw.fersa.FXController.ContractFXController;

import it.uniroma2.dicii.ispw.fersa.Bean.ContractBean;
import it.uniroma2.dicii.ispw.fersa.Controller.ContractUpdateController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class ContractPanelThread implements Runnable {
    ObservableList<ContractBean> observableList;
    ArrayList<ContractBean> contractArrayList;
    ContractUpdateController controller;
    ListView contractList;
    boolean tExit = false;

    ContractPanelThread(ArrayList<ContractBean> contractArrayList, ContractUpdateController controller,
                        ObservableList observableList, ListView contractList){
        this.observableList = observableList;
        this.contractArrayList = contractArrayList;
        this.controller = controller;
        this.contractList = contractList;
    }
    @Override
    public void run() {
        while (!tExit){
            for (ContractBean c: contractArrayList){
                if (controller.checkContract(c)){
                    Platform.runLater(() -> {
                        observableList.clear();
                        observableList.addAll(contractArrayList);
                        contractList.setItems(observableList);
                        contractList.setCellFactory(List -> new CustomListViewContract());
                        return;
                    });
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void interruptThread(){
        this.tExit = true;
    }
}
