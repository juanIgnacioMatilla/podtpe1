package ar.edu.itba.pod.tpe1.server.models;

import java.util.Objects;

public class Patient {
    private String name;
    private Integer emergencyLevel;

    public Patient(String name, Integer emergencyLevel) {
        this.name = name;
        this.emergencyLevel = emergencyLevel;
    }

    public String getName() {
        return name;
    }

    public Integer getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(Integer emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    @Override
    public String toString() {
        return "Patient " + this.getName() + " (" + this.getEmergencyLevel() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Patient))
            return false;
        Patient other = (Patient) o;
        return this.getName().compareTo(other.getName()) == 0;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name);
    }
}
