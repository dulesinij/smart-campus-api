package com.smartcampus.resources;

import com.smartcampus.models.Room;
import com.smartcampus.exceptions.RoomNotEmptyException;
import com.smartcampus.data.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.UriBuilder;
import java.util.Collection;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    // GET /rooms -> get all rooms
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    // POST /rooms -> create new room
    @POST
    public Response createRoom(Room room) {

        // Basic validation
        if (room.getId() == null || room.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID is required")
                    .build();
        }

        // Save room
        DataStore.rooms.put(room.getId(), room);

        UriBuilder builder = UriBuilder.fromPath("/api/v1/rooms/{id}");
        builder.resolveTemplate("id", room.getId());

        return Response.created(builder.build())
                .entity(room)
                .build();
    }

    // GET /rooms/{id} -> get specific room
    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }

    // DELETE /rooms/{id}
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        // Business Logic: Prevent deletion if sensors are still assigned to the room
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            
            throw new RoomNotEmptyException("Cannot delete room: sensors still assigned");
        }

        DataStore.rooms.remove(id);

        return Response.ok("Room deleted successfully").build();
    }
}
