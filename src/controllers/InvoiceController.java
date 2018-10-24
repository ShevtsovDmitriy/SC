package controllers;

import db.DaoHelper;
import entities.Invoice;
import entities.InvoiceSpare;
import entities.dictionary.SparePart;
import exceptions.InvoiceAlreadyOpenedException;

import java.sql.SQLException;

public class InvoiceController {
    private static InvoiceController ourInstance = new InvoiceController();

    public static InvoiceController getInstance() {
        return ourInstance;
    }

    private InvoiceController() {
        isInvoiceOpened = false;
    }

    private Invoice invoice;
    private boolean isInvoiceOpened;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) throws InvoiceAlreadyOpenedException {
        if (!isInvoiceOpened) {
            this.invoice = invoice;
            isInvoiceOpened = true;
        } else {
            throw new InvoiceAlreadyOpenedException("Невозможно редактировать более одной накладной одновременно");
        }
    }

    public void clearInvoice(){
        invoice = null;
        isInvoiceOpened = false;
    }

    public void saveInvoice() throws SQLException {
        if (isInvoiceOpened){
            DaoHelper.getInstance().INVOICE_DAO.update(invoice);
            clearInvoice();
        }
    }

    public void addSpare(SparePart sparePart){
        invoice.addSpare(new InvoiceSpare(invoice, sparePart, 1, sparePart.getDefaultPrice(), sparePart.getDefaultPrice()));
    }

}
