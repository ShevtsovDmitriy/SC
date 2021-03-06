package controllers;

import db.DaoHelper;
import entities.Invoice;
import entities.StoreEntity;
import entities.dictionary.InvoiceType;
import entities.dictionary.SparePart;
import exceptions.InconsistentDataInStoreException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<StoreEntity> getStoreEntitiesOfCategory(String category) throws SQLException {
        return getAllGoodsInStock().stream().filter(e -> e.getSparePart().getCategory().startsWith(category)).collect(Collectors.toList());
    }

    public List<Invoice> getInvoices() throws SQLException {
        return dao.INVOICE_DAO.queryForAll();
    }

    public void createInvoice() throws SQLException {
        dao.INVOICE_DAO.create(new Invoice(new Date(), InvoiceType.PURCHASE));
    }

    public void addEntityToStock(SparePart sparePart, double count,  double buyPrice, double salePrice) throws SQLException {
        List<StoreEntity> entities = getAllGoodsInStock().stream()
                .filter(e -> e.getSparePart().getId()== sparePart.getId() && e.getBuyPrice() == buyPrice && e.getSalePrice() == salePrice)
                .collect(Collectors.toList());

        if (entities != null && !entities.isEmpty()) {
            entities.iterator().next().add(count);
            dao.STORE_ENTITY_DAO.update(entities.iterator().next());
        } else {
            dao.STORE_ENTITY_DAO.create(new StoreEntity(sparePart, count, buyPrice, salePrice));
        }
    }

    public void updateEntityInStock(SparePart sparePart, double count, double newCount, double buyPrice, double newBuyPrice, double salePrice, double newSalePrice, boolean isCountChanges) throws SQLException, InconsistentDataInStoreException {
        List<StoreEntity> entities = getAllGoodsInStock().stream()
                .filter(e -> e.getSparePart().getId()== sparePart.getId() && e.getBuyPrice() == buyPrice && e.getSalePrice() == salePrice)
                .collect(Collectors.toList());
        if (!entities.isEmpty() && ! isCountChanges){
            if (entities.size() > 1){
                throw new InconsistentDataInStoreException("В магазине существует несколько одинаковых товаров");
            }
            StoreEntity entity = entities.iterator().next();
            if (entity.getCount() < count){
                entity.setBuyPrice(newBuyPrice);
                entity.setSalePrice(newSalePrice);
                dao.STORE_ENTITY_DAO.update(entity);
            }else {
                entity.setCount(entity.getCount() - count);
                dao.STORE_ENTITY_DAO.update(entity);
                dao.STORE_ENTITY_DAO.create(new StoreEntity(sparePart, count, newBuyPrice, newSalePrice));
            }
        } else if (entities.isEmpty() && isCountChanges){
            if (newCount > count){
                dao.STORE_ENTITY_DAO.create(new StoreEntity(sparePart, newCount - count, newBuyPrice, newSalePrice));
            }
        } else if (!entities.isEmpty()){
            StoreEntity entity = entities.iterator().next();
            if (newCount > count){
                entity.setCount(newCount - count + entity.getCount());
            } else if (newCount < entity.getCount()){
                entity.setCount(entity.getCount() - (count - newCount));
            } else {
                dao.STORE_ENTITY_DAO.delete(entity);
            }
        }
    }

}
