import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import entities.Client;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestForm extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JLabel label1;
    private JTable table1;

    public TestForm() throws HeadlessException {
        setContentPane(panel1);
        setVisible(true);
        setSize(640, 480);
        //setExtendedState(MAXIMIZED_BOTH);
        button1.addActionListener(new MyButtonListener());

        ArrayList<Entity> entities = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            entities.add(new Entity("Имя " + i, "Описание " + i));
        }

        TableModel model = new MyTableModel(entities);
        table1.setColumnSelectionAllowed(true);
        table1.setModel(model);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private class MyTableModel implements TableModel {

        private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

        private ArrayList<Entity> entities;

        public MyTableModel(ArrayList<Entity> entities) {
            this.entities = entities;
        }

        @Override
        public int getRowCount() {
            return entities.size();
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
            Entity entity = entities.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entity.getName();
                case 1:
                    return entity.getDescription();
            }
            return "";
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            entities.get(rowIndex).setDescription(aValue.toString());
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
        new TestForm();
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:D:/По ремонту/SC/sc.s3db");
            Statement statmt = conn.createStatement();
            //statmt.execute("INSERT INTO 'client' ('fio', 'phone') VALUES ('Petya', '125453'); ")
            ConnectionSource connectionSource =
                    new JdbcConnectionSource("jdbc:sqlite:D:/По ремонту/SC/sc.s3db");
            Dao<Client, String> clientDao =
                    DaoManager.createDao(connectionSource, Client.class);
            clientDao.create(new Client("test", "89056550654", "vk.com"));



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }


}
