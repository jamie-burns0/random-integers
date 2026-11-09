package me.jamieburns;

/**
 * Represents the outcome of an operation with a value and error field.
 * Exactly one will be non-null.
 * Use the static factory methods success() or failure() to construct instances.
 * 
 * @param <T> The type of the success value
 * @param <E> The type of the error value
 */
public record Outcome<T, E>(T value, E error) {
    
    public Outcome {
        if (
            (value != null && error != null) || 
            (value == null && error == null)) 
        {
            throw new IllegalArgumentException("An Outcome must have only one value or one error");
        }
    }
    
    public static <T, E> Outcome<T, E> success(T value) {
        return new Outcome<>(value, null);
    }
    
    public static <T, E> Outcome<T, E> failure(E error) {
        return new Outcome<>(null, error);
    }
    
    public boolean hasError() {
        return error != null;
    }
    
    public boolean hasValue() {
        return value != null;
    }
}
