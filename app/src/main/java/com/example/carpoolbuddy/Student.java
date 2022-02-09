package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a parent to the database if the user type is
 * parent
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Student extends User
{
    private String graduatingYear;
    private ArrayList<String> parentUIDS = new ArrayList<>();

    public Student(String uid,
                   String name,
                   String email,
                   String userType,
                   double priceMultiplier,
                   String password,
                   ArrayList<String> ownedVehicles,
                   String graduatingYear,
                   ArrayList<String> parentUIDS)
    {
        super(uid, name, email, userType, priceMultiplier, password, ownedVehicles);
        this.graduatingYear = graduatingYear;
        this.parentUIDS = parentUIDS;

    }

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    public ArrayList<String> getParentUIDS() {
        return parentUIDS;
    }

    public void setParentUIDS(ArrayList<String> parentUIDS) {
        this.parentUIDS = parentUIDS;
    }
}

