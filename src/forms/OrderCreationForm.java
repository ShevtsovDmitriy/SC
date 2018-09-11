package forms;

import controllers.OrderCreationController;
import db.DictionaryHelper;
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


    /* Listeners */
    private class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                label1.setText(DictionaryHelper.getInstance().getDefects(defectsTextArea.getText()).iterator().next().getName() + " " +
                        DictionaryHelper.getInstance().getDefects(defectsTextArea.getText()).size());
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

}
