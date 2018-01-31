import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import entities.Device;
import entities.dictionary.DeviceType;
import entities.dictionary.EquipmentPart;
import entities.dictionary.Manufacturer;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ConnectionSource connectionSource =
                null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:D:/По ремонту/SC/sc.s3db");
            Dao<Device, String> deviceDao =
                    DaoManager.createDao(connectionSource, Device.class);
            Device device = deviceDao.queryForId("1");
            System.out.println(device.getType().getName());
            System.out.println(device.getManufacturer().getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
