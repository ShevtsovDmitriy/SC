package forms;

import controllers.StoreController;
import entities.StoreEntity;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class StoreForm extends JFrame {
    private JTree categoryTree;
    private JTable table1;
    private JButton addEntityButton;
    private JButton button2;
    private JButton button3;
    private JPanel storePane;

    public StoreForm() throws SQLException {
        setContentPane(storePane);
        setSize(600, 600);
        buildCategoriesTree();
        addEntityButton.addActionListener(new AddEntityButtonListener());
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



private class AddEntityButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        InvoicesForm invoicesForm;
        try {
            invoicesForm = new InvoicesForm();
            invoicesForm.setTitle("Накладные");
            invoicesForm.setVisible(true);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
}

}
