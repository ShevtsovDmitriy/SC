package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.Status;

import java.util.Date;

/* Статус заказа */
@DatabaseTable(tableName = "order_statuses")
public class OrderStatus {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "status", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Status status;
    @DatabaseField
    private Date date;

    public OrderStatus() {
    }

    public OrderStatus(Order order, Status status, Date date) {
        this.order = order;
        this.status = status;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
