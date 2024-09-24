package ar.edu.itba.pod.tpe1.server.models;

public class CareHistory {
    private final Doctor doctor;
    private final Patient patient;
    private final Integer roomId;

    public CareHistory(Doctor doctor, Patient patient, Integer roomId) {
        this.doctor = doctor;
        this.patient = patient;
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
}
