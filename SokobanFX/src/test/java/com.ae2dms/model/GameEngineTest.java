package com.ae2dms.model;

import com.ae2dms.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Test {@code GameEngine}.
 *
 * @author Sijin WANG
 * @since 20 November 2020
 */
public class GameEngineTest extends ApplicationTest {
    private static GameEngine gameEngine;
    private Level currentLevel;
    private List<Point> allCrates = new ArrayList<>();

    /**
     * Show the game page.
     * @param stage current page
     * @throws IOException fxml may not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/game.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        InputStream in = getClass().getClassLoader().getResourceAsStream("debugGame.skb");
        gameEngine = new GameEngine(in, true);
    }

    /**
     * Set before test.
     */
    @Before
    public void setUp() {
        currentLevel = gameEngine.getCurrentLevel();
    }

    /**
     * Test {@code HandleKey} go up.
     */
    @Test
    public void handleKeyUp() {
        gameEngine.setIsTest(1);
        gameEngine.handleKey(KeyCode.UP);
        Assert.assertEquals(1, gameEngine.getIsHandle());
    }

    /**
     * Test {@code HandleKey} go down.
     */
    @Test
    public void handleKeyDown() {
        gameEngine.setIsTest(1);
        gameEngine.handleKey(KeyCode.DOWN);
        Assert.assertEquals(1, gameEngine.getIsHandle());
    }

    /**
     * Test {@code HandleKey} go left.
     */
    @Test
    public void handleKeyLeft() {
        gameEngine.setIsTest(1);
        gameEngine.handleKey(KeyCode.LEFT);
        Assert.assertEquals(1, gameEngine.getIsHandle());
    }

    /**
     * Test {@code HandleKey} go right.
     */
    @Test
    public void handleKeyRight() {
        gameEngine.setIsTest(1);
        gameEngine.handleKey(KeyCode.RIGHT);
        Assert.assertEquals(1, gameEngine.getIsHandle());
    }

    /**
     * Test {@code HandleKey} destroy up.
     */
    @Test
    public void handleKeyDestroyUp() {
        gameEngine.handleKey(KeyCode.W);
        Assert.assertEquals(1, gameEngine.getIsDestroy());
    }

    /**
     * Test {@code HandleKey} destroy down.
     */
    @Test
    public void handleKeyDestroyDown() {
        gameEngine.handleKey(KeyCode.S);
        Assert.assertEquals(1, gameEngine.getIsDestroy());
    }

    /**
     * Test {@code HandleKey} destroy left.
     */
    @Test
    public void handleKeyDestroyLeft() {
        gameEngine.handleKey(KeyCode.A);
        Assert.assertEquals(1, gameEngine.getIsDestroy());
    }

    /**
     * Test {@code HandleKey} destroy right.
     */
    @Test
    public void handleKeyDestroyRight() {
        gameEngine.handleKey(KeyCode.D);
        Assert.assertEquals(1, gameEngine.getIsDestroy());
    }

    /**
     * Test {@code HandleKey} undo.
     */
    @Test
    public void handleKeyUndo() {
        gameEngine.setIsTest(1);
        gameEngine.handleKey(KeyCode.LEFT);
        gameEngine.handleKey(KeyCode.Z);
        Assert.assertEquals(1, gameEngine.getIsUndo());
    }

    /**
     * Test {@code move} go up.
     */
    @Test
    public void moveUp() {
        gameEngine.move(new Point(-1, 0));
        Point moved = gameEngine.getCurrentLevel().getKeeperPosition();
        // initial point is (2, 13)
        Assert.assertEquals(moved, new Point(1, 13));

        GameObject gameObject = currentLevel.objectsGrid.getGameObjectAt(moved);
        Assert.assertEquals(GameObject.KEEPER, gameObject);
    }

    /**
     * Test {@code move} go down.
     */
    @Test
    public void moveDown() {
        gameEngine.move(new Point(1, 0));
        Point moved = gameEngine.getCurrentLevel().getKeeperPosition();
        // initial point is (2, 13)
        Assert.assertEquals(moved, new Point(3, 13));

        GameObject gameObject = currentLevel.objectsGrid.getGameObjectAt(moved);
        Assert.assertEquals(GameObject.KEEPER, gameObject);
    }

    /**
     * Test {@code move} go left.
     */
    @Test
    public void moveLeft() {
        gameEngine.move(new Point(0, -1));
        Point moved = gameEngine.getCurrentLevel().getKeeperPosition();
        // initial point is (2, 13)
        Assert.assertEquals(moved, new Point(2, 12));

        GameObject gameObject = currentLevel.objectsGrid.getGameObjectAt(moved);
        Assert.assertEquals(GameObject.KEEPER, gameObject);
    }

    /**
     * Test {@code move} go right.
     */
    @Test
    public void moveRight() {
        gameEngine.move(new Point(0, 1));
        Point moved = gameEngine.getCurrentLevel().getKeeperPosition();
        // initial point is (2, 13)
        Assert.assertEquals(moved, new Point(2, 14));

        GameObject gameObject = currentLevel.objectsGrid.getGameObjectAt(moved);
        Assert.assertEquals(GameObject.KEEPER, gameObject);
    }

    /**
     * Test {@code moveBack} without crate.
     */
    @Test
    public void moveBackWithoutCrate() {
        gameEngine.move(new Point(0, 1));
        gameEngine.move(new Point(0, 1));
        gameEngine.moveBack();
        Point moved = gameEngine.getCurrentLevel().getKeeperPosition();
        Assert.assertEquals(moved, new Point(2, 14));

        GameObject gameObject = currentLevel.objectsGrid.getGameObjectAt(moved);
        Assert.assertEquals(GameObject.KEEPER, gameObject);
    }

    /**
     * Test {@code moveBack} with crate.
     */
    @Test
    public void moveBackWithCrate() {
        gameEngine.move(new Point(0, -1));
        gameEngine.move(new Point(0, -1));
        gameEngine.move(new Point(0, -1));
        gameEngine.moveBack();

        // get position of keeper and crate
        Point movedKeeper = gameEngine.getCurrentLevel().getKeeperPosition();
        Point cratePos = gameEngine.getCurrentLevel().getCratePosition();

        Assert.assertEquals(movedKeeper, new Point(2, 11));
        Assert.assertEquals(cratePos, new Point(2, 10));

        GameObject keeper = currentLevel.objectsGrid.getGameObjectAt(movedKeeper);
        Assert.assertEquals(GameObject.KEEPER, keeper);
        GameObject crate = currentLevel.objectsGrid.getGameObjectAt(cratePos);
        Assert.assertEquals(GameObject.CRATE, crate);
    }

    /**
     * Test {@code moveFirst} (reset).
     */
    @Test
    public void moveFirst() {
        gameEngine.move(new Point(0, -1));
        gameEngine.move(new Point(0, -1));
        gameEngine.move(new Point(0, -1));
        gameEngine.moveFirst();

        // get position of keeper and crate
        Point movedKeeper = gameEngine.getCurrentLevel().getKeeperPosition();
        allCrates = gameEngine.getCurrentLevel().getFirstCrates();

        Assert.assertEquals(new Point(2, 13), movedKeeper);
        Assert.assertEquals(new Point(2, 10), allCrates.get(0));

        GameObject keeper = currentLevel.objectsGrid.getGameObjectAt(movedKeeper);
        Assert.assertEquals(GameObject.KEEPER, keeper);
        GameObject crate = currentLevel.objectsGrid.getGameObjectAt(allCrates.get(0));
        Assert.assertEquals(GameObject.CRATE, crate);
    }
}