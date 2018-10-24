package tables;

import controllers.StoreController;
import entities.Invoice;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoicesTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Invoice> invoices;

    public InvoicesTableModel() throws SQLException {
        this.invoices = StoreController.getController().getInvoices().stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).collect(Collectors.toList());
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Номер";
            case 1:
                return "Дата";
            case 2:
                return "Тип";
        }
        return "";
    }

    public int getRowCount() {
        return invoices.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoice = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return invoice.getId();
            case 1:{
                Locale local = new Locale("ru","RU");
                DateFormat df = DateFormat.getDateTimeInstance (DateFormat.DEFAULT,DateFormat.DEFAULT,local);
                return df.format(invoice.getDate());
            }
            case 2:
                return invoice.getType().getName();
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
        this.invoices = StoreController.getController().getInvoices();
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }
}