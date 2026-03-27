package model;

public class Machine {
    private String machineId;
    private String machineCode;
    private Area area;
    private String config;
    private double pricePerHour;
    private StatusMachine statusMachine;

    status         ENUM ('AVAILABLE','IN_USE','MAINTENANCE')
}
