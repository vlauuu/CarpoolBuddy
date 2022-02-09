package com.example.carpoolbuddy;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * It creates a user with suitable params
 *
 * @author Vico Lau
 * @version 0.1
 */

public class User {

    private String uid;
    private String name;
    private String email;
    private String userType;
    private double priceMultiplier;
    private ArrayList<String> ownedVehicles;
    private String password;

    public User(String uid,
                String name,
                String email,
                String userType,
                double priceMultiplier,
                String password,
                ArrayList<String> ownedVehicles) {

        this.email = email;
        this.name = name;
        this.uid = uid;
        this.userType = userType;
        this.priceMultiplier = priceMultiplier;
        this.password = password;
        this.ownedVehicles = ownedVehicles;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public ArrayList<String> getOwnedVehicles() {
        return ownedVehicles;
    }

    public void setOwnedVehicles(ArrayList<String> ownedVehicles)
    {
        this.ownedVehicles = ownedVehicles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
