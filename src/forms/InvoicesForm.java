package forms;

import controllers.InvoiceController;
import controllers.StoreController;
import exceptions.InvoiceAlreadyOpenedException;
import tables.InvoiceSparesTableModel;
import tables.InvoicesTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class InvoicesForm extends JFrame {
    private JTable invoicesTable;
    private JButton addInvoiceButton;
    private JButton removeInvoiceButton;
    private JPanel invoicesPanel;
    private JTable invoiceDetailsTable;
    private JButton addSpareButton;
    private JButton removeSpareButton;

    public InvoicesForm() throws HeadlessException, SQLException {
        setContentPane(invoicesPanel);
        setSize(750, 600);
        addInvoiceButton.addActionListener(new AddInvoiceButtonListener());
        printInvoices();
        invoicesTable.addMouseListener(new InvoiceTableMouseAdapter());
        addSpareButton.addActionListener(new AddSpareButtonListener());
    }

    private void printInvoices() throws SQLException {
        invoicesTable.setModel(new InvoicesTableModel());
        invoicesTable.revalidate();
        invoicesTable.getSelectionModel().setSelectionInterval(0, 0);
    }

    private void addInvoice() throws SQLException {
        StoreController.getController().createInvoice();
        printInvoices();
    }

    private class AddInvoiceButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                addInvoice();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class InvoiceTableMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                InvoiceController.getInstance().saveInvoice();
                InvoiceController.getInstance().setInvoice(((InvoicesTableModel)invoicesTable.getModel()).getInvoices().get(invoicesTable.getSelectedRow()));
                InvoiceSparesTableModel invoiceSparesTableModel = new InvoiceSparesTableModel(InvoiceController.getInstance().getInvoice().getSparesList());
                invoiceDetailsTable.setModel(invoiceSparesTableModel);
                invoiceDetailsTable.revalidate();
            } catch (SQLException | InvoiceAlreadyOpenedException e1) {
            e1.printStackTrace();
        }
        }
    }

    private class AddSpareButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SpareSelectionForm spareSelectionForm = new SpareSelectionForm();
                spareSelectionForm.setTitle("Выберите деталь для добавления");
                spareSelectionForm.setVisible(true);
                spareSelectionForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        InvoiceSparesTableModel invoiceSparesTableModel = new InvoiceSparesTableModel(InvoiceController.getInstance().getInvoice().getSparesList());
                        invoiceDetailsTable.setModel(invoiceSparesTableModel);
                        invoiceDetailsTable.revalidate();
                    }
                });
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }

}
