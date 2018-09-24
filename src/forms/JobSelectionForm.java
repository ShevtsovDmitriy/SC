package forms;

import db.DictionaryHelper;
import entities.dictionary.Job;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.sql.SQLException;
import java.util.List;

public class JobSelectionForm extends JFrame {
    private JTree cathegoryTree;
    private JTable table1;
    private JPanel jobSelectionPane;

    public JobSelectionForm() throws SQLException {

        List<Job> jobs = DictionaryHelper.getInstance().getAllJobs();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Все");
        for (Job job: jobs){
            addJobToTree(job, root, 0);
        }

        DefaultTreeModel treeModel1 = new DefaultTreeModel(root, true);
        cathegoryTree.setModel(treeModel1);
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

}
