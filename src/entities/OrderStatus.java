package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.Status;

/* Статус заказа */
@DatabaseTable(tableName = "device_defects")
public class OrderStatus {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "status", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Status status;

    public OrderStatus() {
    }

    public OrderStatus(Order order, Status status) {
        this.order = order;
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
