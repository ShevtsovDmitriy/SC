package entities.dictionary;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/* Запчасти */
@DatabaseTable(tableName = "spare_parts")
public class SparePart {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String category;
    @DatabaseField
    private float defaultPrice;
    @DatabaseField
    private float manufacturer;

    public SparePart() {
    }

    public SparePart(String name, String category, float defaultPrice, float manufacturer) {
        this.name = name;
        this.category = category;
        this.defaultPrice = defaultPrice;
        this.manufacturer = manufacturer;
    }

    public SparePart(String name, String category, float defaultPrice) {
        this.name = name;
        this.category = category;
        this.defaultPrice = defaultPrice;
    }

    public SparePart(String name, float defaultPrice) {
        this.name = name;
        this.defaultPrice = defaultPrice;
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

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(float manufacturer) {
        this.manufacturer = manufacturer;
    }
}
