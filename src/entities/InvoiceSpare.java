package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.dictionary.SparePart;

/* Деталь в накладной */
@DatabaseTable(tableName = "invoice_spares")
public class InvoiceSpare {

    @DatabaseField(columnName  = "invoice", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private Invoice invoice;
    @DatabaseField(columnName  = "sparePart", foreign = true, canBeNull = false, foreignAutoRefresh = true)
    private SparePart sparePart;
    @DatabaseField
    private double count;
    @DatabaseField
    private double buyPrice;
    @DatabaseField
    private double salePrice;

    public InvoiceSpare() {
    }

    public InvoiceSpare(Invoice invoice, SparePart sparePart, double count, double buyPrice, double salePrice) {
        this.invoice = invoice;
        this.sparePart = sparePart;
        this.count = count;
        this.buyPrice = buyPrice;
        this.salePrice = salePrice;
    }

    public InvoiceSpare(Invoice invoice, SparePart sparePart) {
        this.invoice = invoice;
        this.sparePart = sparePart;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

}
