package com.example.ridematching.service;

import com.example.ridematching.model.Driver;
import com.example.ridematching.model.Location;
import com.example.ridematching.model.Ride;
import com.example.ridematching.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RideService {
    private final DriverService driverService;
    private final RideRepository rideRepository;
    private final AtomicInteger rideIdCounter = new AtomicInteger(1);

    @Autowired
    public RideService(DriverService driverService, RideRepository rideRepository) {
        this.driverService = driverService;
        this.rideRepository = rideRepository;
    }

    public synchronized Ride requestRide(Location pickupLocation) {
        Driver driver = driverService.findNearestAvailable(pickupLocation);
        if (driver == null) return null;
        driverService.setAvailability(driver.getId(), false);
        Ride ride = new Ride(rideIdCounter.getAndIncrement(), driver, pickupLocation);
        rideRepository.saveRide(ride.getRideId(), ride);
        return ride;

    }

    public void completeRide(int rideId) {
        rideRepository.removeRide(rideId);

    }

    public List<Ride> getAllRides() {
        return new ArrayList<>(rideRepository.getAllRides());


    }
}
