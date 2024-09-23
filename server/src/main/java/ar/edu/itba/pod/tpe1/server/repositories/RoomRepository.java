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

    public Boolean addRoom() {
        Room room = new Room(rooms.size() + 1);
        return rooms.add(room);
    }

    public void setUnavailable(Room room) {
        for (Room r : rooms) {
            if (r.equals(room))
                r.setOccupied(true);
        }
    }
}
