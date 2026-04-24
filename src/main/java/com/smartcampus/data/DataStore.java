package com.smartcampus.data;

import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

    // Thread-safe storage
    public static Map<String, Room> rooms = new ConcurrentHashMap<>();

    public static Map<String, Sensor> sensors = new ConcurrentHashMap<>();

    public static Map<String, List<SensorReading>> sensorReadings = new ConcurrentHashMap<>();
}
