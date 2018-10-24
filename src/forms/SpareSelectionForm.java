package forms;

import controllers.InvoiceController;
import db.DictionaryHelper;
import entities.dictionary.SparePart;
import tables.SparePartsTableModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class SpareSelectionForm extends JFrame {
    private JTree categoryTree;
    private JTable sparePartsTable;
    private JButton button1;
    private JButton button2;
    private JPanel spareSelectionPanel;

    public SpareSelectionForm() throws SQLException {
        setContentPane(spareSelectionPanel);
        setSize(800, 600);
        buildCategoriesTree();

        sparePartsTable.addMouseListener(new TableMouseListener());
    }

    private void buildCategoriesTree() throws SQLException {
        List<SparePart> spareParts = DictionaryHelper.getInstance().getSpareParts();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Все");
        for (SparePart sparePart: spareParts){
            addSparePartsCategoryToTree(sparePart, root, 0);
        }

        DefaultTreeModel treeModel1 = new DefaultTreeModel(root, true);
        categoryTree.setModel(treeModel1);
        categoryTree.addMouseListener(new CategoryListMouseListener());

    }

    private void addSparePartsCategoryToTree(SparePart sparePart, DefaultMutableTreeNode node, int level){
        boolean nodeFound = false;
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (level < sparePart.getPath().length && child.toString().equals(sparePart.getPath()[level])) {
                nodeFound = true;
                addSparePartsCategoryToTree(sparePart, child, level + 1);
            }
        }
        if(!nodeFound){
            DefaultMutableTreeNode buferNode = node;
            for (int i = level; i < sparePart.getPath().length; i++){
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sparePart.getPath()[i]);
                buferNode.add(newNode);
                buferNode = newNode;
            }
        }
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
                SparePartsTableModel sparePartsTableModel = new SparePartsTableModel(DictionaryHelper.getInstance().getSparesOfCategory(category.toString()));
                sparePartsTable.setModel(sparePartsTableModel);
                sparePartsTable.revalidate();
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
                int row = sparePartsTable.getSelectedRow();
                SparePart sparePart = ((SparePartsTableModel)sparePartsTable.getModel()).getSparePart(row);
                InvoiceController.getInstance().addSpare(sparePart);
                dispose();
            }


        }
    }

}
