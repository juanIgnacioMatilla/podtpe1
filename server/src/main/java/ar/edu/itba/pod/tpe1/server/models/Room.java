package ar.edu.itba.pod.tpe1.server.models;

public class Room {
    private Integer id;
    private Boolean occupied;

    public Room(Integer id) {
        this.id = id;
        this.occupied = false;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    @Override
    public String toString() {
        return "Room #" + this.getId();
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
