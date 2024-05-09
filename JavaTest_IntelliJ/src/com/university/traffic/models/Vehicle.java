package com.university.traffic.models;

import com.university.traffic.contracts.Rideable;

public abstract class Vehicle implements Rideable {
    private String make;
    private String model;
    private int year;

    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    @Override
    public int getMaxSpeed() {
        return 100;
    }

    public abstract float getFuelEfficiency();
}
