package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    public void setUnavailable(Room room) {
        rooms.stream()
                .filter(r -> r.equals(room))
                .findFirst()
                .map(r -> {
                    r.setOccupied(true);
                    return r;
                });
    }
}
