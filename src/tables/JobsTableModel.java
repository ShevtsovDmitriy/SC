package tables;

import entities.dictionary.Job;
import utils.Utils;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobsTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    List<Job> jobs;

    public JobsTableModel(List<Job> jobs) throws SQLException {
        this.jobs = jobs;
    }

    @Override
    public int getRowCount() {
        return jobs.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Название";
            case 1:
                return "Цена";
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
        Job job = jobs.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return job.getName();
            case 1: {
                return Utils.formatDouble(job.getPrice());
            }
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

    public Job getJob(int index) {
        return jobs.get(index);
    }


}