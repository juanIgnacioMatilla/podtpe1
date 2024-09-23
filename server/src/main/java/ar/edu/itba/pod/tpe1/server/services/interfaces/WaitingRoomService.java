
package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Patient;

public interface WaitingRoomService {

    boolean addPatient(Patient patient);

    boolean updateLevel(String name, Integer level);

    Integer checkPatient(String name);

}
