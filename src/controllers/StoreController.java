package controllers;

import db.DaoHelper;
import entities.StoreEntity;

import java.sql.SQLException;
import java.util.List;

public class StoreController {

    private static StoreController ourInstance = new StoreController();

    public static StoreController getController() {
        return ourInstance;
    }

    private StoreController() {
    }

    public List<StoreEntity> getAllGoodsInStock() throws SQLException {
        return DaoHelper.getInstance().STORE_ENTITY_DAO.queryForAll();
    }

    public double getEntitiesCount(StoreEntity entity){
        return 1; //TODO: fix stub
    }

}
