package com.movieapp.android.service;

import static org.junit.Assert.assertEquals;

import com.movieapp.android.model.LocationPosition;

import org.junit.Test;

public class HaversineCalculatorTest {

    @Test
    public void shouldCorrectlyCalculateDistance() {
        testDistanceCalculation(new LocationPosition(51.5074, 0.1278), new LocationPosition(48.8566, 2.3522), 334.6);
        testDistanceCalculation(new LocationPosition(40.7128, -74.0060), new LocationPosition(34.0522, -118.2437), 3935.8);
        testDistanceCalculation(new LocationPosition(-33.8688, 151.2093), new LocationPosition(35.6895, 139.6917), 7826.6);
    }

    private void testDistanceCalculation(LocationPosition currentPosition, LocationPosition destination, double expectedDistance) {
        double distance = HaversineDistanceCalculator.calculate(currentPosition, destination);
        assertEquals(expectedDistance, distance, 0.1);
    }

}
