package com.example.ridematching.controller;

import com.example.ridematching.dto.DriverRegistrationRequest;
import com.example.ridematching.dto.RideRequest;
import com.example.ridematching.model.Driver;
import com.example.ridematching.model.Location;
import com.example.ridematching.model.Ride;
import com.example.ridematching.service.DriverService;
import com.example.ridematching.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DriverController {

    private final DriverService driverService;
    private final RideService rideService;

    @Autowired
    public DriverController(DriverService driverService, RideService rideService) {
        this.driverService = driverService;
        this.rideService = rideService;
    }


    @PostMapping("/drivers")
    public Driver registerDriver(@RequestBody DriverRegistrationRequest req) {
        return driverService.registerDriver(req.getName(), req.getX(), req.getY());
    }

    @PostMapping("/rides")
    public Ride requestRide(@RequestBody RideRequest req) {
        return rideService.requestRide(new Location(req.getX(), req.getY()));
    }

    @GetMapping("/ride/all")
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    @PostMapping("/rides/{rideId}/complete")
    public void completeRide(@PathVariable int rideId) {
        rideService.completeRide(rideId);
    }

    @GetMapping("/drivers/available")
    public List<Driver> getAvailableDrivers(@RequestParam double x, @RequestParam double y, @RequestParam(defaultValue = "5") int limit) {
        return driverService.getNearestAvailableDrivers(x, y, limit);
    }
}
