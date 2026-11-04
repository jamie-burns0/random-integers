package me.jamieburns;

import java.util.List;
import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RandomIntegersService {

    List<Integer> createRandomIntegersList(int count) {
        return new Random().ints(count, 1, 100).boxed().toList();
    }
}
