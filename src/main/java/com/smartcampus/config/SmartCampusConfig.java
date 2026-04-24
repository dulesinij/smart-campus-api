package com.smartcampus.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1/...") // Fulfills the versioning requirement

public class SmartCampusConfig extends Application {
    // This can stay empty. JAX-RS will automatically find your resource classes using this configuration
}
