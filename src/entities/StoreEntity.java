package entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
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
    private double count;
    @DatabaseField
    private double buyPrice;
    @DatabaseField
    private double salePrice;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<SparePhoto> photos;

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

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public ForeignCollection<SparePhoto> getPhotos() {
        return photos;
    }

    public void addPhoto(SparePhoto photo) {
        this.photos.add(photo);
    }

    public String[] getPath(){
        return sparePart.getPath();
    }
}
