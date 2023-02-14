package de.uniks.pmws2223.uno;

import java.util.List;

public class Constants {

    // player type
    public static final String HUMAN = "human";
    public static final String BOT = "bot";

    // cards at start
    public static final int NUMBER_CARDS_START = 7;

    // direction
    public static final String CLOCKWISE = "clockwise";
    public static final String COUNTER_CLOCKWISE = "counterclockwise";

    // syles
    public static final String BACKGROUND_COLOR = "-fx-background-color: ";
    public static final String BACKGROUND_RADIUS = "-fx-background-radius: ";
    public static final String BORDER_COLOR = "-fx-border-color: ";
    public static final String BORDER_RADIUS = "-fx-border-radius: ";
    public static final String FONT_SIZE = "-fx-font-size: ";
    public static final String FONT_WEIGHT = "-fx-font-weight: ";
    public static final int  RADIUS = 10;
    public static final int SIZE_SMALL = 14;
    public static final int SIZE_BIG = 24;
    public static final String BOLD = "bold";

    // display colors
    public static final String DISPLAY_YELLOW = "#edf004";
    public static final String DISPLAY_RED = "#ff0e42";
    public static final String DISPLAY_GREEN = "#76ff00";
    public static final String DISPLAY_BLUE = "#4e2efa";
    public static final String DISPLAY_BLACK = "#090909";
    public static final String DISPLAY_WHITE = "#c3c3c3";


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
    public static final String DRAW = "+2";
    public static final String WILD = "wild";
    public static final List<String> VALUES = List.of(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, DRAW, WILD);

}
