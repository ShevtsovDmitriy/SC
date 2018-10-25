package entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.InvoiceType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* Накладная */
@DatabaseTable(tableName = "invoices")
public class Invoice {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private Date date;
    @DatabaseField
    private InvoiceType type;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<InvoiceSpare> spares;

    public Invoice() {
    }

    public Invoice(Date date, InvoiceType type) {
        this.date = date;
        this.type = type;
    }

    public Invoice(InvoiceType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public List<InvoiceSpare> getSparesList() {
        return new ArrayList<>(spares);
    }

    public ForeignCollection<InvoiceSpare> getSpares() {
        return spares;
    }

    public void setSpares(ForeignCollection<InvoiceSpare> spares) {
        this.spares = spares;
    }

    public void addSpare(InvoiceSpare spare) {
        this.spares.add(spare);
    }
}
