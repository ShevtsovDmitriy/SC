package forms;

import controllers.StoreController;
import entities.StoreEntity;
import tables.StoreTableModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class StoreForm extends JFrame {
    private JTree categoryTree;
    private JTable storeTable;
    private JButton addEntityButton;
    private JButton button2;
    private JButton button3;
    private JPanel storePane;

    public StoreForm() throws SQLException {
        setContentPane(storePane);
        setSize(900, 600);
        buildCategoriesTree();
        addEntityButton.addActionListener(new AddEntityButtonListener());
        categoryTree.addMouseListener(new CategoryListMouseListener());
    }

    private void buildCategoriesTree() throws SQLException {
        List<StoreEntity> storeEntities = StoreController.getController().getAllGoodsInStock();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Все");
        for (StoreEntity storeEntity: storeEntities){
            addStoreEntityCategoryToTree(storeEntity, root, 0);
        }

        DefaultTreeModel treeModel1 = new DefaultTreeModel(root, true);
        categoryTree.setModel(treeModel1);
    }

    private void addStoreEntityCategoryToTree(StoreEntity storeEntity, DefaultMutableTreeNode node, int level){
        boolean nodeFound = false;
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (level < storeEntity.getPath().length && child.toString().equals(storeEntity.getPath()[level])) {
                nodeFound = true;
                addStoreEntityCategoryToTree(storeEntity, child, level + 1);
            }
        }
        if(!nodeFound){
            DefaultMutableTreeNode buferNode = node;
            for (int i = level; i < storeEntity.getPath().length; i++){
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(storeEntity.getPath()[i]);
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
                StoreTableModel storeTableModel = new StoreTableModel(StoreController.getController().getStoreEntitiesOfCategory(category.toString()));
                storeTable.setModel(storeTableModel);
                storeTable.getColumnModel().getColumn(0).setPreferredWidth(400);
                storeTable.revalidate();
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

private class AddEntityButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        InvoicesForm invoicesForm;
        try {
            invoicesForm = new InvoicesForm();
            invoicesForm.setTitle("Накладные");
            invoicesForm.setVisible(true);
            invoicesForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        TreePath path = categoryTree.getSelectionModel().getSelectionPath();
                        fillTable();
                        buildCategoriesTree();
                        categoryTree.getSelectionModel().setSelectionPath(path);
                        categoryTree.revalidate();
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

}
