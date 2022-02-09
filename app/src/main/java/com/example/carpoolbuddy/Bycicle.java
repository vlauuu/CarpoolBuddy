package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * This adds additional information to add a bicycle to the database if the vehicle type is
 * bicycle
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Bycicle extends Vehicle {

    private String bycicleType;
    private int weight;
    private int weightCapacity;

    public Bycicle(int capacity,
                   String model,
                   boolean open,
                   String owner,
                   int rating) {
        super(capacity, model, open, owner, rating);

        this.bycicleType = bycicleType;
        this.weight = weight;
        this.weightCapacity = weightCapacity;

    }
}
