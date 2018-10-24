package tables;

import entities.dictionary.SparePart;
import utils.Utils;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SparePartsTableModel extends AbstractTableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    List<SparePart> spareParts;

    public SparePartsTableModel(List<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    @Override
    public int getRowCount() {
        return spareParts.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Название";
            case 1:
                return "Цена";
            case 2:
                return "Производитель";

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
        SparePart sparePart = spareParts.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sparePart.getName();
            case 1:
                return Utils.formatDouble(sparePart.getDefaultPrice());
            case 2:
                return sparePart.getManufacturer();
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

    public SparePart getSparePart(int index) {
        return spareParts.get(index);
    }

    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    public void addSparePart(List<SparePart> spareParts){
        this.spareParts.addAll(spareParts);
    }

    public void setSparePart(List<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    public void removeSparePart(int rowIndex){
        spareParts.remove(rowIndex);
    }

}
