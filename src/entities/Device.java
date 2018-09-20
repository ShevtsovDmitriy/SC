package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.DeviceType;
import entities.dictionary.Manufacturer;
/* Устройство */
@DatabaseTable(tableName = "devices")
public class Device {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName  = "type", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private DeviceType type;
    @DatabaseField(columnName  = "manufacturer", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Manufacturer manufacturer;
    @DatabaseField
    private String model;
    @DatabaseField
    private String notes;
    @DatabaseField
    private String condition;

    public Device() {
    }

    public Device(DeviceType type, Manufacturer manufacturer, String model, String notes, String condition) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.notes = notes;
        this.condition = condition;
    }

    public Device(DeviceType type, Manufacturer manufacturer, String model) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public Device(DeviceType type, Manufacturer manufacturer, String model, String condition) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Device && ((Device) obj).getId() == this.getId();
    }
}
