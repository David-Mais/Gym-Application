package com.davidmaisuradze.gymapplication.initializer;

import java.util.Random;

public class RandomPassGenerator {
    private static final Random random = new Random();
    private RandomPassGenerator() {}
    public static String generatePassword() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            char randomChar = (char) random.nextInt(33, 127);
            builder.append(randomChar);
        }
        return builder.toString();
    }
}
