package me.jamieburns;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/generate")
public class RandomIntegersResource {

    private RandomIntegersService service;
    private ResourceHelperService helper;

    public RandomIntegersResource(
        RandomIntegersService service,
        ResourceHelperService helper)
    {
        this.service = service;
        this.helper = helper;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generate(
        @QueryParam("numberOfIntegers") Integer numberOfIntegers)
    {
        var n = helper.resolveNumberOfIntegers(numberOfIntegers);

        return Response
            .ok()
            .entity(service.createRandomIntegersList(n))
            .build();
    }
}
