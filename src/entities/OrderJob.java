package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.Job;

/* Выполненные работы */
@DatabaseTable(tableName = "order_jobs")
public class OrderJob {

    @DatabaseField(columnName  = "order", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Order order;
    @DatabaseField(columnName  = "job", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Job job;

    public OrderJob() {
    }

    public OrderJob(Order order, Job job) {
        this.order = order;
        this.job = job;
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
}
