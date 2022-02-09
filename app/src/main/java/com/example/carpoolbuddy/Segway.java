package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a segway to the database if the vehicle type is
 * segway
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Segway extends Vehicle {

    private int range;
    private int weightCapacity;

    public Segway(int capacity,
                  String model,
                  boolean open,
                  String owner,
                  int rating) {

        super(capacity, model, open, owner, rating);

        this.range = range;
        this.weightCapacity = weightCapacity;

    }
}
