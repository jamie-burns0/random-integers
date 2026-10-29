package me.jamieburns;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/generate")
public class RandomIntegersResource {

    private RandomIntegersService service;

    public RandomIntegersResource(RandomIntegersService service) {
        this.service = service;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generate() {
        return Response
            .ok()
            .entity(service.createRandomIntegersList())
            .build();
    }
}
