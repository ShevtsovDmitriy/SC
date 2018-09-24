package controllers;

import entities.Client;
import entities.Device;
import entities.dictionary.Job;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationController {
    private static OrderCreationController ourInstance = new OrderCreationController();

    public static OrderCreationController getController() {
        return ourInstance;
    }

    private OrderCreationController() {
    }

    public void clearAll(){
        ourInstance = new OrderCreationController();
    }

    private List<Integer> equipments;
    private Client client;
    private Device device;
    private List<Job> jobs;

    public List<Integer> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Integer> equipments) {
        this.equipments = equipments;
    }

    public void addEquipment(int equipment){
        if(equipments == null){
            equipments = new ArrayList<>(5);
        }
        equipments.add(equipment);
    }

    public void removeEquipment(int equipment){
        equipments.removeIf(e -> e.equals(Integer.valueOf(equipment)));
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job){
        if (jobs == null){
            jobs = new ArrayList<>();
        }
        jobs.add(job);
    }
}
