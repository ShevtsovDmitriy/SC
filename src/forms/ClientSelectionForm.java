package forms;

import controllers.OrderCreationController;
import tables.ClientsTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ClientSelectionForm extends JFrame{
    private JButton selectButton;
    private JButton deleteButton;
    private JTable clientsTable;
    private JPanel clientsPannel;

    public ClientSelectionForm() throws SQLException {
        setSize(400, 600);
        setContentPane(clientsPannel);
        clientsTable.setModel(new ClientsTableModel());
        selectButton.addActionListener(new SelectClientButtonListener());
    }

    private void selectClient(){
        int rowIndex = clientsTable.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < clientsTable.getRowCount()){
            ClientsTableModel clientsTableModel = (ClientsTableModel) clientsTable.getModel();
            OrderCreationController.getController().setClient(clientsTableModel.getClient(rowIndex));
            dispose();
        }

    }

    private class SelectClientButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            selectClient();
        }
    }


}
