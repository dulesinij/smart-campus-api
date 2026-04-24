package com.smartcampus.mappers;

import com.smartcampus.exceptions.SensorUnavailableException;
import com.smartcampus.models.ErrorResponse;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException ex) {
        ErrorResponse error = new ErrorResponse(403, ex.getMessage());

        return Response.status(Response.Status.FORBIDDEN)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
