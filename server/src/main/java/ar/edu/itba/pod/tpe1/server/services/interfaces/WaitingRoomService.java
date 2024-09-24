
package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Patient;

public interface WaitingRoomService {

    Patient addPatient(Patient patient);

    Patient updateLevel(Patient patient, Integer level);

    Integer checkPatient(Patient patient);

}
