package db;

import com.j256.ormlite.stmt.QueryBuilder;
import entities.*;
import entities.dictionary.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ServiceCenter {

    private DaoHelper dao = DaoHelper.getInstance();

    public int createClient(String fio, String phone, String url, String note) throws SQLException {
        Client client = new Client(fio, phone, url, note);
        dao.CLIENT_DAO.create(client);
        return  client.getId();
    }

    public int createDevice(DeviceType type, Manufacturer manufacturer, String model, String notes, String condition) throws SQLException {
        Device device = new Device(type, manufacturer, model, notes, condition);
        dao.DEVICE_DAO.create(device);
        return device.getId();
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

        dao.ORDER_STATUS_DAO.create(new OrderStatus(order, dao.STATUS_DAO.queryForId("1"), new Date()));

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

    public List<Order> getAllOrders() throws SQLException {
        return dao.ORDER_DAO.queryForAll();
    }

    public void setNewEquipments(Order order, String equipmentsString) throws SQLException {
        String[] stringEquipments = equipmentsString.split("\n");
        List<EquipmentPart> equipments = new ArrayList<>(stringEquipments.length);
        for (String stringEquipment : stringEquipments) {
            QueryBuilder<EquipmentPart, String> queryBuilder = dao.EQUIPMENT_PART_DAO.queryBuilder();
            queryBuilder.where().eq("name", stringEquipment);
            EquipmentPart equipment = dao.EQUIPMENT_PART_DAO.iterator(queryBuilder.prepare()).first();
            equipments.add(equipment);
        }
        for(EquipmentPart equipmentPart: equipments){
            QueryBuilder<Equipment, String> queryBuilder = dao.EQUIPMENT_DAO.queryBuilder();
            queryBuilder.where().eq("order", order.getId()).and().eq("equipmentPart", equipmentPart.getId());
            if (dao.EQUIPMENT_DAO.iterator(queryBuilder.prepare()) == null) {
                dao.EQUIPMENT_DAO.create(new Equipment(order, dao.EQUIPMENT_PART_DAO.queryForId(String.valueOf(equipmentPart.getId()))));
            }
        }

    }

}
