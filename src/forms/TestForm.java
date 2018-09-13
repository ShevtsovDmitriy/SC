package forms;

import tables.OrderTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class TestForm extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;
    private JTable table1;

    public TestForm() throws HeadlessException, ClassNotFoundException, SQLException {
        setContentPane(panel1);
        setVisible(true);
        setSize(640, 480);
        //setExtendedState(MAXIMIZED_BOTH);
        button1.addActionListener(new MyButtonListener());

        TableModel model = new OrderTableModel();
        table1.setModel(model);


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
            OrderCreationForm orderCreationForm = new OrderCreationForm();
            orderCreationForm.setVisible(true);
            orderCreationForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        ((OrderTableModel)table1.getModel()).refresh();
                        table1.revalidate();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
            });
        }
    }

    public static void main(String[] args) {
        try {
            new TestForm();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }


}
