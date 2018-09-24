package tables;

import entities.Order;
import entities.OrderJob;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class OrderJobsTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    List<OrderJob> jobs;

    public OrderJobsTableModel(Order order) throws SQLException {
        jobs = order.getJobs();
    }

    @Override
    public int getRowCount() {
        return jobs.size();
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
                return "Цена";
            case 2:
                return "Количество";
            case 3:
                return "Стоимость";
            case 4:
                return "Выполнил";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderJob job = jobs.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return job.getJob().getName();
            case 1:
                return job.getStringPrice();
            case 2:
                return job.getStringQuantity();
            case 3: {
                DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
                otherSymbols.setDecimalSeparator('.');
                NumberFormat nf = new DecimalFormat("#.######", otherSymbols);
                return nf.format(job.getPrice() * job.getQuantity());
            }
            case 4:
                return job.getUser().getName();
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

    public OrderJob getJob(int index) {
        return jobs.get(index);
    }
}
