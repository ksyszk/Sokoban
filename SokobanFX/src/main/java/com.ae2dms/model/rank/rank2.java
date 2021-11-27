package com.ae2dms.model.rank;

import com.ae2dms.controller.gameController;
import com.ae2dms.model.GameEngine;

import java.io.IOException;

/**
 * Rank2.
 *
 * <p>Load file which stores scores of level2.
 * Calculate and show rank after complete level2.</p>
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class rank2 implements rank {
    private GameEngine gameEngine = gameController.getGameEngine();

    @Override
    public void rankLevel() throws IOException {
        gameEngine.ranking("src/main/resources/rank/level2.txt");
    }
}
