package forms;

import controllers.OrderCreationController;
import db.DictionaryHelper;
import db.ServiceCenter;
import entities.dictionary.Defect;
import entities.dictionary.DeviceType;
import entities.dictionary.EquipmentPart;
import entities.dictionary.Manufacturer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class OrderCreationForm extends JFrame {

    private JTextField clientNameTextField;
    private JTextField clientPhoneTextField;
    private JTextField clientUrlTextField;
    private JPanel pannel1;
    private JButton findInDictionaryButton;
    private JComboBox deviceTypeCombobox;
    private JLabel label1;
    private JComboBox manufacturerComboBox;
    private JTextField modelTextField;
    private JComboBox deviceComboBox;
    private JTextArea defectsTextArea;
    private JComboBox defectComboBox;
    private JPanel tabsContainerPannel;
    private JTabbedPane tabbedPane1;
    private JTable statusesTable;
    private JPanel statusesPannel;
    private JPanel equipmentsPannel;
    private JTextArea equipmentPartsTextArea;
    private JButton addEquipmentButton;
    private JButton deleteEquipmentButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton okButton;
    private JButton button5;


    public OrderCreationForm() throws HeadlessException {
        setContentPane(pannel1);
        setSize(1200, 700);
        OrderCreationController.getController().clearAll();

        try {
            prepareComoBoxes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        findInDictionaryButton.addActionListener(new MyButtonListener());
        addEquipmentButton.addActionListener(new AddEquipmentButtonListener());
        deleteEquipmentButton.addActionListener(new DeleteEquipmentButtonListener());
        okButton.addActionListener(new OkButtonListener());

    }

    private void prepareComoBoxes() throws SQLException {
        addAllDeviceTypes();
        addAllManufacturers();
        addAllDefects();
        deviceTypeCombobox.setSelectedIndex(-1);
        manufacturerComboBox.setSelectedIndex(-1);
        defectComboBox.setSelectedIndex(-1);
        defectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defectsTextArea.append(((JComboBox) e.getSource()).getSelectedItem().toString());
                defectsTextArea.append("\n");

            }
        });
    }

    @SuppressWarnings("unchecked")
    private void addAllDeviceTypes() throws SQLException {
        for (DeviceType deviceType : DictionaryHelper.getInstance().getDeviceTypes()) {
            deviceTypeCombobox.addItem(deviceType.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private void addAllManufacturers() throws SQLException {
        for (Manufacturer manufacturer : DictionaryHelper.getInstance().getManufacturers()) {
            manufacturerComboBox.addItem(manufacturer.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private void addAllDefects() throws SQLException {
        for (Defect defect : DictionaryHelper.getInstance().getDefects()) {
            defectComboBox.addItem(defect.getName());
        }
    }

    private void fillEquipmentParts() throws SQLException {
        List<EquipmentPart> equipmentParts = DictionaryHelper.getInstance().getEquipmentParts(OrderCreationController.getController().getEquipments());
        equipmentParts.forEach((e) -> {
            equipmentPartsTextArea.append(e.getName());
            equipmentPartsTextArea.append("\n");
        });
    }

    private boolean checkFields(){
        Color red = new Color(255, 0, 40);
        boolean flag = true;
        if (clientNameTextField.getText().isEmpty()){
            clientNameTextField.setBackground(red);
            flag = false;
        }
        if (deviceTypeCombobox.getSelectedIndex() == -1){
            deviceTypeCombobox.setBackground(red);
            flag = false;
        }
        if (manufacturerComboBox.getSelectedIndex() == -1){
            manufacturerComboBox.setBackground(red);
            flag = false;
        }

        return flag;
    }

    private void saveChanges() throws SQLException {
        if (!checkFields()){
            return;
        }
        ServiceCenter sc = new ServiceCenter();
        int client = sc.createClient(clientNameTextField.getText(), clientPhoneTextField.getText(), clientUrlTextField.getText(), "");

        int device = sc.createDevice(DictionaryHelper.getInstance().getDeviceType(deviceTypeCombobox.getSelectedIndex()),
                DictionaryHelper.getInstance().getManufacturer(manufacturerComboBox.getSelectedIndex()),
                modelTextField.getText(),
                "",
                ""
                );

        int orderId = sc.createOrder(client,
                device,
                DictionaryHelper.getInstance().getDefects(defectsTextArea.getText()),
                OrderCreationController.getController().getEquipments()
                );

        dispose();

    }

    /* Listeners */
    private class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private class AddEquipmentButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AddEquipmentsForm addEquipmentsForm = new AddEquipmentsForm();
                addEquipmentsForm.setVisible(true);
                addEquipmentsForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            fillEquipmentParts();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }

    private class DeleteEquipmentButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText(OrderCreationController.getController().getEquipments().toString());

        }
    }

    private class OkButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveChanges();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
