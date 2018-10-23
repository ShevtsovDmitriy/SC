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
    private User user;
    @DatabaseField
    private double price;
    @DatabaseField
    private double quantity;

    public OrderSpare() {
    }

    public OrderSpare(Order order, SparePart sparePart, User user, double price, double quantity) {
        this.order = order;
        this.sparePart = sparePart;
        this.user = user;
        this.price = price;
        this.quantity = quantity;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
