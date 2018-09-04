package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.SparePart;

/* Использованные детали */
@DatabaseTable(tableName = "order_spare_parts")
public class OrderSpare {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "sparePart", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private SparePart sparePart;

    public OrderSpare() {
    }

    public OrderSpare(Order order, SparePart sparePart) {
        this.order = order;
        this.sparePart = sparePart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }
}
