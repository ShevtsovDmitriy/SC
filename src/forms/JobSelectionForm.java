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
    private JButton button2;
    private JButton addJobButton;

    public JobSelectionForm() throws SQLException {

        List<Job> jobs = DictionaryHelper.getInstance().getAllJobs();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Все");
        for (Job job: jobs){
            addJobToTree(job, root, 0);
        }

        DefaultTreeModel treeModel1 = new DefaultTreeModel(root, true);
        categoryTree.setModel(treeModel1);
        categoryTree.addMouseListener(new CategoryListMouseListener());

        jobsTable.addMouseListener(new TableMouseListener());

        selectJobButton.addActionListener(new SelectJobButtonListener());
        addJobButton.addActionListener(new AddJobButtonListener());

        setContentPane(jobSelectionPane);
        setSize(600, 600);
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
            for (int i = level; i < job.getPath().length; i++){
                node.add(new DefaultMutableTreeNode(job.getPath()[i]));
            }
        }
    }


    private JDialog createJobCreationDialog(){
        JDialog dialog = new JDialog(this, "Создание новой работы", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(600, 130);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JTextField nameField = new JTextField("Наименование");
        nameField.setPreferredSize(new Dimension(150, 24));
        nameField.addFocusListener(new TextFieldFocusListener("Наименование"));
        JTextField costField = new JTextField("Стоимость");
        costField.setPreferredSize(new Dimension(150, 24));
        costField.addFocusListener(new TextFieldFocusListener("Стоимость"));
        panel.add(nameField);
        panel.add(costField);
        //TODO: add all fields and repair focus
        JButton okButton = new JButton("Да");
        JButton cancelButton = new JButton("Нет");
        //okButton.addActionListener(new OrderCreationForm.SaveChangesButtonListener());
        //cancelButton.addActionListener(new OrderCreationForm.DiscardChangesButtonListener());

        panel.add(okButton);
        panel.add(cancelButton);

        dialog.add(panel);
        //dialog.getRootPane().setDefaultButton(cancelButton);
        dialog.setLocationRelativeTo(null);
        okButton.requestFocusInWindow();

        return dialog;
    }

    private class CategoryListMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            TreePath path = categoryTree.getSelectionModel().getSelectionPath();
            if (path != null){
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
    }

    private class TableMouseListener extends MouseAdapter
    {
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
        }
    }

}
