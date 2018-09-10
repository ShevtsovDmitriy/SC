package forms;

import db.DictionaryHelper;
import entities.dictionary.DeviceType;
import entities.dictionary.Manufacturer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class OrderCreationForm extends JFrame {

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel pannel1;
    private JButton button1;
    private JComboBox deviceTypeCombobox;
    private JLabel label1;
    private JComboBox manufacturerComboBox;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JTextArea textArea1;
    private JComboBox comboBox2;


    public OrderCreationForm() throws HeadlessException {
        setContentPane(pannel1);
        setSize(1200, 700);

        try {
            addAllDeviceTypes();
            addAllManufacturers();
            deviceTypeCombobox.setSelectedIndex(-1);
            manufacturerComboBox.setSelectedIndex(-1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        button1.addActionListener(new MyButtonListener());

    }

    @SuppressWarnings("unchecked")
    private void addAllDeviceTypes() throws SQLException {
        deviceTypeCombobox.addItem("Добавить новый тип");
        for (DeviceType deviceType : DictionaryHelper.getInstance().getDeviceTypes()) {
            deviceTypeCombobox.addItem(deviceType.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private void addAllManufacturers() throws SQLException {
        manufacturerComboBox.addItem("Добавить нового");
        for (Manufacturer manufacturer : DictionaryHelper.getInstance().getManufacturers()) {
            manufacturerComboBox.addItem(manufacturer.getName());
        }
    }

    private class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            label1.setText(Integer.toString(deviceTypeCombobox.getSelectedIndex()));
        }
    }


}
