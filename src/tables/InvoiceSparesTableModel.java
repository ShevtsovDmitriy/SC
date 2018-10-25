package tables;

import controllers.InvoiceController;
import controllers.StoreController;
import entities.InvoiceSpare;
import exceptions.InconsistentDataInStoreException;
import utils.Utils;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvoiceSparesTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<InvoiceSpare> spares;

    public InvoiceSparesTableModel(List<InvoiceSpare> spares) {
        this.spares = spares;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getRowCount() {
        return spares.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
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
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceSpare spare = spares.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return spare.getSparePart().getName();
            case 1:
                return Utils.formatDouble(spare.getBuyPrice());
            case 2:
                return Utils.formatDouble(spare.getSalePrice());
            case 3:
                return Utils.formatDouble(spare.getCount());
            case 4:
                return Utils.formatDouble(spare.getCount() * spare.getSalePrice());
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex > 0 && columnIndex < 4){
            return true;
        }
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        InvoiceSpare spare = spares.get(rowIndex);
        if (!value.toString().matches("(([-+])?[0-9]+(\\.[0-9]+)?)+")){
            return;
        }
        boolean isCountChanges = false;
        double oldBuyPrice = spare.getBuyPrice();
        double oldSalePrice = spare.getSalePrice();
        double oldCount = spare.getCount();
        switch (columnIndex) {
            case 1:
                spare.setBuyPrice(Double.parseDouble(value.toString()));
                break;
            case 2:
                spare.setSalePrice(Double.parseDouble(value.toString()));
                break;
            case 3:
                spare.setCount(Double.parseDouble(value.toString()));
                isCountChanges = true;
                break;
        }
        try {
            InvoiceController.getInstance().updateInvoiceSpare(spare);
            StoreController.getController().updateEntityInStock(spare.getSparePart(), oldCount, spare.getCount(), oldBuyPrice, spare.getBuyPrice(), oldSalePrice, spare.getSalePrice(), isCountChanges);
        } catch (SQLException | InconsistentDataInStoreException e) {
            e.printStackTrace();
        }

    }

}