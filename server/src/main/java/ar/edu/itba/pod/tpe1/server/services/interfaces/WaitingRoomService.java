
package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Patient;

public interface WaitingRoomService {

    Patient addPatient(String name, int level);

    Patient updateLevel(String patientName, Integer level);

    Integer checkPatient(String patientName);

    Patient getPatient(String name);

}
