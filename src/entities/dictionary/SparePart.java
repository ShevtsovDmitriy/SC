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
    private double defaultPrice;
    @DatabaseField
    private String manufacturer;

    public SparePart() {
    }

    public SparePart(String name, String category, double defaultPrice, String manufacturer) {
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

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String[] getPath(){
        return getCategory().split("/");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SparePart && this.id == ((SparePart)obj).id;
    }
}
