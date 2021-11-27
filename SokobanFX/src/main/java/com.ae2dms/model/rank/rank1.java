package com.ae2dms.model.rank;

import com.ae2dms.controller.gameController;
import com.ae2dms.model.GameEngine;

import java.io.IOException;

/**
 * Rank1.
 *
 * <p>Load file which stores scores of level1.
 * Calculate and show rank after complete level1.</p>
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class rank1 implements rank{
    private GameEngine gameEngine = gameController.getGameEngine();

    @Override
    public void rankLevel() throws IOException {
        gameEngine.ranking("src/main/resources/rank/level1.txt");
    }
}
