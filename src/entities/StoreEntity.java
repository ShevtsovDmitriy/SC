package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.SparePart;

/* Деталь на складе */
@DatabaseTable(tableName = "store_entities")
public class StoreEntity {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName  = "sparePart", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private SparePart sparePart;
    @DatabaseField
    private int count;
    @DatabaseField
    private float buyPrice;
    @DatabaseField
    private float salePrice;

    public StoreEntity() {
    }

    public StoreEntity(SparePart sparePart, int count, float buyPrice, float salePrice) {
        this.sparePart = sparePart;
        this.count = count;
        this.buyPrice = buyPrice;
        this.salePrice = salePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }
}
