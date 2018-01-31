package entities.dictionary;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "defects")
public class Defect {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;

    public Defect() {
    }

    public Defect(String name) {
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
