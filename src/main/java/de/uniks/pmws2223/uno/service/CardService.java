package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.Constants;
import de.uniks.pmws2223.uno.model.Card;

import java.util.Random;

import static de.uniks.pmws2223.uno.Constants.*;

public class CardService {
    private final Random random= new Random();

    public Card createCard(){
        int value = random.nextInt(0,13);
        int color = random.nextInt(0,3);

        if (value == 13){
            return new Card(VALUES.get(value), BLACK);
        }
        return new Card(VALUES.get(value), COLORS.get(color));
    }
}
