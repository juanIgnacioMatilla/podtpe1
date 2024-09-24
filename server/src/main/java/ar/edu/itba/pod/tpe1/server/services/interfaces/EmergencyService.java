package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;

/**
 * EmergencyService
 */
public interface EmergencyService {

    CareHistory carePatient(Room room);

    void careAllPatients();

    boolean dischargePatient(Room room, Doctor doctor, Patient patient);

}
