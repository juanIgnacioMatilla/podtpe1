package ar.edu.itba.pod.tpe1.server.models;

import java.security.Timestamp;
import java.time.*;
import java.sql.Time;
import java.time.Instant;
import java.util.Objects;

public class Patient implements Comparable<Patient> {
    private String name;
    private Integer emergencyLevel;
    private LocalDateTime timeCreated;

    public Patient(String name, Integer emergencyLevel) {
        this.name = name;
        this.emergencyLevel = emergencyLevel;
        timeCreated = LocalDateTime.now();
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
    
    public LocalDateTime getTimeCreated() {
        return timeCreated;
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

    @Override
    public int compareTo(Patient other){
        if(this.getEmergencyLevel().compareTo(other.getEmergencyLevel()) != 0)
            return this.getEmergencyLevel().compareTo(other.getEmergencyLevel());
        return this.getTimeCreated().compareTo(other.getTimeCreated());
    }
}
