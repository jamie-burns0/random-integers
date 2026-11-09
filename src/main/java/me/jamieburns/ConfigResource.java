package me.jamieburns;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/config")
public class ConfigResource {

    private ResourceHelperService helper;

    public ConfigResource(ResourceHelperService helper) {
        this.helper = helper;
    }

    @GET
    @Operation(
        operationId = "config",
        description = "get API configuration details including maximum number of integers"
    )
    @APIResponse(
        responseCode = "200",
        description = "API configuration",
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
                        description = "Configuration details",
                        type = SchemaType.OBJECT,
                        examples = "{\"maximumNumberOfIntegers\": 1000}"
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response config() {

        Config config = new Config(helper.maximumNumberOfIntegers());
          
        return Response
            .ok()
            .entity(ApiResponse.ok(config))
            .build();
    }
}
