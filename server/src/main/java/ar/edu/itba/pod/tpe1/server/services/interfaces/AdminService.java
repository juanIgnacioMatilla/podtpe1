package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Doctor;
import models.doctor.DoctorOuterClass;

/**
 * InnerAdminServicd
 */
public interface AdminService {

    int addRoom();

    Doctor addDoctor(String name, Integer maxLevel);

    Doctor setDoctor(String doctorName, DoctorOuterClass.DoctorStatus availability);

    Doctor getDoctor(String name);

}
