package me.jamieburns;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection 
public record Config(
    int maximumNumberOfIntegers
) {}
