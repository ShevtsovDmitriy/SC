package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.SparePart;

/* Деталь в накладной */
@DatabaseTable(tableName = "device_defects")
public class InvoiceSpare {

    @DatabaseField(columnName  = "invoice", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Invoice invoice;
    @DatabaseField(columnName  = "sparePart", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private SparePart sparePart;

}
