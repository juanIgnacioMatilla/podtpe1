package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Doctor;

/**
 * InnerAdminServicd
 */
public interface AdminService {

    void addRoom();

    Doctor addDoctor(String name, Integer maxLevel);

    Doctor setDoctor(Doctor doctor, Doctor.Status availability);

    Doctor.Status checkDoctor(Doctor doctor);

}
