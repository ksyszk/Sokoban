package com.ae2dms.model;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test {@code GameGrid}.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class GameGridTest {
    GameGrid gameGrid = new GameGrid(20, 20);

    /**
     * Test {@code translatePoint()}.
     */
    @Test
    public void translatePoint() {
        Point before = new Point(0, 0);
        Point after = GameGrid.translatePoint(before, new Point(1, 0));
        Assert.assertEquals(new Point(1, 0), after);
    }

    /**
     * Test {@code removeGameObjectAt()}.
     */
    @Test
    public void removeGameObjectAt() {
        gameGrid.putGameObjectAt(GameObject.KEEPER, new Point(0, 0));
        gameGrid.removeGameObjectAt(new Point(0, 0));
        assertNull(gameGrid.getGameObjectAt(0, 0));
    }

    /**
     * Test {@code putGameObjectAt()}.
     */
    @Test
    public void putGameObjectAt() {
        gameGrid.putGameObjectAt(GameObject.KEEPER, 0, 0);
        Assert.assertEquals(GameObject.KEEPER, gameGrid.getGameObjectAt(0, 0));
    }

    /**
     * Test {@code putGameObjectAt()}.
     */
    @Test
    public void testPutGameObjectAt() {
        gameGrid.putGameObjectAt(GameObject.KEEPER, new Point(0, 0));
        Assert.assertEquals(GameObject.KEEPER, gameGrid.getGameObjectAt(0, 0));
    }
}