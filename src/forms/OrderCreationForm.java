package forms;

import controllers.OrderCreationController;
import controllers.UserSessionController;
import db.DictionaryHelper;
import db.ServiceCenter;
import entities.*;
import entities.dictionary.*;
import tables.OrderJobsTableModel;
import tables.StatusesTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;
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
    private JButton addEquipmentButton;
    private JButton deleteEquipmentButton;
    private JButton editStatusbutton;
    private JButton addInServiceStatusButtonbutton;
    private JButton addReadyStatusButton;
    private JButton okButton;
    private JButton cancelButton;
    private JList equipmentPartsList;
    private JFormattedTextField orderDateTextField;
    private JButton addOutStatusButton;
    private JButton addStatusButton;
    private JComboBox statusComboBox;
    private JButton removeStatusButton;
    private JTextArea notesTextArea;
    private JButton addJobButton;
    private JButton button2;
    private JTable jobsTable;

    private int orderId;
    private Order order;
    private boolean isChanged;
    private ServiceCenter sc;

    public OrderCreationForm(int orderId) throws HeadlessException, SQLException {
        sc = new ServiceCenter();
        this.orderId = orderId;
        order = sc.getOrder(orderId);
        setContentPane(pannel1);
        setSize(1000, 700);
        OrderCreationController.getController().clearAll();
        isChanged = false;
        setResizable(false);

        try {
           fillForms();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void fillForms() throws SQLException {
        prepareComboBoxes();
        setListeners();
        if(orderId > -1){
            fillFields();
        }


    }

    private void prepareComboBoxes() throws SQLException {
        addAllDeviceTypes();
        addAllManufacturers();
        addAllDefects();
        addAllStatuses();
        deviceTypeCombobox.setSelectedIndex(-1);
        manufacturerComboBox.setSelectedIndex(-1);
        defectComboBox.setSelectedIndex(-1);
        statusComboBox.setSelectedIndex(0);

        DefaultListModel listModel = new DefaultListModel();
        equipmentPartsList.setModel(listModel);
    }

    private void setListeners(){
        findInDictionaryButton.addActionListener(new MyButtonListener());
        addEquipmentButton.addActionListener(new AddEquipmentButtonListener());
        okButton.addActionListener(new OkButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        deleteEquipmentButton.addActionListener(new RemoveEquipmentFromListButtonListener());
        defectComboBox.addActionListener(e -> {
            defectsTextArea.append(((JComboBox) e.getSource()).getSelectedItem().toString());
            defectsTextArea.append("\n");

        });
        addStatusButton.addActionListener(new AddStatusButtonListener());
        removeStatusButton.addActionListener(new RemoveStatusButtonListener());
        addInServiceStatusButtonbutton.addActionListener(new AddInServiceStatusButtonListener());
        addReadyStatusButton.addActionListener(new AddReadyStatusButtonListener());
        addOutStatusButton.addActionListener(new AddOutStatusButtonListener());
        editStatusbutton.addActionListener(new EditStatusButtonListener());
        findInDictionaryButton.addActionListener(new FindInDictionaryButtonListener());
        clientNameTextField.addFocusListener(new TextFieldFocusListener());
        deviceTypeCombobox.addFocusListener(new ComboBoxFocusListener());
        manufacturerComboBox.addFocusListener(new ComboBoxFocusListener());

        addJobButton.addActionListener(new AddJobButtonListener());
    }

    private void fillFields() throws SQLException {
        OrderCreationController.getController().setClient(order.getClient());
        OrderCreationController.getController().setDevice(order.getDevice());
        setClientFromDictionary();
        setDeviceFromClient();

        StringBuilder defects = new StringBuilder();
        order.getDefects().forEach(v -> {
            defects.append(v.getName());
            defects.append("\n");
        });
        defectsTextArea.setText(defects.toString());
        DefaultListModel listModel = (DefaultListModel) equipmentPartsList.getModel();
        order.getEquipments().forEach(v -> {
            listModel.addElement(v.getEquipmentPart());
            OrderCreationController.getController().addEquipment(v.getEquipmentPart().getId());
        });

        StatusesTableModel tableModel = new StatusesTableModel(order);
        statusesTable.setModel(tableModel);

        Locale local = new Locale("ru","RU");
        DateFormat df = DateFormat.getDateTimeInstance (DateFormat.DEFAULT,DateFormat.DEFAULT,local);
        orderDateTextField.setValue(df.format(order.getDate()));
        notesTextArea.setText(order.getNotes());

        OrderJobsTableModel jobTableModel = new OrderJobsTableModel(order);
        jobsTable.setModel(jobTableModel);
        jobsTable.getColumnModel().getColumn(0).setPreferredWidth(500);

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

    @SuppressWarnings("unchecked")
    private void addAllStatuses() throws SQLException {
        for (Status status : DictionaryHelper.getInstance().getStatuses()) {
            statusComboBox.addItem(status.getName());
        }
    }

    private void fillEquipmentParts() throws SQLException {
        List<EquipmentPart> equipmentParts = DictionaryHelper.getInstance().getEquipmentParts(OrderCreationController.getController().getEquipments());
        DefaultListModel listModel = (DefaultListModel) equipmentPartsList.getModel();
        equipmentParts.forEach(listModel::addElement);
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

        if (isCreation()) {
            int client;
            int device;
            if (isClientSelected()){
                client = OrderCreationController.getController().getClient().getId();
            }else {
                client = sc.createClient(clientNameTextField.getText(), clientPhoneTextField.getText(), clientUrlTextField.getText(), "");
            }

            if (isDeviceSelected()){
                device = OrderCreationController.getController().getDevice().getId();
            }else {
                device = sc.createDevice(DictionaryHelper.getInstance().getDeviceType(deviceTypeCombobox.getSelectedIndex()),
                        DictionaryHelper.getInstance().getManufacturer(manufacturerComboBox.getSelectedIndex()),
                        modelTextField.getText(),
                        "",
                        ""
                );
            }
            order = sc.createOrder(client,
                    device,
                    DictionaryHelper.getInstance().getDefects(defectsTextArea.getText()),
                    OrderCreationController.getController().getEquipments()
            );
            order.setNotes(notesTextArea.getText());
            TableModel tableModel =  statusesTable.getModel();
            if (tableModel instanceof StatusesTableModel) {
                sc.addStatusesToOrder(order, ((StatusesTableModel) statusesTable.getModel()).getStatuses());
            } else {
                sc.addStatusesToOrder(order, Collections.singletonList(new OrderStatus(DictionaryHelper.getInstance().getAcceptedStatus(), new Date())));
            }
            sc.addJobsToOrder(((OrderJobsTableModel)jobsTable.getModel()).getJobs());
            sc.updateOrder(order);


        } else {
            Client client = order.getClient();
            client.setFio(clientNameTextField.getText());
            client.setPhone(clientPhoneTextField.getText());
            client.setUrl(clientUrlTextField.getText());
            sc.updateClient(client);

            Device device;
            if (isDeviceSelected()){
                device = OrderCreationController.getController().getDevice();
                order.setDevice(device);
            } else {
                device = order.getDevice();
            }
            device.setType(DictionaryHelper.getInstance().getDeviceType(deviceTypeCombobox.getSelectedIndex()));
            device.setManufacturer(DictionaryHelper.getInstance().getManufacturer(manufacturerComboBox.getSelectedIndex()));
            device.setModel(modelTextField.getText());
            sc.updateDevice(device);


            DefaultListModel listModel = (DefaultListModel) equipmentPartsList.getModel();
            int size = listModel.getSize();
            List<EquipmentPart> equipmentParts = new ArrayList<>(size);
            for (int i = 0; i < size; i++){
                equipmentParts.add((EquipmentPart) listModel.get(i));
            }
            sc.setNewEquipments(order, equipmentParts);
            sc.setNewDefects(order, defectsTextArea.getText());
            TableModel tableModel =  statusesTable.getModel();
            if (tableModel instanceof StatusesTableModel) {
                sc.addStatusesToOrder(order, ((StatusesTableModel) statusesTable.getModel()).getStatuses());
            } else {
                sc.addStatusesToOrder(order, Collections.singletonList(new OrderStatus(DictionaryHelper.getInstance().getAcceptedStatus(), new Date())));
            }
            order.setNotes(notesTextArea.getText());
            sc.addJobsToOrder(((OrderJobsTableModel)jobsTable.getModel()).getJobs());
            sc.updateOrder(order);
        }

        dispose();

    }

    private void closeWindow(){
        if (!isCreation() || isChanged){
            JDialog dialog = createConfirmDialog();
            dialog.setVisible(true);
        }
        dispose();
    }

    private JDialog createConfirmDialog(){
        JDialog dialog = new JDialog(this, "Подтвердите закрытие", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(200, 130);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Данные были изменены"));
        panel.add(new JLabel("Сохранить изменения?"));

        JButton okButton = new JButton("Да");
        JButton cancelButton = new JButton("Нет");
        okButton.addActionListener(new SaveChangesButtonListener());
        cancelButton.addActionListener(new DiscardChangesButtonListener());

        panel.add(okButton);
        panel.add(cancelButton);
        dialog.add(panel);
        dialog.setLocationRelativeTo(null);
        return dialog;
    }

    private void removeEquipmentFromList(){
        int selectedElement = equipmentPartsList.getSelectedIndex();
        DefaultListModel listModel = (DefaultListModel) equipmentPartsList.getModel();
        EquipmentPart selectedEquipmentPart = (EquipmentPart) listModel.getElementAt(selectedElement);
        OrderCreationController.getController().removeEquipment(selectedEquipmentPart.getId());
        listModel.remove(selectedElement);
    }

    private void addStatus(Status status){
        TableModel tableModel =  statusesTable.getModel();
        if (tableModel instanceof StatusesTableModel) {
            ((StatusesTableModel) statusesTable.getModel()).addStatus(status);
        } else {
            StatusesTableModel statusesTableModel = new StatusesTableModel();
            statusesTableModel.addStatus(status);
            statusesTable.setModel(statusesTableModel);
        }
        statusesTable.revalidate();
    }

    private void addJob(){
        try {
            JobSelectionForm jobSelectionForm = new JobSelectionForm();
            jobSelectionForm.setVisible(true);
            jobSelectionForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
            List<Job> jobs = OrderCreationController.getController().getJobs();
            if (jobs != null){
                List<OrderJob> orderJobs = new ArrayList<>(jobs.size());
                jobs.forEach(v -> orderJobs.add(new OrderJob(order, v, v.getPrice(), 1, UserSessionController.getInstance().getUser())));
                ((OrderJobsTableModel)jobsTable.getModel()).addJobs(orderJobs);
                jobsTable.revalidate();
            }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean isCreation(){
        return orderId < 0;
    }

    private boolean isClientSelected(){
        return OrderCreationController.getController().getClient() != null;
    }

    private boolean isDeviceSelected(){
        return OrderCreationController.getController().getDevice() != null;
    }

    private void setClientFromDictionary() throws SQLException {
        Client client = OrderCreationController.getController().getClient();
        clientNameTextField.setText(client.getFio());
        clientPhoneTextField.setText(client.getPhone());
        clientUrlTextField.setText(client.getUrl());
        for (Device device: sc.getAllDevisesForClient(client)){
            deviceComboBox.addItem(device.getManufacturer().getName() + " " + device.getModel());
        }
        deviceComboBox.setSelectedIndex(-1);
        deviceComboBox.addActionListener(new SelectDeviceInComboBoxListener());
    }

    private void setDeviceFromClient(){
        Device device = OrderCreationController.getController().getDevice();
        deviceTypeCombobox.setSelectedIndex(DictionaryHelper.getInstance().getDeviceTypeIndex(device.getType()));
        manufacturerComboBox.setSelectedIndex(DictionaryHelper.getInstance().getManufacturerIndex(device.getManufacturer()));
        modelTextField.setText(device.getModel());
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

    private class CancelButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            closeWindow();
        }
    }

    private class SaveChangesButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveChanges();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class DiscardChangesButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class RemoveEquipmentFromListButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removeEquipmentFromList();
        }
    }

    private class TestListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class AddStatusButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addStatus(DictionaryHelper.getInstance().getStatus(statusComboBox.getSelectedIndex()));
        }
    }

    private class AddInServiceStatusButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addStatus(DictionaryHelper.getInstance().getInServiceStatus());
        }
    }

    private class AddReadyStatusButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addStatus(DictionaryHelper.getInstance().getReadyStatus());
        }
    }

    private class AddOutStatusButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addStatus(DictionaryHelper.getInstance().getOutStatus());
        }
    }

    private class RemoveStatusButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = statusesTable.getSelectedRow();
            if (rowIndex >= 0 && rowIndex < statusesTable.getModel().getRowCount()) {
                ((StatusesTableModel) statusesTable.getModel()).removeStatus(rowIndex);
            }
            statusesTable.revalidate();
        }
    }

    private class EditStatusButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = statusesTable.getSelectedRow();
            if (rowIndex >= 0 && rowIndex < statusesTable.getModel().getRowCount()) {
                ((StatusesTableModel) statusesTable.getModel()).getStatuses().get(rowIndex).setStatus(DictionaryHelper.getInstance().getStatus(statusComboBox.getSelectedIndex()));
            }
            statusesTable.revalidate();
            statusesTable.updateUI();
        }
    }

    private class FindInDictionaryButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ClientSelectionForm clientSelectionForm = new ClientSelectionForm();
                clientSelectionForm.setVisible(true);
                clientSelectionForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            setClientFromDictionary();
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

    private class SelectDeviceInComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int index = deviceComboBox.getSelectedIndex();
                if (index >= 0 && index < sc.getAllDevisesForClient(OrderCreationController.getController().getClient()).size()){
                    Device device = sc.getAllDevisesForClient(OrderCreationController.getController().getClient()).get(index);
                    OrderCreationController.getController().setDevice(device);
                    setDeviceFromClient();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class TextFieldFocusListener implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField textField = (JTextField)e.getSource();
            if (!textField.getText().isEmpty()){
                textField.setBackground(new Color(255, 255 , 255));
            }
        }
    }

    private class ComboBoxFocusListener implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            JComboBox comboBox = (JComboBox)e.getSource();
            if (comboBox.getSelectedIndex() >= 0){
                comboBox.setBackground(new Color(238, 238 , 238));
            }
        }
    }

    private class AddJobButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addJob();
        }
    }
}
