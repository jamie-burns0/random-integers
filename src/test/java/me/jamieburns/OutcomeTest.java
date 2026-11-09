package me.jamieburns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OutcomeTest {

    @Test
    void successCreatesOutcomeWithValue() {

        var outcome = Outcome.success(42);
        
        assertEquals(42, outcome.value());
        assertNull(outcome.error());
        assertTrue(outcome.hasValue());
        assertFalse(outcome.hasError());
    }

    @Test
    void failureCreatesOutcomeWithError() {
        var outcome = Outcome.failure("something went wrong");
        
        assertNull(outcome.value());
        assertEquals("something went wrong", outcome.error());
        assertFalse(outcome.hasValue());
        assertTrue(outcome.hasError());
    }

    @Test
    void directConstructionWithBothValueAndErrorThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Outcome<>(42, "error"));
    }

    @Test
    void directConstructionWithBothNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Outcome<>(null, null));
    }

    @Test
    void successWithNullValueThrows() {
        assertThrows(IllegalArgumentException.class, () -> Outcome.success(null));
    }

    @Test
    void failureWithNullErrorThrows() {
        assertThrows(IllegalArgumentException.class, () -> Outcome.failure(null));
    }
}
