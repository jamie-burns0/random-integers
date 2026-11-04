package me.jamieburns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.BadRequestException;

@QuarkusTest 
class ResourceHelperServiceTest {

    @ConfigProperty(name = "maximum-number-of-integers-to-generate")
    int maxNumberOfIntegers;

    private ResourceHelperService helper;

    @BeforeEach
    void setUp() {
        helper = new ResourceHelperService(maxNumberOfIntegers);
    }

    @Test
    void noQueryParamReturnsMaximum() {
        assertEquals(maxNumberOfIntegers, helper.resolveNumberOfIntegers(null));
    }

    @Test
    void zeroIsRejected() {
        assertThrows(BadRequestException.class, () -> helper.resolveNumberOfIntegers(0));
    }

    @Test
    void negativeIsRejected() {
        assertThrows(BadRequestException.class, () -> helper.resolveNumberOfIntegers(-1));
    }

    @Test
    void aboveMaximumIsRejected() {
        assertThrows(BadRequestException.class, () -> helper.resolveNumberOfIntegers(maxNumberOfIntegers + 1));
    }

    @Test
    void oneIsAccepted() {
        assertEquals(1, helper.resolveNumberOfIntegers(1));
    }

    @Test
    void betweenOneAndMaximumIsAccepted() {
        var n = maxNumberOfIntegers - 1;
        assertEquals(n, helper.resolveNumberOfIntegers(n));
    }

    @Test
    void maximumIsAccepted() {
        assertEquals(maxNumberOfIntegers, helper.resolveNumberOfIntegers(maxNumberOfIntegers));
    }
}
