package entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.Defect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    @ForeignCollectionField
    private ForeignCollection<Photo> photos;
    @ForeignCollectionField
    private ForeignCollection<DeviceDefect> defects;
    @ForeignCollectionField
    private ForeignCollection<Equipment> equipments;
    @ForeignCollectionField
    private ForeignCollection<OrderJob> jobs;
    @ForeignCollectionField
    private ForeignCollection<OrderSpare> spares;
    @ForeignCollectionField
    private ForeignCollection<OrderStatus> statuses;


    public Order() {
        date = new Date();
        warranty = false;
    }

    public Order(Client client, Device device) throws SQLException {
        this();
        this.client = client;
        this.device = device;
    }

    public Order(Client client, Device device, Collection<DeviceDefect> defects) throws SQLException {
        this();
        this.client = client;
        this.device = device;
        addAllDefects(defects);
    }

    public Order(Client client, Device device, Collection<DeviceDefect> defects, Collection<Equipment> equipments) throws SQLException {
        this.client = client;
        this.device = device;
        addAllDefects(defects);
        addAllEquipments(equipments);
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

    public Collection<Defect> getDefects() {
        ArrayList<Defect> defects = new ArrayList<>(5);
        for (DeviceDefect defect: this.defects){
            defects.add(defect.getDefect());
        }
        return defects;
    }

    public void addDefect(DeviceDefect defect) {
        this.defects.add(defect);
    }

    public List<OrderJob> getJobs() {
        return new ArrayList<>(jobs);
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

    public List<OrderStatus> getStatuses() {
        return new ArrayList<>(statuses);
    }

    public OrderStatus getLastStatus(){
        return statuses.stream().max(OrderStatus::compareTo).get();
    }

    public void addStatus(OrderStatus status) {
        this.statuses.add(status);
    }

    private void addAllDefects(Collection<DeviceDefect> defects){
        this.defects.addAll(defects);
    }

    private void addAllEquipments(Collection<Equipment> equipments){
        this.equipments.addAll(equipments);
    }

    public Collection<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ForeignCollection<Equipment> equipments) {
        this.equipments = equipments;
    }

}
