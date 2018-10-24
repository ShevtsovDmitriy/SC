package forms;

import controllers.OrderCreationController;
import db.DictionaryHelper;
import entities.dictionary.Job;
import tables.JobsTableModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class JobSelectionForm extends JFrame {
    private JTree categoryTree;
    private JTable jobsTable;
    private JPanel jobSelectionPane;
    private JScrollPane jobsScrollPane;
    private JButton selectJobButton;
    private JButton deleteButton;
    private JButton addJobButton;

    public JobSelectionForm() throws SQLException {

        buildCategoriesTree();
        categoryTree.addMouseListener(new CategoryListMouseListener());

        jobsTable.addMouseListener(new TableMouseListener());

        selectJobButton.addActionListener(new SelectJobButtonListener());
        addJobButton.addActionListener(new AddJobButtonListener());
        deleteButton.addActionListener(new DeleteJobButtonListener());

        setContentPane(jobSelectionPane);
        setSize(600, 600);
    }

    private void buildCategoriesTree() throws SQLException {
        List<Job> jobs = DictionaryHelper.getInstance().getAllJobs();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Все");
        for (Job job: jobs){
            addJobToTree(job, root, 0);
        }

        DefaultTreeModel treeModel1 = new DefaultTreeModel(root, true);
        categoryTree.setModel(treeModel1);
    }

    private void addJobToTree(Job job, DefaultMutableTreeNode node, int level){
        boolean nodeFound = false;
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (level < job.getPath().length && child.toString().equals(job.getPath()[level])) {
                nodeFound = true;
                addJobToTree(job, child, level + 1);
            }
        }
        if(!nodeFound){
            DefaultMutableTreeNode buferNode = node;
            for (int i = level; i < job.getPath().length; i++){
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(job.getPath()[i]);
                buferNode.add(newNode);
                buferNode = newNode;
            }
        }
    }


    private JDialog createJobCreationDialog(){
        return new JobCreationDialog(this, "Создание новой работы", true);
    }

    private void fillTable() {
        TreePath path = categoryTree.getSelectionModel().getSelectionPath();
        if (path != null) {
            StringBuilder category = new StringBuilder();
            for (int i = 1; i < path.getPathCount(); i++) {
                category.append(((DefaultMutableTreeNode) path.getPath()[i]).getUserObject().toString());
                if (i != path.getPathCount() - 1) {
                    category.append("/");
                }
            }
            try {
                JobsTableModel jobsTableModel = new JobsTableModel(DictionaryHelper.getInstance().getJobsOfCategory(category.toString()));
                jobsTable.setModel(jobsTableModel);
                jobsTable.revalidate();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }

    private class CategoryListMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e)
        {
            fillTable();
        }
    }

    private class TableMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2) {
                int row = jobsTable.getSelectedRow();
                Job job = ((JobsTableModel)jobsTable.getModel()).getJob(row);
                OrderCreationController.getController().addJob(job);
                dispose();
            }


        }
    }

    private class SelectJobButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = jobsTable.getSelectedRow();
            if (row >= 0 && row < jobsTable.getModel().getRowCount()){
                Job job = ((JobsTableModel) jobsTable.getModel()).getJob(row);
                OrderCreationController.getController().addJob(job);
                dispose();
            }
        }
    }

    private class AddJobButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = createJobCreationDialog();
            dialog.setVisible(true);
        }
    }

    private class TextFieldFocusListener implements FocusListener {

        private String text;

        public TextFieldFocusListener(String text) {
            this.text = text;
        }


        @Override
        public void focusGained(FocusEvent e) {
            JTextField field = (JTextField)e.getSource();
            if(text.equals(field.getText())){
                field.setText(null);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField field = (JTextField)e.getSource();
            if(field.getText() == null || field.getText().isEmpty()){
                field.setText(text);
            }
            field.setBackground(new Color(255, 255 , 255));
        }
    }

    private class OkButtonListener implements ActionListener {

        JobCreationDialog dialog;

        public OkButtonListener(JobCreationDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = dialog.getName();
            String cost = dialog.getCost();
            String category = dialog.getCategory();
            Color red = new Color(255, 0, 40);
            boolean flag = true;
            if (name.isEmpty()){
                dialog.getNameField().setBackground(red);
                flag = false;
            }
            if (!cost.matches("(([-+])?[0-9]+(\\.[0-9]+)?)+")){
                dialog.getCostField().setBackground(red);
                flag = false;
            }
            if (category.isEmpty()){
                dialog.getCategoryField().setBackground(red);
                flag = false;
            }
            if (!flag){
                return;
            }

            try {
                DictionaryHelper.getInstance().addJob(new Job(name, Double.parseDouble(cost), category));
                fillTable();
                buildCategoriesTree();
                dialog.dispose();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        }
    }

    private class CancelButtonListener implements ActionListener {
        JDialog dialog;

        public CancelButtonListener(JDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    }

    private class JobCreationDialog extends JDialog{
        private JTextField nameField;
        private JTextField costField;
        private JTextField categoryField;

        public JobCreationDialog(Frame owner, String title, boolean modal) {
            super(owner, title, modal);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setSize(900, 130);
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            nameField = new JTextField("Наименование");
            nameField.setPreferredSize(new Dimension(150, 24));
            nameField.addFocusListener(new TextFieldFocusListener("Наименование"));
            costField = new JTextField("Стоимость");
            costField.setPreferredSize(new Dimension(150, 24));
            costField.addFocusListener(new TextFieldFocusListener("Стоимость"));
            categoryField = new JTextField("Категория");
            categoryField.setPreferredSize(new Dimension(300, 24));
            categoryField.addFocusListener(new TextFieldFocusListener("Категория"));
            panel.add(nameField);
            panel.add(costField);
            panel.add(categoryField);
            TreePath path = categoryTree.getSelectionModel().getSelectionPath();
            if (path != null){
                StringBuilder category = new StringBuilder();
                for (int i = 1; i < path.getPathCount(); i++) {
                    category.append(((DefaultMutableTreeNode) path.getPath()[i]).getUserObject().toString());
                    if (i != path.getPathCount() - 1) {
                        category.append("/");
                    }
                }
                categoryField.setText(category.toString());
            }
            JButton okButton = new JButton("Добавить");
            JButton cancelButton = new JButton("Отмена");
            okButton.addActionListener(new OkButtonListener(this));
            cancelButton.addActionListener(new CancelButtonListener(this));

            panel.add(okButton);
            panel.add(cancelButton);

            this.add(panel);
            //dialog.getRootPane().setDefaultButton(cancelButton);
            this.setLocationRelativeTo(null);
            okButton.requestFocusInWindow();
        }

        public JTextField getNameField() {
            return nameField;
        }

        public JTextField getCostField() {
            return costField;
        }

        public JTextField getCategoryField() {
            return categoryField;
        }

        public String getName() {
            return nameField.getText();
        }

        public String getCost() {
            return costField.getText();
        }

        public String getCategory() {
            return categoryField.getText();
        }

    }

    private class DeleteJobButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = jobsTable.getSelectedRow();
            if (rowIndex >= 0 && rowIndex < jobsTable.getModel().getRowCount()) {
                try {
                    DictionaryHelper.getInstance().removeJob(((JobsTableModel) jobsTable.getModel()).getJob(rowIndex));
                    fillTable();
                    buildCategoriesTree();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            jobsTable.revalidate();
        }
    }

}
