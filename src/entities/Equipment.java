package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.EquipmentPart;

/* Комплектность заказа */
@DatabaseTable(tableName = "equipments")
public class Equipment {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "equipmentPart", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private EquipmentPart equipmentPart;

    public Equipment(Order order, EquipmentPart equipmentPart) {
        this.order = order;
        this.equipmentPart = equipmentPart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public EquipmentPart getEquipmentPart() {
        return equipmentPart;
    }

    public void setEquipmentPart(EquipmentPart equipmentPart) {
        this.equipmentPart = equipmentPart;
    }
}
