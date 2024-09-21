
package ar.edu.itba.pod.tpe1.server.services;

import ar.edu.itba.pod.tpe1.server.models.Availability;
/**
 * InnerAdminServicd
 */
public interface AdminService{


    void addRoom();

    boolean addDoctor(String name, Integer maxLevel);
    
    boolean setDoctor(String name, String availability);

    Availability checkDoctor(String name);



}
