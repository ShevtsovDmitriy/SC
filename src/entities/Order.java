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
    ForeignCollection<Photo> photos;
    @ForeignCollectionField(eager = false)
    ForeignCollection<DeviceDefect> defects;
    @ForeignCollectionField(eager = false)
    ForeignCollection<OrderJob> jobs;
    @ForeignCollectionField(eager = false)
    ForeignCollection<OrderSpare> spares;
    @ForeignCollectionField(eager = false)
    ForeignCollection<OrderStatus> statuses;


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

    public void setPhotos(ForeignCollection<Photo> photos) {
        this.photos = photos;
    }
}
