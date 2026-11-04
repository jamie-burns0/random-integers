package me.jamieburns;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped 
public class ResourceHelperService {

    private final int maximumNumberOfIntegers;

    public ResourceHelperService(
        @ConfigProperty(name = "maximum-number-of-integers-to-generate")
        int maximumNumberOfIntegers)
    {
        this.maximumNumberOfIntegers = maximumNumberOfIntegers;
    }

    int resolveNumberOfIntegers(Integer numberOfIntegers) {

        if (numberOfIntegers == null) {
            return maximumNumberOfIntegers;
        }

        if (numberOfIntegers < 1) {
            throw new BadRequestException("numberOfIntegers must be at least 1");
        }

        if (numberOfIntegers > maximumNumberOfIntegers) {
            throw new BadRequestException("numberOfIntegers must be no more than %s".formatted(maximumNumberOfIntegers));
        }
        
        return Math.min(numberOfIntegers, maximumNumberOfIntegers);
    }
}
