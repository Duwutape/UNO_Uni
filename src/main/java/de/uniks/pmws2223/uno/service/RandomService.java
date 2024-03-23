package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;

import java.util.Objects;
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
        String value = chooseValue();

        if (Objects.equals(value, WILD)) {
            return new Card(value, BLACK);
        }
        return new Card(value, chooseColor());
    }

    public String chooseValue() {
        return VALUES.get(random.nextInt(0, 14));
    }

    public String chooseColor() {
        return COLORS.get(random.nextInt(0, 4));
    }
}
