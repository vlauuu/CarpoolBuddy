package com.example.carpoolbuddy;

import java.util.ArrayList;

/**
 * It creates a user with suitable params
 *
 * @author Vico Lau
 * @version 0.1
 */

public class Vehicle {

    private int basePrice;
    private int capacity;
    private String model;
    private boolean open;
    private String owner;
    private int rating;
    private ArrayList<String> reservedUids;
    private String vehicleId;
    private String vehicleType;

    public Vehicle(int capacity,
                   String model,
                   boolean open,
                   String owner,
                   int rating) {

        this.capacity = capacity;
        this.model = model;
        this.open = open;
        this.owner = owner;
        this.rating = rating;

    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<String> getReservedUids() {
        return reservedUids;
    }

    public void setReservedUids(ArrayList<String> reservedUids) {
        this.reservedUids = reservedUids;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
