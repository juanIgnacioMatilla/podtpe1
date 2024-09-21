

package ar.edu.itba.pod.tpe1.server.services;

/**
 * EmergencyService
 */
public interface EmergencyService {

  boolean carePatient(Integer room);

  void careAllPatients();

  boolean dischargePatient(Integer room,String doctor,String patient);

}
