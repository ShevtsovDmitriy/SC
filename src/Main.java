import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.InvoiceSpare;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ConnectionSource connectionSource =
                null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:sc.s3db");
            TableUtils.createTable(connectionSource, InvoiceSpare.class);
/*
            ServiceCenter serviceCenter = new ServiceCenter();
            serviceCenter.createUser("Admin", "admin", UserRole.ADMIN);
*/

/*

            int client = serviceCenter.createClient("Test Client", "123456789", "vk.com", "note");
            DeviceType type = db.DaoHelper.getInstance().DEVICE_TYPE_DAO.queryForId("1");
            Manufacturer manufacturer = db.DaoHelper.getInstance().MANUFACTURER_DAO.queryForId("1");
            int device = serviceCenter.createDevice(type, manufacturer, "Iphone 5", "", null);
            ArrayList<Integer> defects = new ArrayList<>(2);
            defects.add(3);
            defects.add(4);
            ArrayList<Integer> equipments = new ArrayList<>(2);
            equipments.add(1);
            equipments.add(2);
            serviceCenter.createOrder(client, device, defects, equipments);

*/
/*
            Order order = serviceCenter.getOrder(1);
            String s1 = order.getClient().getFio();
            String s2 = order.getDevice().getManufacturer().getName();
            String s3 = order.getDevice().getModel();
            StringBuilder s4 = new StringBuilder();
            for(Defect defect: order.getDefects()){
                s4.append(defect.getName());
                s4.append(", ");
            }
            System.out.print("Client: " + s1 + " Device: " + s2 + " " + s3 + " Defects: " + s4);


*/

            /*Dao<Device, String> deviceDao =
                    DaoManager.createDao(connectionSource, Device.class);
            Device device = deviceDao.queryForId("1");
            System.out.println(device.getType().getName());
            System.out.println(device.getManufacturer().getName());


            Dao<Order, String> orderDao = DaoManager.createDao(connectionSource, Order.class);
            Order o = new Order(DaoHelper.getInstance().CLIENT_DAO.queryForId("1"), DaoHelper.getInstance().DEVICE_DAO.queryForId("1"));
            orderDao.create(o);*/
            //Order order = orderDao.queryForId("1");
            //order.getPhotos().add(new Photo("Link to Photo"));



           /* TableUtils.createTable(connectionSource, Defect.class);
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
            TableUtils.createTable(connectionSource, StoreEntity.class);*/


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
