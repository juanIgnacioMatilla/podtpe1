package ar.edu.itba.pod.tpe1.server.services.interfaces;

import java.util.List;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;

/**
 * EmergencyService
 */
public interface EmergencyService {

    Room carePatient(Integer roomId);

    void careAllPatients();

    Room dischargePatient(Integer roomId, String doctorName, String patientName);

}
