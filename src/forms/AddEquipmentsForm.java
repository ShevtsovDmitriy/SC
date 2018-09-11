package forms;

import controllers.OrderCreationController;
import db.DictionaryHelper;
import entities.dictionary.EquipmentPart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEquipmentsForm extends JFrame {

    private JPanel panel1;
    private JButton okButton;
    private JPanel checkBoxesPanel;

    private Map<Integer, JCheckBox> checkBoxes;

    public AddEquipmentsForm() throws HeadlessException, SQLException {
        setContentPane(panel1);
        setSize(300, 700);
        fillCheckboxes();
        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCheckedEquipments();
            }
        });


    }

    private void fillCheckboxes() throws SQLException {
        List<EquipmentPart> equipmentParts = DictionaryHelper.getInstance().getAllEquipmentParts();
       checkBoxes = new HashMap<>(equipmentParts.size());
        for (EquipmentPart equipmentPart: equipmentParts){
            checkBoxes.put(equipmentPart.getId(), new JCheckBox(equipmentPart.getName()));
        }
        checkBoxesPanel.setLayout(new BoxLayout(checkBoxesPanel, BoxLayout.Y_AXIS));
        checkBoxes.forEach((k, v) -> {
            checkBoxesPanel.add(v);
            v.setSize(200, 20);
            v.setVerticalAlignment(JCheckBox.TOP);
            v.setVisible(true);

        });
        checkBoxesPanel.setVisible(true);
        checkBoxesPanel.revalidate();

    }

    private void saveCheckedEquipments(){
        List<Integer> equipmentParts = new ArrayList<>(checkBoxes.size()/2);
        checkBoxes.forEach((k, v) -> {
            if(v.isSelected()){
                equipmentParts.add(k);
            }
        });
        OrderCreationController.getController().setEquipments(equipmentParts);
        dispose();
    }

}
