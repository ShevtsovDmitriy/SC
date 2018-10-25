package controllers;

import db.DaoHelper;
import entities.Invoice;
import entities.InvoiceSpare;
import entities.dictionary.SparePart;
import exceptions.InvoiceAlreadyOpenedException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addSpare(SparePart sparePart) throws SQLException {
        int count = 1;
        List<InvoiceSpare> invoiceSpareList = invoice.getSpares().stream().filter(invoiceSpare -> invoiceSpare.getSparePart().equals(sparePart)).collect(Collectors.toList());
        if (!invoiceSpareList.isEmpty()){
            InvoiceSpare spare =  invoiceSpareList.iterator().next();
            spare.plusOne();
            invoice.getSpares().update(spare);
            StoreController.getController().addEntityToStock(spare.getSparePart(), 1, sparePart.getDefaultPrice(), sparePart.getDefaultPrice());
        } else {
            InvoiceSpare spare = new InvoiceSpare(invoice, sparePart, count, sparePart.getDefaultPrice(), sparePart.getDefaultPrice());
            invoice.addSpare(spare);
            StoreController.getController().addEntityToStock(spare.getSparePart(), 1, sparePart.getDefaultPrice(), sparePart.getDefaultPrice());
        }

    }

}
