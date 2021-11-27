package com.ae2dms.model;

/**
 * Define all kinds of game objects.
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public enum GameObject {
    WALL('W'),
    FLOOR(' '),
    CRATE('C'),
    DIAMOND('D'),
    KEEPER('S'),
    CRATE_ON_DIAMOND('O'),
    KEEPER_ON_DIAMOND('K'),
    DEBUG_OBJECT('=');

    public final char symbol;
    GameObject(final char symbol) {
        this.symbol = symbol;
    }

    /**
     * Translate lower case 'c' to upper case 'C'.
     *
     * <p>If meet 'c', translate it into 'C'.</p>
     *
     * @param c lower case 'c'
     * @return WALL
     */
    public static GameObject fromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }
        return WALL;
    }

    /**
     * Get object symbol.
     * @return symbol
     */
    public String getStringSymbol() {
        return String.valueOf(symbol);
    }

    /**
     * Get object symbol.
     * @return symbol
     */
    public char getCharSymbol() {
        return symbol;
    }
}