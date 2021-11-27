package com.ae2dms.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Level in the game.
 *
 * <p>This class create and store levels.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public final class Level implements Iterable<GameObject> {
    /**
     * A game grid used for objects except diamond.
     */
    public final GameGrid objectsGrid;

    /**
     * A game grid used for diamond.
     */
    public final GameGrid diamondsGrid;

    private final String name;
    private final int index;
    private final String count;
    private int numberOfDiamonds = 0;
    private Point keeperPosition = new Point(0, 0);
    private Point cratePosition = new Point(0, 0);
    private List<Point> allCrates = new ArrayList<>();
    private List<Point> allWalls = new ArrayList<>();

    /**
     * Constructor of level.
     *
     * @param levelName name of this level
     * @param levelIndex the index of level
     * @param raw_level a list stores objects in the level
     */
    public Level(String moveCount, String levelName, int levelIndex, List<String> raw_level) {
        if (GameEngine.isDebugActive()) {
            System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);
        }

        name = levelName;
        index = levelIndex;
        count = moveCount;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        objectsGrid = new GameGrid(rows, columns);  // empty map
        diamondsGrid = new GameGrid(rows, columns);

        int rowSize = raw_level.size();
        for (int row = 0; row < rowSize; row++) {
            int colSize = raw_level.get(row).length();
            for (int col = 0; col < colSize; col++) {
                GameObject curTile = GameObject.fromChar(raw_level.get(row).charAt(col));

                if (curTile == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.putGameObjectAt(curTile, row, col);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.KEEPER) {
                    keeperPosition = new Point(row, col);
                } else if (curTile == GameObject.KEEPER_ON_DIAMOND) {
                    keeperPosition = new Point(row, col);
                    numberOfDiamonds++;
                    diamondsGrid.putGameObjectAt(GameObject.DIAMOND, row, col);
                    curTile = GameObject.KEEPER;
                } else if (curTile == GameObject.CRATE) {
                    cratePosition = new Point(row, col);
                    // initial positions of all crates are stored in allCrates
                    allCrates.add((Point) cratePosition.clone());
                } else if (curTile == GameObject.WALL) {
                    getAllWalls().add(new Point(row, col));
                }

                objectsGrid.putGameObjectAt(curTile, row, col);
                curTile = null;
            }
        }
    }

    /**
     * Determine whether this level has completed.
     * @return true/false
     */
    boolean isComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                if (objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.getGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }
        return cratedDiamondsCount >= numberOfDiamonds;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public Point getKeeperPosition() {
        return keeperPosition;
    }

    GameObject getTargetObject(Point source, Point delta) {
        return objectsGrid.getTargetFromSource(source, delta);
    }

    @Override
    public String toString() {
        return objectsGrid.toString();
    }

    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }

    public Point getCratePosition() {
        return cratePosition;
    }

    public List<Point> getFirstCrates() {
        return allCrates;
    }

    public List<Point> getAllWalls() {
        return allWalls;
    }

    public String getCount() {
        return count;
    }

    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        @Override
        public boolean hasNext() {
            return !(row == objectsGrid.ROWS - 1 && column == objectsGrid.COLUMNS);
        }

        @Override
        public GameObject next() {
            if (column >= objectsGrid.COLUMNS) {
                column = 0;
                row++;
            }

            GameObject object = objectsGrid.getGameObjectAt(column, row);
            GameObject diamond = diamondsGrid.getGameObjectAt(column, row);
            GameObject retObj = object;
            column++;
            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                } else if (object == GameObject.KEEPER) {
                    retObj = GameObject.KEEPER_ON_DIAMOND;
                } else {
                    retObj = object;
                }
            }
            return retObj;
        }

        public Point getCurrentPosition() {
            return new Point(column, row);
        }

    }
}