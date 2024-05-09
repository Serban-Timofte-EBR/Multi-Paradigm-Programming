package com.university.traffic.models;

import java.util.Arrays;

enum FuelType {
    PETROL, DIESEL, ELECTRIC
}
public class Car extends Vehicle {
    private FuelType fuelType;

    private int[] distances;

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public int[] getDistances() {
        return Arrays.copyOf(distances, distances.length);
    }

    public void setDistances(int[] distances) {
        this.distances = Arrays.copyOf(distances, distances.length);
    }

    public Car(String make, String model, int year, FuelType fuelType) {
        super(make, model, year);
        this.fuelType = fuelType;
    }

    @Override
    public float getFuelEfficiency() {
        // PETROL: 12.5, DIESEL: 15, ELECTRIC: 0
        if (fuelType == FuelType.PETROL) { return 12.5f; }
        else if (fuelType == FuelType.DIESEL) { return 15f; }
        return 0f;
    }
}
