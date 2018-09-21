package db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import entities.*;
import entities.dictionary.*;

import java.sql.SQLException;

public class DaoHelper {

    private static DaoHelper ourInstance = new DaoHelper();

    public static DaoHelper getInstance() {
        return ourInstance;
    }

    private DaoHelper() {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:sc.s3db");
            createDaos();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ConnectionSource connectionSource;

    public Dao<Client, String> CLIENT_DAO;
    public Dao<Device, String> DEVICE_DAO;
    public Dao<DeviceDefect, String> DEVICE_DEFECT_DAO;
    public Dao<Equipment, String> EQUIPMENT_DAO;
    public Dao<Invoice, String> INVOICE_DAO;
    public Dao<Order, String> ORDER_DAO;
    public Dao<OrderJob, String> ORDER_JOB_DAO;
    public Dao<OrderSpare, String> ORDER_SPARE_DAO;
    public Dao<OrderStatus, String> ORDER_STATUS_DAO;
    public Dao<Photo, String> PHOTO_DAO;
    public Dao<SparePhoto, String> SPARE_PHOTO_DAO;
    public Dao<StoreEntity, String> STORE_ENTITY_DAO;

    public Dao<Defect, String> DEFECT_DAO;
    public Dao<DeviceType, String> DEVICE_TYPE_DAO;
    public Dao<EquipmentPart, String> EQUIPMENT_PART_DAO;
    public Dao<Job, String> JOB_DAO;
    public Dao<Manufacturer, String> MANUFACTURER_DAO;
    public Dao<SparePart, String> SPARE_PART_DAO;
    public Dao<Status, String> STATUS_DAO;
    public Dao<User, String> USER_DAO;

    private void createDaos() throws SQLException {
        CLIENT_DAO = DaoManager.createDao(connectionSource, Client.class);
        DEVICE_DAO = DaoManager.createDao(connectionSource, Device.class);
        DEVICE_DEFECT_DAO = DaoManager.createDao(connectionSource, DeviceDefect.class);
        EQUIPMENT_DAO = DaoManager.createDao(connectionSource, Equipment.class);
        INVOICE_DAO = DaoManager.createDao(connectionSource, Invoice.class);
        ORDER_DAO = DaoManager.createDao(connectionSource, Order.class);
        ORDER_JOB_DAO = DaoManager.createDao(connectionSource, OrderJob.class);
        ORDER_SPARE_DAO = DaoManager.createDao(connectionSource, OrderSpare.class);
        ORDER_STATUS_DAO = DaoManager.createDao(connectionSource, OrderStatus.class);
        PHOTO_DAO = DaoManager.createDao(connectionSource, Photo.class);
        SPARE_PHOTO_DAO = DaoManager.createDao(connectionSource, SparePhoto.class);
        STORE_ENTITY_DAO = DaoManager.createDao(connectionSource, StoreEntity.class);

        DEFECT_DAO = DaoManager.createDao(connectionSource, Defect.class);
        DEVICE_TYPE_DAO = DaoManager.createDao(connectionSource, DeviceType.class);
        EQUIPMENT_PART_DAO = DaoManager.createDao(connectionSource, EquipmentPart.class);
        JOB_DAO = DaoManager.createDao(connectionSource, Job.class);
        MANUFACTURER_DAO = DaoManager.createDao(connectionSource, Manufacturer.class);
        SPARE_PART_DAO = DaoManager.createDao(connectionSource, SparePart.class);
        STATUS_DAO = DaoManager.createDao(connectionSource, Status.class);
        USER_DAO = DaoManager.createDao(connectionSource, User.class);
    }

}
