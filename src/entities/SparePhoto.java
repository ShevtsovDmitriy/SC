package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/* Фото детали */
@DatabaseTable(tableName = "spare_photos")
public class SparePhoto {

    @DatabaseField(columnName  = "storeEntity", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private StoreEntity storeEntity;
    @DatabaseField
    private String link;

    public SparePhoto() {
    }

    public SparePhoto(StoreEntity storeEntity, String link) {
        this.storeEntity = storeEntity;
        this.link = link;
    }

    public StoreEntity getStoreEntity() {
        return storeEntity;
    }

    public void setStoreEntity(StoreEntity storeEntity) {
        this.storeEntity = storeEntity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
