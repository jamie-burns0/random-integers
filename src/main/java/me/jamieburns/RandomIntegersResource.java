package me.jamieburns;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@OpenAPIDefinition(
    info = @Info(
        title = "RandomInteger", 
        version = "1.0.0"
    )
)

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
    @Operation(
        operationId = "generate",
        description = "generate a list of random integers"
    )
    @APIResponse(
        responseCode = "200",
        description = "a list of random integers",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                type = SchemaType.OBJECT,
                properties = {
                    @SchemaProperty(
                        name = "success",
                        description = "Success indicator",
                        examples = "true"
                    ),
                    @SchemaProperty(
                        name = "data",
                        description = "List of random integers",
                        implementation = Integer.class,
                        type = SchemaType.ARRAY,
                        examples = "[1, 42, 7, 99, 33]"
                    ),
                    @SchemaProperty(
                        name = "error",
                        description = "Error message - not used",
                        examples = "null"
                    )
                }
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "invalid value for numberOfIntegers",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                type = SchemaType.OBJECT,
                properties = {
                    @SchemaProperty(
                        name = "success",
                        description = "Success indicator",
                        examples = "false"
                    ),
                    @SchemaProperty(
                        name = "data",
                        description = "Data payload (null on error)",
                        examples = "null"
                    ),
                    @SchemaProperty(
                        name = "error",
                        description = "Error message describing what went wrong",
                        examples = "numberOfIntegers must be between 1 and 100"
                    )
                }
            )
        )
    )
    @Produces(MediaType.APPLICATION_JSON)
    public Response generate(
        @Parameter(
            name = "numberOfIntegers",
            description = "The number of random integers to generate. Must be between 1 and the configured maximum value. Call GET /config for the current maximum.",
            required = false,
            example = "10"
        )
        @QueryParam("numberOfIntegers") Integer numberOfIntegers)
    {
        var outcome = helper.resolveNumberOfIntegers(numberOfIntegers);

        if (outcome.hasError()) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ApiResponse.error(outcome.error()))
                .build();
        }

        var list = service.createRandomIntegersList(outcome.value());

        return Response
            .ok()
            .entity(ApiResponse.ok(list))
            .build();
    }
}
