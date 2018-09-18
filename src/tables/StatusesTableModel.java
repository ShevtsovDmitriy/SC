package tables;

import entities.Order;
import entities.OrderStatus;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class StatusesTableModel extends AbstractTableModel {

    private Set<TableModelListener> listeners = new HashSet<>();
    private List<OrderStatus> statuses;
    private Order order;

    public StatusesTableModel(Order order) {
        this.order = order;
        if (order != null && order.getStatuses() != null) {
            statuses = order.getStatuses();
        }
    }

    @Override
    public int getRowCount() {
        return statuses.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Дата";
            case 2:
                return "Статус";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderStatus orderStatus = statuses.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getDateTimeInstance (DateFormat.DEFAULT,DateFormat.DEFAULT,local);
                return df.format(orderStatus.getDate());
            }
            case 1: {
                return orderStatus.getStatus().getName();
            }
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }


}
