package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a car to the database if the vehicle type is
 * car
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Car extends Vehicle{

    private ArrayList<String> reservedUids = new ArrayList<String>();

    public Car(int capacity,
               String model,
               boolean open,
               String owner,
               int rating,
               ArrayList reservedUids) {

        super(capacity, model, open, owner, rating);

        this.reservedUids = reservedUids;
    }

    @Override
    public ArrayList<String> getReservedUids() {
        return reservedUids;
    }
}
