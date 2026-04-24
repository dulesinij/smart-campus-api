package com.smartcampus.resources;

import com.smartcampus.exceptions.LinkedResourceNotFoundException;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.Room;
import com.smartcampus.data.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.UriBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    // GET /sensors -> get sensors with optional filtering
    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        Collection<Sensor> sensors = DataStore.sensors.values();

        // Filter by type if query param is provided
        if (type != null && !type.isEmpty()) {
            return sensors.stream()
                    .filter(sensor -> sensor.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        return sensors;
    }

    // POST /sensors -> Create a sensor and link it to a room
    @POST
    public Response createSensor(Sensor sensor) {

        // Validation: check room exists
        Room room = DataStore.rooms.get(sensor.getRoomId());

        if (room == null) {
            
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        // Save sensor
        DataStore.sensors.put(sensor.getId(), sensor);

        // Link sensor to room (Update the Room object to include this sensor ID)
        room.getSensorIds().add(sensor.getId());

        UriBuilder builder = UriBuilder.fromPath("/api/v1/sensors/{id}");
        builder.resolveTemplate("id", sensor.getId());

        return Response.created(builder.build())
                .entity(sensor)
                .build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
