package me.jamieburns;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Consistent API response wrapper for both success and error cases.
 * 
 * @param <T> The type of the success data
 */
@RegisterForReflection
public record ApiResponse<T>(boolean success, T data, String error) {
    
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null);
    }
    
    public static <T> ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(false, null, errorMessage);
    }
}
