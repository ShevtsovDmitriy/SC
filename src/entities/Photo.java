package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/* Фото заказа */
@DatabaseTable(tableName = "photos")
public class Photo {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField
    private String link;

    public Photo() {
    }

    public Photo(String link) {
        this.link = link;
    }

    public Photo(Order order, String link) {
        this.order = order;
        this.link = link;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
