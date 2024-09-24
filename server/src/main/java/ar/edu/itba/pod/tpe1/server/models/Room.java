package ar.edu.itba.pod.tpe1.server.models;

public class Room {
    private final Integer id;
    private Boolean occupied;
    private Doctor doctor;

    private Patient patient;

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Room(Integer id) {
        this.id = id;
        this.occupied = false;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    @Override
    public String toString() {
        return "Room #" + this.getId();
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
