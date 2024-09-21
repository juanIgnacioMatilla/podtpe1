package ar.edu.itba.pod.tpe1.server.models;

import java.util.Objects;

public class Doctor{
    private String name;
    private Integer maxEmergencies;
    private Boolean available; //hay que ver si usamos el boolean porque tiene 3 estados en realidad
    private Boolean pageable;

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

    public Boolean getAvailable(){
        return available;
    }

    public Boolean getPageable(){
        return pageable;
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
}
