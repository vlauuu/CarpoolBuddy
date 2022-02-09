package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a teacher to the database if the user type is
 * teacher
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Teacher extends User{

    private String inSchoolTitle;

    public Teacher(String uid,
                   String name,
                   String email,
                   String userType,
                   double priceMultiplier,
                   String password,
                   ArrayList<String> ownedVehicles,
                   String inSchoolTitle) {

        super(uid, name, email, userType, priceMultiplier, password, ownedVehicles);
        this.inSchoolTitle = inSchoolTitle;

    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }
}
