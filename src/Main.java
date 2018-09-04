import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

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

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
