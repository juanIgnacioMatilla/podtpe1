
package ar.edu.itba.pod.tpe1.server.services;

/**
 * QueryService
 */
public interface QueryService {

  boolean queryRooms();/* Nodev*/

  boolean queryWaitingRoom();

  boolean queryCares(Integer room);

}
