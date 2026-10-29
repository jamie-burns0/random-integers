package me.jamieburns;

import java.util.List;
import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RandomIntegersService {

    List<Integer> createRandomIntegersList() {
        return new Random().ints(100L, 1, 100).boxed().toList();
    }
}
