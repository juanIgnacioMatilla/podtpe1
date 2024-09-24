package ar.edu.itba.pod.tpe1.server.services.interfaces;

import ar.edu.itba.pod.tpe1.server.models.Doctor;

/**
 * NotificationService
 */
public interface NotificationService {
    Doctor register(Doctor doctor);

    Doctor unregister(Doctor doctor);
}
