
package ar.edu.itba.pod.tpe1.server.services;

public interface WaitingRoomService {

  boolean addPatient(String name, Integer level);

  boolean updateLevel(String name, Integer level);

  Integer checkPatient(String name);


}
