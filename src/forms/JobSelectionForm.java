package forms;

import controllers.OrderCreationController;
import db.DictionaryHelper;
import entities.dictionary.Job;
import tables.JobsTableModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class JobSelectionForm extends JFrame {
    private JTree categoryTree;
    private JTable jobsTable;
    private JPanel jobSelectionPane;
    private JScrollPane jobsScrollPane;
    private JButton button1;
    private JButton button2;
    private JButton button3;

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

}
