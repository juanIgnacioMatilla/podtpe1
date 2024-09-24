package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import ar.edu.itba.pod.tpe1.server.models.Doctor;

public class DoctorRepository {
    private Set<Doctor> doctors;

    public DoctorRepository() {
        doctors = Collections.synchronizedSet(new HashSet<>());
    }

    // set devuelve false si el doctor ya estaba y no lo agrega.
    public Boolean addDoctor(Doctor doctor) {
        return doctors.add(doctor);
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Optional<Doctor> getDoctor(Doctor doctor) {
        return doctors.stream()
                .filter(d -> d.equals(doctor))
                .findFirst();
    }

    public Optional<Doctor> registerDoctorNotif(Doctor doctor) {
        return doctors.stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    d.setPageable(true);
                    return d;
                });
    }

    public Optional<Doctor> unregisterDoctorNotif(Doctor doctor) {
        return doctors.stream()
                .filter(d -> d.equals(doctor))
                .findFirst()
                .map(d -> {
                    d.setPageable(false);
                    return d;
                });
    }

    public void setAttending(Doctor toAttend) {
        doctors.stream()
                .filter(d -> d.equals(toAttend))
                .findFirst()
                .map(d -> {
                    d.setStatus(Doctor.Status.ATTENDING);
                    return d;
                });
    }
}
