package tables;

import db.DictionaryHelper;
import entities.Order;
import entities.OrderStatus;
import entities.dictionary.Status;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SatusesTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    JComboBox editor = new JComboBox();
    private List<OrderStatus> statuses;
    private Order order;

    public SatusesTableCellEditor(Order order) {
        this.order = order;
        if (order != null && order.getStatuses() != null) {
            statuses = order.getStatuses();
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (column != 1)
            return null;
        try {
            OrderStatus orderStatus = statuses.get(row);
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel();

                for(Status status: DictionaryHelper.getInstance().getStatuses()){
                    cbModel.addElement(status.getName());
                }
            editor.setModel(cbModel);
            editor.setSelectedIndex(DictionaryHelper.getInstance().getStatusIndex(orderStatus.getStatus()));
            return editor;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Object getCellEditorValue() {
        return editor.getSelectedItem();
    }


}
