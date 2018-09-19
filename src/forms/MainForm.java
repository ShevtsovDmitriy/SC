package forms;

import tables.OrderTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainForm extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;
    private JTable table1;

    public MainForm() throws HeadlessException, ClassNotFoundException, SQLException {
        setContentPane(panel1);
        setVisible(true);
        setSize(640, 480);
        //setExtendedState(MAXIMIZED_BOTH);
        button1.addActionListener(new MyButtonListener());

        TableModel model = new OrderTableModel();
        table1.setModel(model);
        table1.addMouseListener(new TableMouseListener());

        //getContentPane().add(new JScrollPane(table1));

        //setPreferredSize(new Dimension(260, 220));
        //pack();
        //setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    private class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            OrderCreationForm orderCreationForm = null;
            try {
                orderCreationForm = new OrderCreationForm(-1);
                orderCreationForm.setVisible(true);
                orderCreationForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            ((OrderTableModel)table1.getModel()).refresh();
                            table1.revalidate();
                            table1.updateUI();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private class TableMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2){
                try {
                    OrderCreationForm orderCreationForm = new OrderCreationForm((Integer) table1.getValueAt(table1.getSelectedRow(), 0));
                    orderCreationForm.setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }


        }
    }

    public static void main(String[] args) {
        try {
            new MainForm();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }


}
