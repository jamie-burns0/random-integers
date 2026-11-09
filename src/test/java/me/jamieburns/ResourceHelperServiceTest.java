package me.jamieburns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

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
        var outcome = helper.resolveNumberOfIntegers(null);
        assertTrue(outcome.hasValue());
        assertFalse(outcome.hasError());
        assertEquals(maxNumberOfIntegers, outcome.value());
    }

    @Test
    void zeroIsRejected() {
        var outcome = helper.resolveNumberOfIntegers(0);
        assertFalse(outcome.hasValue());
        assertTrue(outcome.hasError());
        assertEquals("numberOfIntegers must be at least 1", outcome.error());
    }

    @Test
    void negativeIsRejected() {
        var outcome = helper.resolveNumberOfIntegers(-1);
        assertFalse(outcome.hasValue());
        assertTrue(outcome.hasError());
        assertEquals("numberOfIntegers must be at least 1", outcome.error());
    }

    @Test
    void aboveMaximumIsRejected() {
        var outcome = helper.resolveNumberOfIntegers(maxNumberOfIntegers + 1);
        assertFalse(outcome.hasValue());
        assertTrue(outcome.hasError());
        assertEquals("numberOfIntegers must be no more than %s".formatted(maxNumberOfIntegers), outcome.error());
    }

    @Test
    void oneIsAccepted() {
        var outcome = helper.resolveNumberOfIntegers(1);
        assertTrue(outcome.hasValue());
        assertFalse(outcome.hasError());
        assertEquals(1, outcome.value());
    }

    @Test
    void betweenOneAndMaximumIsAccepted() {
        var n = maxNumberOfIntegers - 1;
        var outcome = helper.resolveNumberOfIntegers(n);
        assertTrue(outcome.hasValue());
        assertFalse(outcome.hasError());
        assertEquals(n, outcome.value());
    }

    @Test
    void maximumIsAccepted() {
        var outcome = helper.resolveNumberOfIntegers(maxNumberOfIntegers);
        assertTrue(outcome.hasValue());
        assertFalse(outcome.hasError());
        assertEquals(maxNumberOfIntegers, outcome.value());
    }
}
