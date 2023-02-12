package de.uniks.pmws2223.uno.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.util.List;

@SuppressWarnings("unused")
public class GenModel implements ClassModelDecorator {

    class Game {
        boolean direction;

        @Link("game")
        List<Player> players;
        @Link
        Player currentPLayer;
        @Link("game")
        Card discardPile;
    }

    class Player {
        String name;

        @Link("player")
        List<Card> cards;
        @Link("players")
        Game game;
    }

    class Card {
        String value;
        String color;
        List<String> canPlayedAt;
        List<String> canReceive;

        @Link("discardPile")
        Game game;
        @Link("cards")
        Player player;
    }

    @Override
    public void decorate(ClassModelManager mm) {
        mm.haveNestedClasses(GenModel.class);
    }
}
