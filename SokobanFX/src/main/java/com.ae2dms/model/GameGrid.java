package com.ae2dms.model;

import java.awt.*;
import java.util.Iterator;

/**
 * Actions related to {@code GameGrid} and {@code GameObject}.
 *
 * <p>This class delivers functionality of
 * getting object, set object, remove object etc.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class GameGrid implements Iterable {
    final int COLUMNS;
    final int ROWS;

    private GameObject[][] gameObjects;

    /**
     * Create an empty gamegrid.
     * @param columns number of columns
     * @param rows number of rows
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;

        // Initialize the array
        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     * Translate point to anther position.
     * @param sourceLocation position before
     * @param delta move direction
     * @return position after
     */
    public static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * Get dimension.
     * @return dimension
     */
    public Dimension getDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * Get game object from target position.
     * @param source position before
     * @param delta move direction
     * @return game object in the target position
     */
    GameObject getTargetFromSource(Point source, Point delta) {
        return getGameObjectAt(translatePoint(source, delta));
    }

    /**
     * Get game object from specified position.
     * @param col column of the position
     * @param row row of the position
     * @return game objects
     * @throws ArrayIndexOutOfBoundsException array may be too long
     */
    public GameObject getGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (GameEngine.isDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }

        return gameObjects[col][row];
    }

    /**
     * Get game object in the specified position.
     * @param p specified position
     * @return game object
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }

        return getGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * Remove game object from specified position.
     * @param position specified position
     * @return remove object
     */
    public boolean removeGameObjectAt(Point position) {
        return putGameObjectAt(null, position);
    }

    /**
     * Set game object on the specified position.
     * @param gameObject game object
     * @param x x dimension
     * @param y y dimension
     * @return set object
     */
    public boolean putGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }

        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * Set game object at specified position.
     * @param gameObject game object
     * @param p position
     * @return set object
     */
    public boolean putGameObjectAt(GameObject gameObject, Point p) {
        return p != null && putGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * Determine whether point is out of bounds.
     * @param x x dimension
     * @param y y dimension
     * @return true/false
     */
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);
    }

    /**
     * Determine whether point is out of bounds.
     * @param p point
     * @return true/false
     */
    private boolean isPointOutOfBounds(Point p) {
        return isPointOutOfBounds(p.x, p.y);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.getCharSymbol());
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public Iterator<GameObject> iterator() {
        return new GridIterator();
    }

    public class GridIterator implements Iterator<GameObject> {
        int row = 0;
        int column = 0;

        @Override
        public boolean hasNext() {
            return !(row == ROWS && column == COLUMNS);
        }

        @Override
        public GameObject next() {
            if (column >= COLUMNS) {
                column = 0;
                row++;
            }
            return getGameObjectAt(column++, row);
        }
    }
}