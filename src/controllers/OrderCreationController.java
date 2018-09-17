package controllers;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationController {
    private static OrderCreationController ourInstance = new OrderCreationController();

    public static OrderCreationController getController() {
        return ourInstance;
    }

    private OrderCreationController() {
    }

    public void clearAll(){
        ourInstance = new OrderCreationController();
    }

    private List<Integer> equipments;

    public List<Integer> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Integer> equipments) {
        this.equipments = equipments;
    }

    public void addEquipment(int equipment){
        if(equipments == null){
            equipments = new ArrayList<>(5);
        }
        equipments.add(equipment);
    }

    public void removeEquipment(int equipment){
        equipments.removeIf(e -> e.equals(Integer.valueOf(equipment)));
    }


}
