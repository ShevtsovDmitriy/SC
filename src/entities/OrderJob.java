package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.Job;
import utils.Utils;

/* Выполненные работы */
@DatabaseTable(tableName = "order_jobs")
public class OrderJob {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "job", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Job job;
    @DatabaseField(columnName  = "user", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private User user;
    @DatabaseField
    private double price;
    @DatabaseField
    private double quantity;


    public OrderJob() {
    }

    public OrderJob(Order order, Job job, double price, double quantity, User user) {
        this.order = order;
        this.job = job;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getStringPrice() {
        return Utils.formatDouble(price);
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

    public String getStringQuantity() {
        return Utils.formatDouble(quantity);
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void addOne(){
        this.quantity++;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OrderJob && this.getOrder().getId() == ((OrderJob)obj).getOrder().getId() && this.getJob().getId() == ((OrderJob)obj).getJob().getId();
    }

}
