package entities.dictionary;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/* Тип устройства */
@DatabaseTable(tableName = "device_types")
public class DeviceType {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;

    public DeviceType() {
    }

    public DeviceType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
