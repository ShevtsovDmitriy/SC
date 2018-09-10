package db;

import entities.dictionary.DeviceType;
import entities.dictionary.Manufacturer;

import java.sql.SQLException;
import java.util.List;

public class DictionaryHelper {

    private static DictionaryHelper ourInstance = new DictionaryHelper();

    public static DictionaryHelper getInstance() {
        return ourInstance;
    }

    private DictionaryHelper() {
    }

    private List<DeviceType> deviceTypes;
    private List<Manufacturer> manufacturers;

    DaoHelper dao = DaoHelper.getInstance();

    public List<DeviceType> getDeviceTypes() throws SQLException {
        return deviceTypes == null?deviceTypes = dao.DEVICE_TYPE_DAO.queryForAll():deviceTypes;
    }

    public List<Manufacturer> getManufacturers() throws SQLException {
        return manufacturers == null?manufacturers = dao.MANUFACTURER_DAO.queryForAll():manufacturers;
    }


}
