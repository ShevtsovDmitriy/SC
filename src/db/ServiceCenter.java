package db;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import entities.*;
import entities.dictionary.DeviceType;
import entities.dictionary.EquipmentPart;
import entities.dictionary.Manufacturer;
import entities.dictionary.SparePart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceCenter {

    private DaoHelper dao = DaoHelper.getInstance();

    public int createClient(String fio, String phone, String url, String note) throws SQLException {
        Client client = new Client(fio, phone, url, note);
        dao.CLIENT_DAO.create(client);
        return  client.getId();
    }

    public void updateClient(Client client) throws SQLException {
        dao.CLIENT_DAO.update(client);
    }

    public int createDevice(DeviceType type, Manufacturer manufacturer, String model, String notes, String condition) throws SQLException {
        Device device = new Device(type, manufacturer, model, notes, condition);
        dao.DEVICE_DAO.create(device);
        return device.getId();
    }

    public void updateDevice(Device device) throws SQLException {
        dao.DEVICE_DAO.update(device);
    }

    public List<Device> getAllDevisesForClient(Client client) throws SQLException {
        QueryBuilder<Order, String> queryBuilder = dao.ORDER_DAO.queryBuilder();
        queryBuilder.where().eq("client", client.getId());
        List<Order> clientOrders = dao.ORDER_DAO.query(queryBuilder.prepare());
        List<Device> clientDevices = new ArrayList<>(clientOrders.size());
        clientOrders.forEach(e -> {
            if(!clientDevices.contains(e.getDevice())){
                clientDevices.add(e.getDevice());
            }});
        return clientDevices;
    }

    public Order createOrder(int clientId, int deviceId, Collection<Integer> defectIds, Collection<Integer> equipmentIds) throws SQLException {
        Client client = getClient(clientId);
        Device device = getDevice(deviceId);
        Order order = new Order(client, device);
        dao.ORDER_DAO.create(order);

        for(Integer defectId: defectIds){
            dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(defectId.toString())));
        }
        for(Integer equipmentId: equipmentIds){
            dao.EQUIPMENT_DAO.create(new Equipment(order, dao.EQUIPMENT_PART_DAO.queryForId(equipmentId.toString())));
        }

        return order;
    }

    public void updateOrder(Order order) throws SQLException {
        dao.ORDER_DAO.update(order);
    }

    public void addSpareToOrder(Order order, SparePart sparePart) throws SQLException {
        dao.ORDER_SPARE_DAO.create(new OrderSpare(order, sparePart));
    }

    public void addDefectToOrder(Order order, int defectId) throws SQLException {
        dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(Integer.toString(defectId))));
    }

    public void addEquipmentToOrder(Order order, int equipmentId) throws SQLException {
        dao.EQUIPMENT_DAO.create(new Equipment(order, dao.EQUIPMENT_PART_DAO.queryForId(Integer.toString(equipmentId))));
    }

    public void addJobToOrder(Order order, int jobId) throws SQLException {
        dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(Integer.toString(jobId))));
    }

    public void addStatusesToOrder(Order order, List<OrderStatus> statuses) throws SQLException {
        List<Integer> statusIds = new ArrayList<>(statuses.size());
        statuses.forEach(e -> statusIds.add(e.getStatus().getId()));
        DeleteBuilder<OrderStatus, String> deleteBuilder = dao.ORDER_STATUS_DAO.deleteBuilder();
        deleteBuilder.where().eq("order", order.getId()).and().notIn("status", statusIds);
        deleteBuilder.delete();

        for(OrderStatus status: statuses){
            QueryBuilder<OrderStatus, String> queryBuilder = dao.ORDER_STATUS_DAO.queryBuilder();
            queryBuilder.where().eq("order", order.getId()).and().eq("status", status.getStatus().getId());
            if (dao.ORDER_STATUS_DAO.iterator(queryBuilder.prepare()).first() == null) {
                dao.ORDER_STATUS_DAO.create(new OrderStatus(order, dao.STATUS_DAO.queryForId(String.valueOf(status.getStatus().getId())), status.getDate()));
            }
        }

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

    public void setNewEquipments(Order order, List<EquipmentPart> equipmentParts) throws SQLException {
        List<Integer> equipmentPartIds = new ArrayList<>(equipmentParts.size());
        equipmentParts.forEach(e -> equipmentPartIds.add(e.getId()));
        DeleteBuilder<Equipment, String> deleteBuilder = dao.EQUIPMENT_DAO.deleteBuilder();
        deleteBuilder.where().eq("order", order.getId()).and().notIn("equipmentPart", equipmentPartIds);
        deleteBuilder.delete();

        for(EquipmentPart equipmentPart: equipmentParts){
            QueryBuilder<Equipment, String> queryBuilder = dao.EQUIPMENT_DAO.queryBuilder();
            queryBuilder.where().eq("order", order.getId()).and().eq("equipmentPart", equipmentPart.getId());
            if (dao.EQUIPMENT_DAO.iterator(queryBuilder.prepare()).first() == null) {
                dao.EQUIPMENT_DAO.create(new Equipment(order, dao.EQUIPMENT_PART_DAO.queryForId(String.valueOf(equipmentPart.getId()))));
            }
        }
    }


    public void setNewDefects(Order order, String defectsString) throws SQLException {
        List<Integer> defectIds = DictionaryHelper.getInstance().getDefects(defectsString);

        DeleteBuilder<DeviceDefect, String> deleteBuilder = dao.DEVICE_DEFECT_DAO.deleteBuilder();
        deleteBuilder.where().eq("order", order.getId()).and().notIn("defect", defectIds);
        deleteBuilder.delete();

        for(Integer defectId: defectIds){
            QueryBuilder<DeviceDefect, String> queryBuilder = dao.DEVICE_DEFECT_DAO.queryBuilder();
            queryBuilder.where().eq("order", order.getId()).and().eq("defect", defectId);
            if (dao.DEVICE_DEFECT_DAO.iterator(queryBuilder.prepare()).first() == null) {
                dao.DEVICE_DEFECT_DAO.create(new DeviceDefect(order, dao.DEFECT_DAO.queryForId(defectId.toString())));
            }
        }


    }


}
