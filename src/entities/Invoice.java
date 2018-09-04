package entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.InvoiceType;

import java.util.Date;

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
    ForeignCollection<InvoiceSpare> spares;


}
