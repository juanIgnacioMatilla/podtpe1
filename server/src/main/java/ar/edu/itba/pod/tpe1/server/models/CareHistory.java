package ar.edu.itba.pod.tpe1.server.models;

public class CareHistory {
    private final Doctor doctor;
    private final Patient patient;
    private final Room room;

    public CareHistory(Doctor doctor, Patient patient, Room room) {
        this.doctor = doctor;
        this.patient = patient;
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
}
