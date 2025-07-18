package com.example.ridematching.model;

public class Driver {
    private final int id;
    private String name;
    private Location location;
    private boolean available;

    public Driver(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.available = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Location getLocation() { return location; }
    public boolean isAvailable() { return available; }

    public void setName(String name) { this.name = name; }
    public void setLocation(Location location) { this.location = location; }
    public void setAvailable(boolean available) { this.available = available; }
}
