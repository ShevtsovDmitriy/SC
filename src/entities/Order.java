package entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/* Заказ */
@DatabaseTable(tableName = "orders")
public class Order {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName  = "client", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Client client;
    @DatabaseField(columnName  = "device", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Device device;
    @DatabaseField
    private String notes;
    @DatabaseField
    private Boolean warranty;
    @DatabaseField
    private Date date;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<Photo> photos;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<DeviceDefect> defects;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<OrderJob> jobs;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<OrderSpare> spares;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<OrderStatus> statuses;


    public Order() {
        date = new Date();
        warranty = false;
    }

    public Order(Client client, Device device) {
        this();
        this.client = client;
        this.device = device;
    }

    public Order(Client client, Device device, ForeignCollection<DeviceDefect> defects) {
        this();
        this.client = client;
        this.device = device;
        this.defects = defects;
    }

    public Order(Client client, Device device, String notes, ForeignCollection<Photo> photos) {
        this();
        this.client = client;
        this.device = device;
        this.notes = notes;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ForeignCollection<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    public Boolean getWarranty() {
        return warranty;
    }

    public void setWarranty(Boolean warranty) {
        this.warranty = warranty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ForeignCollection<DeviceDefect> getDefects() {
        return defects;
    }

    public void addDefect(DeviceDefect defect) {
        this.defects.add(defect);
    }

    public ForeignCollection<OrderJob> getJobs() {
        return jobs;
    }

    public void addJob(OrderJob job) {
        this.jobs.add(job);
    }

    public ForeignCollection<OrderSpare> getSpares() {
        return spares;
    }

    public void addSpare(OrderSpare spare) {
        this.spares.add(spare);
    }

    public ForeignCollection<OrderStatus> getStatuses() {
        return statuses;
    }

    public void addStatus(OrderStatus status) {
        this.statuses.add(status);
    }
}
