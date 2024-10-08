
package ar.edu.itba.pod.tpe1.server.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.CareHistory;

/**
 * QueryService
 */
public interface QueryService {

    List<Room> queryRooms();/* Nodev */

    List<Patient> queryWaitingRoom();

    List<CareHistory> queryCares(Integer room);

}
