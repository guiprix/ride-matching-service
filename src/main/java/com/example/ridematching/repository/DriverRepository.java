package com.example.ridematching.repository;

import com.example.ridematching.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

@Repository
public class DriverRepository {
    private final ConcurrentHashMap<Integer, Driver> drivers = new ConcurrentHashMap<>();
    private int idCounter = 1;

    public synchronized Driver save(String name, double x, double y) {
        Driver driver = new Driver(idCounter++, name, new com.example.ridematching.model.Location(x, y));
        drivers.put(driver.getId(), driver);
        return driver;
    }

    public Driver getById(int id) {
        return drivers.get(id);
    }

    public Collection<Driver> getAll() {
        return drivers.values();
    }
    //for tests
    public void clear() {
        drivers.clear();
    }
}
