package db;

import com.j256.ormlite.stmt.QueryBuilder;
import entities.dictionary.*;

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
    private List<Status> statuses;

    private DaoHelper dao = DaoHelper.getInstance();

    /* public methods */
    public List<DeviceType> getDeviceTypes() throws SQLException {
        return deviceTypes == null?deviceTypes = dao.DEVICE_TYPE_DAO.queryForAll():deviceTypes;
    }

    public DeviceType getDeviceType(int id){
        return deviceTypes.get(id);
    }

    public int getDeviceTypeIndex(DeviceType deviceType){
        for(int  i = 0; i < deviceTypes.size(); i++){
            if (deviceTypes.get(i).getId() == deviceType.getId()){
                return i;
            }
        }
        return -1;
    }

    public List<Manufacturer> getManufacturers() throws SQLException {
        return manufacturers == null?manufacturers = dao.MANUFACTURER_DAO.queryForAll():manufacturers;
    }

    public Manufacturer getManufacturer(int id){
        return manufacturers.get(id);
    }

    public int getManufacturerIndex(Manufacturer manufacturer){
        for(int  i = 0; i < manufacturers.size(); i++){
            if (manufacturers.get(i).getId() == manufacturer.getId()){
                return i;
            }
        }
        return -1;
    }

    public List<Defect> getDefects() throws SQLException {
        return defects == null?defects = dao.DEFECT_DAO.queryForAll():defects;
    }

    public List<Integer> getDefects(String defectsString) throws SQLException {
        String[] stringDefects = defectsString.split("\n");
        List<Integer> defects = new ArrayList<>(stringDefects.length);
        for (String stringDefect : stringDefects) {
            QueryBuilder<Defect, String> queryBuilder = dao.DEFECT_DAO.queryBuilder();
            queryBuilder.where().eq("name", stringDefect);
            Defect defect = dao.DEFECT_DAO.iterator(queryBuilder.prepare()).first();
            defects.add(defect != null ? defect.getId() : createDefect(stringDefect).getId());
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

    public List<Status> getStatuses() throws SQLException {
        return statuses == null?statuses = dao.STATUS_DAO.queryForAll():statuses;
    }

    public Status getStatus(int index){
        return statuses.get(index);
    }

    public Status getAcceptedStatus(){
        for (Status status: statuses){
            if ("Принят".equals(status.getName())){
                return status;
            }
        }
        return  statuses.iterator().next();
    }

    public Status getInServiceStatus(){
        for (Status status: statuses){
            if ("В ремонте".equals(status.getName())){
                return status;
            }
        }
        return  statuses.iterator().next();
    }

    public Status getReadyStatus(){
        for (Status status: statuses){
            if ("Готово".equals(status.getName())){
                return status;
            }
        }
        return  statuses.iterator().next();
    }

    public Status getOutStatus(){
        for (Status status: statuses){
            if ("Выдан".equals(status.getName())){
                return status;
            }
        }
        return  statuses.iterator().next();
    }

    public int getStatusIndex(Status status){
        for(int  i = 0; i < statuses.size(); i++){
            if (statuses.get(i).getId() == status.getId()){
                return i;
            }
        }
        return -1;
    }

    public List<Job> getAllJobs() throws SQLException {
        return dao.JOB_DAO.queryForAll();
    }

    public List<Job> getJobsOfCategory(String category) throws SQLException {
        QueryBuilder<Job, String> queryBuilder = dao.JOB_DAO.queryBuilder();
        queryBuilder.where().like("category", category + "%");
        return dao.JOB_DAO.query(queryBuilder.prepare());
    }

    public void addJob(Job job) throws SQLException {
        dao.JOB_DAO.create(job);
    }

    public void removeJob(Job job) throws SQLException {
        dao.JOB_DAO.delete(job);
    }

    /* private methods */
    private Defect createDefect(String defectName) throws SQLException {
        Defect defect = new Defect(defectName);
        dao.DEFECT_DAO.create(defect);
        return defect;
    }
}

