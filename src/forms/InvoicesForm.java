package forms;

import controllers.StoreController;
import entities.Invoice;
import tables.InvoiceSparesTableModel;
import tables.InvoicesTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
            Invoice invoice = ((InvoicesTableModel)invoicesTable.getModel()).getInvoices().get(invoicesTable.getSelectedRow());
            InvoiceSparesTableModel invoiceSparesTableModel = new InvoiceSparesTableModel(invoice.getSpares());
            invoiceDetailsTable.setModel(invoiceSparesTableModel);
            invoiceDetailsTable.revalidate();
        }
    }

    private class AddSpareButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}
