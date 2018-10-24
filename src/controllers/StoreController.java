package controllers;

import db.DaoHelper;
import entities.Invoice;
import entities.StoreEntity;
import entities.dictionary.InvoiceType;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class StoreController {

    private DaoHelper dao = DaoHelper.getInstance();

    private static StoreController ourInstance = new StoreController();

    public static StoreController getController() {
        return ourInstance;
    }

    private StoreController() {
    }

    public List<StoreEntity> getAllGoodsInStock() throws SQLException {
        return dao.STORE_ENTITY_DAO.queryForAll();
    }

    public double getEntitiesCount(StoreEntity entity){
        return 1; //TODO: fix stub
    }

    public List<Invoice> getInvoices() throws SQLException {
        return dao.INVOICE_DAO.queryForAll();
    }

    public void createInvoice() throws SQLException {
        dao.INVOICE_DAO.create(new Invoice(new Date(), InvoiceType.PURCHASE));
    }

}
