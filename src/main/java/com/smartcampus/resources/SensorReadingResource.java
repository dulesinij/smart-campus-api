package com.smartcampus.resources;

import com.smartcampus.exceptions.SensorUnavailableException;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;
import com.smartcampus.data.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.UriBuilder;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    // constructor receives sensorId
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // GET /sensors/{id}/readings
    @GET
    public Response getReadings() {

        List<SensorReading> readings =
                DataStore.sensorReadings.getOrDefault(sensorId, new ArrayList<>());

        return Response.ok(readings).build();
    }

    // POST /sensors/{id}/readings
    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        // add reading to list
        DataStore.sensorReadings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        // Side Effect
        sensor.setCurrentValue(reading.getValue());

        UriBuilder builder = UriBuilder.fromPath("/api/v1/sensors/{sid}/readings/{rid}");
        builder.resolveTemplate("sid", sensorId);
        builder.resolveTemplate("rid", reading.getId());

        return Response.created(builder.build())
                .entity(reading)
                .build();
    }
}
