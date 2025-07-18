package com.example.ridematching.service;

import com.example.ridematching.model.Driver;
import com.example.ridematching.model.Location;
import com.example.ridematching.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository repository;

    @Autowired
    public DriverService(DriverRepository driverRepository){
        this.repository = driverRepository;
    }
    public Driver registerDriver(String name, double x, double y) {
        return repository.save(name, x, y);
    }

    public void updateLocation(int driverId, double x, double y) {
        Driver driver = repository.getById(driverId);
        if (driver != null) {
            driver.setLocation(new Location(x, y));
        }
    }

    public List<Driver> getNearestAvailableDrivers(double x, double y, int limit) {
        Location location = new Location(x, y);
        return repository.getAll().stream()
                .filter(Driver::isAvailable)
                .peek(driver -> {
                    Location loc = driver.getLocation();
                    double distance = loc.distanceTo(location);
                    System.out.println("Available Driver: ID=" + driver.getId() +
                            ", Location=(" + loc.getX() + ", " + loc.getY() + ")" +
                            ", Distance to (" + x + ", " + y + ") = " + distance);
                })
                .sorted(Comparator.comparingDouble(d -> d.getLocation().distanceTo(location)))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void setAvailability(int driverId, boolean available) {
        Driver driver = repository.getById(driverId);
        if (driver != null) {
            driver.setAvailable(available);
        }
    }

    public Driver findNearestAvailable(Location pickup) {
        return repository.getAll().stream()
                .filter(Driver::isAvailable)
                .min((d1, d2) -> Double.compare(
                        d1.getLocation().distanceTo(pickup),
                        d2.getLocation().distanceTo(pickup)))
                .orElse(null);
    }
}
