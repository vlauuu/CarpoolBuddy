package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a parent to the database if the user type is
 * parent
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Parent extends User{

    private ArrayList<String> childrenUIDS = new ArrayList<>();

    public Parent(String uid,
                  String name,
                  String email,
                  String userType,
                  double priceMultiplier,
                  String password,
                  ArrayList<String> ownedVehicles,
                  ArrayList childrenUIDS) {

        super(uid, name, email, userType, priceMultiplier, password, ownedVehicles);
        this.childrenUIDS = childrenUIDS;

    }

    public ArrayList<String> getChildrenUIDS()
    {
        return childrenUIDS;
    }

    public void setChildrenUIDS(ArrayList<String> childrenUIDS)
    {
        this.childrenUIDS = childrenUIDS;
    }
}
