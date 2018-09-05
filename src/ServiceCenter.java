import entities.*;
import entities.dictionary.DeviceType;
import entities.dictionary.Manufacturer;
import entities.dictionary.SparePart;

import java.sql.SQLException;
import java.util.Collection;

public class ServiceCenter {

    private DaoHelper dao = DaoHelper.getInstance();

    public int createClient(String fio, String phone, String url, String note) throws SQLException {
        Client client = new Client(fio, phone, url, note);
        return dao.CLIENT_DAO.create(client);
    }

    public int createDevice(DeviceType type, Manufacturer manufacturer, String model, String notes, String condition) throws SQLException {
        Device device = new Device(type, manufacturer, model, notes, condition);
        return dao.DEVICE_DAO.create(device);
    }

    public int createOrder(int clientId, int deviceId, Collection<Integer> defectIds, Collection<Integer> equipmentIds) throws SQLException {
        Client client = getClient(clientId);
        Device device = getDevice(deviceId);
        Order order = new Order(client, device);
        int orderId = dao.ORDER_DAO.create(order);

        for(Integer defectId: defectIds){
            dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(defectId.toString())));
        }
        for(Integer equipmentId: equipmentIds){
            dao.EQUIPMENT_DAO.create(new Equipment(order, dao.EQUIPMENT_PART_DAO.queryForId(equipmentId.toString())));
        }

        return orderId;
    }

    public void addSpareToOrder(Order order, SparePart sparePart) throws SQLException {
        dao.ORDER_SPARE_DAO.create(new OrderSpare(order, sparePart));
    }

    public void addDefectToOrder(Order order, int defectId) throws SQLException {
        dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(Integer.toString(defectId))));
    }

    public void addEquipmentToOrder(Order order, int defectId) throws SQLException {
        dao.EQUIPMENT_DAO.create(new Equipment(order, dao.EQUIPMENT_PART_DAO.queryForId(Integer.toString(defectId))));
    }

    public void addJobToOrder(Order order, int defectId) throws SQLException {
        dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(Integer.toString(defectId))));
    }

    public Client getClient(int id) throws SQLException {
        return dao.CLIENT_DAO.queryForId(Integer.toString(id));
    }

    public Device getDevice(int id) throws SQLException {
        return dao.DEVICE_DAO.queryForId(Integer.toString(id));
    }

    public Order getOrder(int id) throws SQLException {
        return dao.ORDER_DAO.queryForId(Integer.toString(id));
    }

}
