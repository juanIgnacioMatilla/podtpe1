package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Doctor;

/**
 * InnerAdminServicd
 */
public interface AdminService {

    void addRoom();

    boolean addDoctor(String name, Integer maxLevel);

    boolean setDoctor(String name, Doctor.Status availability);

    Doctor.Status checkDoctor(String name);

}
