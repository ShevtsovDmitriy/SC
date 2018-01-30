package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "equipment_parts")
public class EquipmentPart {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;

    public EquipmentPart() {
    }

    public EquipmentPart(String name) {
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
