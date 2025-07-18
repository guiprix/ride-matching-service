package com.example.ridematching.dto;

public class DriverRegistrationRequest {
    private String name;
    private double x;
    private double y;

    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }

    public void setName(String name) { this.name = name; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
