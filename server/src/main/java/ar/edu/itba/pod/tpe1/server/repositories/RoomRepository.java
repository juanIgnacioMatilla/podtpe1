package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Doctor;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;

public class RoomRepository {
    Set<Room> rooms;

    public RoomRepository() {
        rooms = Collections.synchronizedSet(new HashSet<>());
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Room addRoom() {
        Room aux = new Room(rooms.size() + 1);
        rooms.add(aux);
        return aux;
    }

    public Optional<Room> updateRoom(Integer roomId, Patient patient, Doctor doctor, Boolean occupied) {
        return rooms.stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .map(r -> {
                    if(r.getOccupied()) {
                        if(!r.getDoctor().equals(doctor))
                            throw new RuntimeException("Doctor was not attending emergency in that room");
                    }
                    r.setDoctor(doctor);
                    r.setPatient(patient);
                    r.setOccupied(occupied);
                    return r;
                });
    }
}
