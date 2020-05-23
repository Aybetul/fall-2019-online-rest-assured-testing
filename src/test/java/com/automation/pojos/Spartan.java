package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/*
this class represents spartan  pojo
 */
public class Spartan {
private int id;
private  String name;
private  String gender; // json and java have to have same name;
@SerializedName("phone") // in case not matching we use this annotation
private long phoneNumber ;


public Spartan( String name, String gender, long phoneNumber){
   this.name=name;
   this.gender=gender;
   this.phoneNumber=phoneNumber;
}

    public Spartan( int id, String name, String gender, long phoneNumber){
        this.id=id;
       this.name=name;
        this.gender=gender;
        this.phoneNumber=phoneNumber;
    }
public  Spartan(){

}
    public int getId() {
        return id;
    }

    public String getName() {
      return name;
    }

    public String getGender() {
        return gender;
    }

    public long getPhoneNumber() {
        return phoneNumber;
}

    @Override
    public String toString() {
        return "Spartan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spartan spartan = (Spartan) o;
        return id == spartan.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  gender, phoneNumber);
    }
}
