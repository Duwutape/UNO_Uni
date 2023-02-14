package de.uniks.pmws2223.uno;

import java.util.List;

public class Constants {

    // player type
    public static final String HUMAN = "human";
    public static final String BOT = "bot";

    // direction
    public static final String CLOCKWISE = "clockwise";
    public static final String COUNTER_CLOCKWISE = "counterclockwise";

    // has won ints
    public static final int WON_PLAYER = 1;
    public static final int WON_BOTO = 2;
    public static final int WON_BOT1 = 3;
    public static final int WON_BOT2 = 4;

    // card colors
    public static final String YELLOW = "yellow";
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String BLACK = "black";
    public static final List<String> COLORS = List.of(YELLOW, RED, GREEN, BLUE, BLACK);


    // card value
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String SIX = "6";
    public static final String SEVEN = "7";
    public static final String EIGHT = "8";
    public static final String NINE = "9";
    public static final String REVERSE = "reverse";
    public static final String SKIP = "skip";
    public static final String DRAW = "draw";
    public static final String WILD = "wild";
    public static final List<String> VALUES = List.of(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, DRAW, WILD);

}
