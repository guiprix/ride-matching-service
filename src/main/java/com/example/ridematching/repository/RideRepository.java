package com.example.ridematching.repository;

import com.example.ridematching.model.Driver;
import com.example.ridematching.model.Location;
import com.example.ridematching.model.Ride;
import com.example.ridematching.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RideRepository {
    private final DriverService driverService;
    private final ConcurrentHashMap<Integer, Ride> rides = new ConcurrentHashMap<>();

    @Autowired
    public RideRepository(DriverService driverService) {
        this.driverService = driverService;
    }

    public Collection<Ride> getAllRides() {
        System.out.println("all rides: " + rides.values());
        return rides.values();
    }

    public void removeRide(int rideId) {
        Ride ride = rides.remove(rideId);
        if (ride != null) {
            driverService.setAvailability(ride.getDriver().getId(), true);
        }
    }

    public synchronized void saveRide(int rideId, Ride ride) {
        rides.put(ride.getRideId(), ride);
    }
    //for tests
    public void clear() {
        rides.clear();
    }
}
