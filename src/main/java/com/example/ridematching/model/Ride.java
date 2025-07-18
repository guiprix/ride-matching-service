package com.example.ridematching.model;

public class Ride {
    private final int rideId;
    private final Driver driver;
    private final Location pickupLocation;

    public Ride(int rideId, Driver driver, Location pickupLocation) {
        this.rideId = rideId;
        this.driver = driver;
        this.pickupLocation = pickupLocation;
    }

    public int getRideId() { return rideId; }
    public Driver getDriver() { return driver; }
    public Location getPickupLocation() { return pickupLocation; }
}
