package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.Defect;

/* Неиспрвности устройства в заказе */
@DatabaseTable(tableName = "device_defects")
public class DeviceDefect {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "defect", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Defect defect;

    public DeviceDefect() {
    }

    public DeviceDefect(Order order, Defect defect) {
        this.order = order;
        this.defect = defect;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Defect getDefect() {
        return defect;
    }

    public void setDefect(Defect defect) {
        this.defect = defect;
    }
}
