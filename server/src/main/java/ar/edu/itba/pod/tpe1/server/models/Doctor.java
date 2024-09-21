package ar.edu.itba.pod.tpe1.server.models;

import java.util.Objects;

public class Doctor{
    private String name;
    private Integer maxEmergencies;
    private Status status;

    public Doctor(String name,Integer maxEmergencies){
        this.name = name;
        this.maxEmergencies = maxEmergencies;
    }

    public String getName(){
        return name;
    }

    public Integer getMaxEmergencies(){
        return maxEmergencies;
    }

    public Status getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return "Doctor " + this.getName() + " (" + this.getMaxEmergencies() + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Doctor))
            return false;
        Doctor other = (Doctor) o;
        return this.getName().compareTo(other.getName()) == 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getName());
    }


    public enum Status {
        AVAILABLE,
        UNAVAILABLE,
        ATTENDING;

        public static Status fromString(String status) {
            switch (status.toUpperCase()) {
                case "AVAILABLE":
                    return AVAILABLE;
                case "UNAVAILABLE":
                    return UNAVAILABLE;
                case "ATTENDING":
                    return ATTENDING;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        @Override
        public String toString() {
            return name();
        }
    }


}
