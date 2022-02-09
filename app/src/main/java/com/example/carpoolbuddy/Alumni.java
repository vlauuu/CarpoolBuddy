package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds the graduation year if the user type is alumni
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Alumni extends User{

    private String graduateYear;

    public Alumni(String uid,
                  String name,
                  String email,
                  String userType,
                  double priceMultiplier,
                  String password,
                  ArrayList<String> ownedVehicles,
                  String graduateYear) {

        super(uid, name, email, userType, priceMultiplier, password, ownedVehicles);
        this.graduateYear = graduateYear;

    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

}
