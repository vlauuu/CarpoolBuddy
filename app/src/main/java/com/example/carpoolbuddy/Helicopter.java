package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a helicopter to the database if the vehicle type is
 * helicopter
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Helicopter extends Vehicle{

    private int maxAltitude;
    private int maxAirSpeed;

    public Helicopter(int capacity,
                      String model,
                      boolean open,
                      String owner,
                      int rating) {
        super(capacity, model, open, owner, rating);

        this.maxAltitude = maxAltitude;
        this.maxAirSpeed = maxAirSpeed;
    }

}
