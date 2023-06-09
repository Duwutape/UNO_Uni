package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;

import java.util.Random;

import static de.uniks.pmws2223.uno.Constants.*;

public class RandomService {
    private final Random random;

    public RandomService(Random random) {
        this.random = random;
    }

    public RandomService() {
        this.random = new Random();
    }

    public Card createCard() {
        int value = chooseValue();
        int color = chooseColor();

        if (value == 13) {
            return new Card(VALUES.get(value), BLACK);
        }
        return new Card(VALUES.get(value), COLORS.get(color));
    }

    public int chooseValue() {
        return random.nextInt(0, 14);
    }

    public int chooseColor() {
        return random.nextInt(0, 4);
    }
}
