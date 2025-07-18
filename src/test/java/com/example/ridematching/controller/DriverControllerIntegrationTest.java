package com.example.ridematching.controller;

import com.example.ridematching.repository.DriverRepository;
import com.example.ridematching.repository.RideRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    private int rideId;

    @BeforeEach
    void setup() throws Exception {

        // 1. Register a driver
        mockMvc.perform(post("/api/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Alice",
                          "x": 10.0,
                          "y": 5.0
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));

        // 2. Request a ride
        String response = mockMvc.perform(post("/api/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "x": 10.1,
                          "y": 5.2
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rideId").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<?, ?> result = objectMapper.readValue(response, Map.class);
        rideId = (Integer) result.get("rideId");
    }

    @Test
    void testGetAllRides() throws Exception {
        mockMvc.perform(get("/api/ride/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath(String.format("$[?(@.rideId == %d)].driver.name", rideId))
                        .value(hasItem("Alice")));

    }

    @Test
    void testCompleteRide() throws Exception {
        mockMvc.perform(post("/api/rides/" + rideId + "/complete"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAvailableDriversAfterCompletion() throws Exception {
        // Complete the ride first
        mockMvc.perform(post("/api/rides/" + rideId + "/complete"))
                .andExpect(status().isOk());

        // Driver should now be available again
        mockMvc.perform(get("/api/drivers/available")
                        .param("x", "10.0")
                        .param("y", "5.0")
                        .param("limit", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    void testGetAvailableDriversBeforeRide() throws Exception {
        mockMvc.perform(get("/api/drivers/available")
                        .param("x", "10.0")
                        .param("y", "5.0")
                        .param("limit", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testRegisterDriver() throws Exception {
        mockMvc.perform(post("/api/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "name": "Bob",
                      "x": 20.0,
                      "y": 10.0
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.location.x").value(20.0))
                .andExpect(jsonPath("$.location.y").value(10.0))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void testFindNearestAvailableDriver() throws Exception {
        // Complete ride to make Alice available again
        mockMvc.perform(post("/api/rides/" + rideId + "/complete"))
                .andExpect(status().isOk());

        // Now check if she appears in nearby drivers
        mockMvc.perform(get("/api/drivers/available")
                        .param("x", "10.1")
                        .param("y", "5.1")
                        .param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].available").value(true));
    }

}
