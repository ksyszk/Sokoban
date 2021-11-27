package com.ae2dms.model.move_State;

import com.ae2dms.controller.gameController;
import com.ae2dms.model.GameEngine;

import java.awt.*;

/**
 * Handle movement in normal state.
 *
 * <p>This class controls the movement of keeper in normal state.</p>
 * @author Sijin WANG
 * @since 21 November 2020
 */
public class normalMove implements moveHandle {
    private GameEngine gameEngine = gameController.getGameEngine();

    /**
     * Move up.
     */
    @Override
    public void goUp() {
        gameEngine.move(new Point(-1, 0));
    }

    /**
     * Move down.
     */
    @Override
    public void goDown() {
        gameEngine.move(new Point(1, 0));
    }

    /**
     * Move left.
     */
    @Override
    public void goLeft() {
        gameEngine.move(new Point(0, -1));
    }

    /**
     * Move right.
     */
    @Override
    public void goRight() {
        gameEngine.move(new Point(0, 1));
    }
}
