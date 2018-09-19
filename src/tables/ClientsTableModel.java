package tables;

import db.DaoHelper;
import entities.Client;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientsTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    List<Client> clients;

    public ClientsTableModel() throws SQLException {
        this.clients = DaoHelper.getInstance().CLIENT_DAO.queryForAll();
    }

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Имя";
            case 1:
                return "Телефон";
            case 2:
                return "Ссылка";
            case 3:
                return "Заметки";
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
        Client client = clients.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getFio();
            case 1:
                return client.getPhone();
            case 2:
                return client.getUrl();
            case 3:
                return client.getNote();
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

    public Client getClient(int index) {
        return clients.get(index);
    }
}
