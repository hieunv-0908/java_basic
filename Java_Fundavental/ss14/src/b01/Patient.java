package b01;

import java.util.Objects;

public class Patient {
    private String name;
    private String countryside;

    public Patient(String name, String countryside) {
        this.name = name;
        this.countryside = countryside;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return countryside;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Patient)) return false;
        Patient patient = (Patient) obj;
        return name.equals(patient.name) && countryside.equals(patient.countryside);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, countryside);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", countryside='" + countryside + '\'' +
                '}';
    }
}
