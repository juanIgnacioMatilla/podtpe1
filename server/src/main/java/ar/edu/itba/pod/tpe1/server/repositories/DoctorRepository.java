package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Collections;
import java.util.HashSet;
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

    public Doctor getDoctor(String name) {
        Doctor out = null;
        for (Doctor d : doctors) {
            if (d.getName().compareTo(name) == 0)
                out = d;
        }
        return out;
    }

    public Boolean registerDoctorNotif(Doctor doctor) {
        for (Doctor d : doctors) {
            if (d.equals(doctor)) {
                d.setPageable(true);
                return true;
            }
        }
        return false;
    }

    public Boolean unregisterDoctorNotif(Doctor doctor) {
        for (Doctor d : doctors) {
            if (d.equals(doctor)) {
                d.setPageable(false);
                return true;
            }
        }
        return false;
    }

    public void setAttending(Doctor toAttend) {
        for (Doctor doctor : doctors) {
            if (doctor.equals(toAttend))
                doctor.setStatus(Doctor.Status.ATTENDING);
        }
    }
}
