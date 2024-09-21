package ar.edu.itba.pod.tpe1.server.models;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class EmergencyRoom{
    private Set<Doctor> doctors = new TreeSet<>();
    private Queue<ArrayList<Patient>> patients = new PriorityQueue<>();
    private Set<Room> rooms = new TreeSet<>();
    
    public EmergencyRoom(){

    }
    
    public Set<Room> getRooms(){
        return rooms;
    }

    public Set<Doctor> getDoctors(){
        return doctors;
    }

    public Queue<ArrayList<Patient>> getPatients(){
        return patients;
    }
}
