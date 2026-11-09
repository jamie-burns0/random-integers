package me.jamieburns;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped 
public class ResourceHelperService {

    private final int maximumNumberOfIntegers;

    public ResourceHelperService(
        @ConfigProperty(name = "maximum-number-of-integers-to-generate")
        int maximumNumberOfIntegers)
    {
        this.maximumNumberOfIntegers = maximumNumberOfIntegers;
    }

    int maximumNumberOfIntegers() {
        return maximumNumberOfIntegers;
    }

    Outcome<Integer, String> resolveNumberOfIntegers(Integer numberOfIntegers) {

        if (numberOfIntegers == null) {
            return Outcome.success(maximumNumberOfIntegers);
        }

        if (numberOfIntegers < 1) {
            return Outcome.failure("numberOfIntegers must be at least 1");
        }

        if (numberOfIntegers > maximumNumberOfIntegers) {
            return Outcome.failure("numberOfIntegers must be no more than %s".formatted(maximumNumberOfIntegers));
        }
        
        return Outcome.success(Math.min(numberOfIntegers, maximumNumberOfIntegers));
    }
}
