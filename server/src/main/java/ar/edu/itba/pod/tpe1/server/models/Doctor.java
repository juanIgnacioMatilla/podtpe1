package ar.edu.itba.pod.tpe1.server.models;

import models.doctor.DoctorOuterClass;

import java.util.Objects;

public class Doctor implements Comparable<Doctor> {
    private String name;
    private Integer level;
    private DoctorOuterClass.DoctorStatus status;
    private Boolean pageable;

    public Doctor(String name, Integer level) {
        this.name = name;
        this.level = level;
        this.status = DoctorOuterClass.DoctorStatus.UNAVAILABLE;
        this.pageable = false;
    }

    public String getName() {
        return name;
    }

    public Integer getLevel() {
        return level;
    }

    public DoctorOuterClass.DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorOuterClass.DoctorStatus status) {
        this.status = status;
    }

    public void setPageable(Boolean pageable) {
        this.pageable = pageable;
    }

    public Boolean getPageable() {
        return pageable;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean canCare(Integer emergencyLevel) {
        return (this.getStatus() == DoctorOuterClass.DoctorStatus.AVAILABLE) 
                && (this.getLevel() >= emergencyLevel);
    }

    @Override
    public String toString() {
        return "Doctor " + this.getName() + " (" + this.getLevel() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Doctor))
            return false;
        Doctor other = (Doctor) o;
        return this.getName().compareTo(other.getName()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
    
    @Override
    public int compareTo(Doctor other){
        int nameComparison = this.getName().compareTo(other.getName());
        if (nameComparison == 0) {
            return 0;
        }
        if(this.getLevel().compareTo(other.getLevel()) != 0)
            return this.getLevel().compareTo(other.getLevel());
        return this.getName().compareTo(other.getName());
    }
}
