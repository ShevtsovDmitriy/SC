package db;

import com.j256.ormlite.stmt.QueryBuilder;
import entities.dictionary.Defect;
import entities.dictionary.DeviceType;
import entities.dictionary.EquipmentPart;
import entities.dictionary.Manufacturer;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private List<Defect> defects;

    private DaoHelper dao = DaoHelper.getInstance();

    /* public methods */
    public List<DeviceType> getDeviceTypes() throws SQLException {
        return deviceTypes == null?deviceTypes = dao.DEVICE_TYPE_DAO.queryForAll():deviceTypes;
    }

    public List<Manufacturer> getManufacturers() throws SQLException {
        return manufacturers == null?manufacturers = dao.MANUFACTURER_DAO.queryForAll():manufacturers;
    }

    public List<Defect> getDefects() throws SQLException {
        return defects == null?defects = dao.DEFECT_DAO.queryForAll():defects;
    }

    public List<Defect> getDefects(String defectsString) throws SQLException {
        String[] stringDefects = defectsString.split("\n");
        List<Defect> defects = new ArrayList<>(stringDefects.length);
        for (String stringDefect : stringDefects) {
            QueryBuilder<Defect, String> queryBuilder = dao.DEFECT_DAO.queryBuilder();
            queryBuilder.where().eq("name", stringDefect);
            Defect defect = dao.DEFECT_DAO.iterator(queryBuilder.prepare()).first();
            defects.add(defect != null ? defect : createDefect(stringDefect));
        }
        return defects;
    }

    public List<EquipmentPart> getAllEquipmentParts() throws SQLException {
        return dao.EQUIPMENT_PART_DAO.queryForAll();
    }

    public List<EquipmentPart> getEquipmentParts(List<Integer> parts) throws SQLException {
        QueryBuilder<EquipmentPart, String> queryBuilder = dao.EQUIPMENT_PART_DAO.queryBuilder();
        queryBuilder.where().in("id", parts);
        return dao.EQUIPMENT_PART_DAO.query(queryBuilder.prepare());
    }

    /* private methods */
    private Defect createDefect(String defectName) throws SQLException {
        Defect defect = new Defect(defectName);
        dao.DEFECT_DAO.create(defect);
        return defect;
    }
}

