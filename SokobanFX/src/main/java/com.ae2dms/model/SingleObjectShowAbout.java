package com.ae2dms.model;

import com.ae2dms.controller.gameController;

/**
 * Show about using Singleton pattern.
 *
 * <p>This class uses Singleton pattern to show about.</p>
 *
 * @author Sijin WANG
 * @since 23 November 2020
 */
public class SingleObjectShowAbout {
    gameController gc = new gameController();
    private static SingleObjectShowAbout instance = new SingleObjectShowAbout();

    private SingleObjectShowAbout() {}

    public static SingleObjectShowAbout getInstance() {
        return instance;
    }

    public void showAbout() {
        String title = "About this game";
        String message = "Game created by Sijin WANG\n";

        gc.newDialog(title, message, null, 150);
    }
}
