package tables;

import controllers.StoreController;
import entities.StoreEntity;
import utils.Utils;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreTableModel extends AbstractTableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    List<StoreEntity> storeEntities;

    public StoreTableModel(List<StoreEntity> storeEntities) {
        this.storeEntities = storeEntities;
    }

    @Override
    public int getRowCount() {
        return storeEntities.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Название";
            case 1:
                return "Закупка";
            case 2:
                return "Продажа";
            case 3:
                return "Количество";
            case 4:
                return "Стоимость";
            case 5:
                return "Общее количество";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StoreEntity storeEntity = storeEntities.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return storeEntity.getSparePart().getName();
            case 1:
                return Utils.formatDouble(storeEntity.getBuyPrice());
            case 2:
                return Utils.formatDouble(storeEntity.getSalePrice());
            case 3:
                return Utils.formatDouble(storeEntity.getCount());
            case 4:
                return Utils.formatDouble(storeEntity.getCount() * storeEntity.getSalePrice());
            case 5:
                return Utils.formatDouble(StoreController.getController().getEntitiesCount(storeEntity));
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public StoreEntity getEntity(int index) {
        return storeEntities.get(index);
    }

    public List<StoreEntity> getEntities() {
        return storeEntities;
    }

    public void addEntity(List<StoreEntity> jobs){
        this.storeEntities.addAll(jobs);
    }

    public void setEntities(List<StoreEntity> jobs) {
        this.storeEntities = jobs;
    }

    public void removeEntity(int rowIndex){
        storeEntities.remove(rowIndex);
    }

}
