package tables;

import db.ServiceCenter;
import entities.Order;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OrderTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Order> orders;
    ServiceCenter sc = new ServiceCenter();

    public OrderTableModel() throws SQLException {
        this.orders = sc.getAllOrders();
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return int.class;
            case 1:
                return Date.class;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return String.class;
        }
        return String.class;
    }

    public int getColumnCount() {
        return 7;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Дата";
            case 2:
                return "Клиент";
            case 3:
                return "Телефон";
            case 4:
                return "Производитель";
            case 5:
                return "Устройство";
            case 6:
                return "Статус";
        }
        return "";
    }

    public int getRowCount() {
        return orders.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Order order = sc.getAllOrders().get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return order.getId();
                case 1:
                    return order.getDate();
                case 2:
                    return order.getClient().getFio();
                case 3:
                    return order.getClient().getPhone();
                case 4:
                    return order.getDevice().getManufacturer().getName();
                case 5:
                    return order.getDevice().getModel();
                case 6:
                    return order.getLastStatus().getName();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

    public void refresh() throws SQLException {
        this.orders = sc.getAllOrders();
    }

}