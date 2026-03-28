package model;

public class Machine {
    private String machineId;
    private String machineCode;
    private Area area;
    private String config;
    private double pricePerHour;
    private StatusMachine statusMachine;

    public Machine(String machineId, String machineCode, Area area, String config, double pricePerHour, StatusMachine statusMachine) {
        this.machineId = machineId;
        this.machineCode = machineCode;
        this.area = area;
        this.config = config;
        this.pricePerHour = pricePerHour;
        this.statusMachine = statusMachine;
    }

    public Machine() {
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public StatusMachine getStatusMachine() {
        return statusMachine;
    }

    public void setStatusMachine(StatusMachine statusMachine) {
        this.statusMachine = statusMachine;
    }
}
