import entities.dictionary.EquipmentPart;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

        ArrayList<EquipmentPart> equipmentParts = new ArrayList<>();
        Connection conn;
/*
        ConnectionSource connectionSource =
                new JdbcConnectionSource("jdbc:sqlite:D:/По ремонту/SC/sc.s3db");
        Dao<EquipmentPart, String> equipDao =
                DaoManager.createDao(connectionSource, EquipmentPart.class);


        for (Object anEquipDao : equipDao) {
            equipmentParts.add((EquipmentPart) anEquipDao);
        }


        TableModel model = new MyTableModel(equipmentParts);
        table1.setColumnSelectionAllowed(true);
        table1.setModel(model);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);*/
    }

    private class MyTableModel implements TableModel {

        private Set<TableModelListener> listeners = new HashSet<>();

        private ArrayList<EquipmentPart> equipmentParts;

        public MyTableModel(ArrayList<EquipmentPart> equipmentParts) {
            this.equipmentParts = equipmentParts;
        }

        @Override
        public int getRowCount() {
            return equipmentParts.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Имя";
                case 2:
                    return "Описание";
            }
            return "";
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            EquipmentPart entity = equipmentParts.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entity.getId();
                case 1:
                    return entity.getName();
            }
            return "";
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                equipmentParts.get(rowIndex).setName(aValue.toString());
            }
        }

        @Override
        public void addTableModelListener(TableModelListener listener) {
            listeners.add(listener);
        }

        @Override
        public void removeTableModelListener(TableModelListener listener) {
            listeners.remove(listener);
        }
    }

    private class MyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            label1.setText("yeah");

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
