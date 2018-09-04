import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.*;
import entities.dictionary.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ConnectionSource connectionSource =
                null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:sc.s3db");
            /*Dao<Device, String> deviceDao =
                    DaoManager.createDao(connectionSource, Device.class);
            Device device = deviceDao.queryForId("1");
            System.out.println(device.getType().getName());
            System.out.println(device.getManufacturer().getName());*/

            /*
            Dao<Order, String> orderDao = DaoManager.createDao(connectionSource, Order.class);
            Order o = new Order(new Client("test client fio"), new Device(), "note");
            //orderDao.create(o);
            Order order = orderDao.queryForId("1");
            //order.getPhotos().add(new Photo("Link to Photo"));

            Dao<Client, String> clientDao = DaoManager.createDao(connectionSource, Client.class);
            Client client = clientDao.queryForId("1");
            client.setFio("test Client");
            clientDao.update(client);
*/
            TableUtils.createTable(connectionSource, Defect.class);
            TableUtils.createTable(connectionSource, DeviceType.class);
            TableUtils.createTable(connectionSource, EquipmentPart.class);
            TableUtils.createTable(connectionSource, Job.class);
            TableUtils.createTable(connectionSource, Manufacturer.class);
            TableUtils.createTable(connectionSource, SparePart.class);
            TableUtils.createTable(connectionSource, Status.class);
            TableUtils.createTable(connectionSource, Client.class);
            TableUtils.createTable(connectionSource, Device.class);
            TableUtils.createTable(connectionSource, DeviceDefect.class);
            TableUtils.createTable(connectionSource, Equipment.class);
            TableUtils.createTable(connectionSource, Invoice.class);
            TableUtils.createTable(connectionSource, InvoiceSpare.class);
            TableUtils.createTable(connectionSource, Order.class);
            TableUtils.createTable(connectionSource, OrderJob.class);
            TableUtils.createTable(connectionSource, OrderSpare.class);
            TableUtils.createTable(connectionSource, OrderStatus.class);
            TableUtils.createTable(connectionSource, Photo.class);
            TableUtils.createTable(connectionSource, SparePhoto.class);
            TableUtils.createTable(connectionSource, StoreEntity.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
