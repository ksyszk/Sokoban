package com.ae2dms.model;

import com.ae2dms.controller.gameController;
import com.ae2dms.model.move_State.moveHandle;
import com.ae2dms.model.move_State.moveState;
import com.ae2dms.model.move_State.normalMove;
import com.ae2dms.model.move_State.reverseMove;
import com.ae2dms.model.rank.rank;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * All features applied in Sokoban.
 *
 * <p>This class delivers all actions which can be done in this game.
 * For example, move, undo, reset etc.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class GameEngine {
    /**
     * Game name.
     */
    public static final String GAME_NAME = "SokobanFX";

    /**
     * Record log.
     */
    public static GameLogger logger;

    /**
     * Count movements for the whole game.
     */
    public int movesCount = 0;

    /**
     * Count movements for each level.
     */
    private int eachLevelCount = 0;

    /**
     * Map name.
     */
    public String mapSetName;

    public List<String> finalRank = new ArrayList<>();

    public int rankNum = 0;

    private static boolean debug = false;
    private static boolean reverse = false;
    private Level currentLevel;
    private List<Level> levels;
    private boolean gameComplete = false;
    private List<Point> positions = new ArrayList<>();
    boolean isCrateMoved = false;
    private final List<Point> crates = new ArrayList<>();
    private List<Point> allCrates = new ArrayList<>();
    private List<Point> allWalls = new ArrayList<>();
    gameController gc = new gameController();
    private boolean keeperMoved = false;

    /**
     * Number of destroyed walls.
     */
    public int destroyNum = 0;

    /**
     * Number of using undo.
     */
    public int undoNum = 0;

    /**
     * Number of using reset.
     */
    public int resetNum = 0;

    private int isHandle = 0;
    private int isTest = 0;
    private int isDestroy = 0;
    private int isUndo = 0;
    private boolean isBuff = false;

    /**
     * Create a game engine, load game file, set levels.
     *
     * <p>Initialize he game.</p>
     *
     * @param input game file
     * @param production
     */
    public GameEngine(InputStream input, boolean production) {
        try {
            logger = new GameLogger();
            levels = loadGameFile(input);
            currentLevel = getNextLevel();
        } catch (IOException x) {
            System.out.println("Cannot create logger.");
        } catch (NoSuchElementException e) {
            logger.warning("Cannot load the default save file: " + e.getStackTrace());
        }
    }

    /**
     * Open/Close debug mode
     *
     * @return true/false
     */
    public static boolean isDebugActive() {
        return debug;
    }

    /**
     * Handle key press
     *
     * <p>For each key press, call corresponding method.
     * Set shortcut key for undo and close game.
     * Extension: reverse move state
     * (For example, if player press 'LEFT', keeper goes right).
     * destroy walls
     * (Use W A S D to control keeper to destroy walls).</p>
     *
     * @param code key press
     */
    public void handleKey(KeyCode code) {
        // state pattern
        moveHandle reverseState = new reverseMove();
        moveHandle normalState = new normalMove();
        moveState moveReverse = new moveState(reverseState);
        moveState moveNormal = new moveState(normalState);
        switch (code) {
            case UP:
                if(isTest == 1) {
                    if(reverse) {
                        move(new Point(1, 0));
                        break;
                    }
                    move(new Point(-1, 0));
                    break;
                }
                if(reverse) {
                    moveReverse.goUp();
                    break;
                }
                moveNormal.goUp();
                break;

            case RIGHT:
                if(isTest == 1) {
                    if(reverse) {
                        move(new Point(0, -1));
                        break;
                    }
                    move(new Point(0, 1));
                    break;
                }
                if(reverse) {
                    moveReverse.goRight();
                    break;
                }
                moveNormal.goRight();
                break;

            case DOWN:
                if(isTest == 1) {
                    if(reverse) {
                        move(new Point(11, 0));
                        break;
                    }
                    move(new Point(1, 0));
                    break;
                }
                if(reverse) {
                    moveReverse.goDown();
                    break;
                }
                moveNormal.goDown();
                break;

            case LEFT:
                if(isTest == 1) {
                    if(reverse) {
                        move(new Point(0, 1));
                        break;
                    }
                    move(new Point(0, -1));
                    break;
                }
                if(reverse) {
                    moveReverse.goLeft();
                    break;
                }
                moveNormal.goLeft();
                break;

            case W:
                destroyWall(new Point(-1, 0));
                break;

            case S:
                destroyWall(new Point(1, 0));
                break;

            case A:
                destroyWall(new Point(0, -1));
                break;

            case D:
                destroyWall(new Point(0, 1));
                break;

            case ESCAPE:
                System.exit(0);
                break;

            case Z:
                moveBack();
                break;

            default:
                // TODO: implement something funny.
        }

        if (isDebugActive()) {
            System.out.println(code);
        }
    }

    /**
     * Keeper movement.
     *
     * <p>This method is used for handling keeper's movement.</p>
     *
     * @param delta movement
     */
    public void move(Point delta) {
        isHandle = 1;
        if (isGameComplete()) {
            return;
        }

        Point cratePosition = currentLevel.getCratePosition();
        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.objectsGrid.getGameObjectAt(keeperPosition);
        if(keeper == GameObject.KEEPER_ON_DIAMOND) {
            keeper = GameObject.KEEPER;
        }

        // store the first position of keeper
        if(positions.isEmpty()){
            positions.add((Point) keeperPosition.clone());
        }
        // target point
        Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.objectsGrid.getGameObjectAt(targetObjectPoint);

        if (GameEngine.isDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(currentLevel.toString());
            System.out.println("Keeper pos: " + keeperPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
        }

        setKeeperMoved(false);

        switch (keeperTarget) {
            case WALL:
                break;

            case CRATE:
                isCrateMoved = true;
                GameObject crateTarget = currentLevel.getTargetObject(targetObjectPoint, delta);
                if (crateTarget != GameObject.FLOOR) {
                    break;
                }

                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(targetObjectPoint, delta)), targetObjectPoint);
                currentLevel.objectsGrid.putGameObjectAt(keeperTarget, GameGrid.translatePoint(targetObjectPoint, delta));
                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(keeperPosition, delta)), keeperPosition);
                currentLevel.objectsGrid.putGameObjectAt(keeper, GameGrid.translatePoint(keeperPosition, delta));
                setKeeperMoved(true);

                int x = (int) (targetObjectPoint.getX() - cratePosition.getX());
                int y = (int) (targetObjectPoint.getY() - cratePosition.getY());

                if(crates.isEmpty() || Math.abs(x) + Math.abs(y)>1){
                    crates.add((Point) targetObjectPoint.clone());
                }

                cratePosition.translate((int) (x+delta.getX()), (int) (y+delta.getY()));
                break;

            case FLOOR:
                // put floor at the position before
                currentLevel.objectsGrid.putGameObjectAt(currentLevel.objectsGrid.getGameObjectAt(GameGrid.translatePoint(keeperPosition, delta)), keeperPosition);
                // put keeper at the position after
                currentLevel.objectsGrid.putGameObjectAt(keeper, GameGrid.translatePoint(keeperPosition, delta));
                setKeeperMoved(true);
                break;

            default:
                System.out.println(keeperTarget);
                logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }

        if (isKeeperMoved()) {
            // if keeper meets specified point, complete the level.
            if((targetObjectPoint.equals(new Point(11, 11)) && currentLevel.getIndex() == 1)
                    || (targetObjectPoint.equals(new Point(18, 18)) && currentLevel.getIndex() == 2)
                    || (targetObjectPoint.equals(new Point(10, 10)) && currentLevel.getIndex() == 3)
                    || (targetObjectPoint.equals(new Point(13, 18)) && currentLevel.getIndex() == 4)
                    || (targetObjectPoint.equals(new Point(18, 14)) && currentLevel.getIndex() == 5)
                    || (targetObjectPoint.equals(new Point(17, 9)) && currentLevel.getIndex() == 6)) {
                isBuff = true;
                levelComplete();
                return;
            }

            keeperPosition.translate((int) delta.getX(), (int) delta.getY());
            movesCount++;
            setEachLevelCount(getEachLevelCount() + 1);
            Point after = currentLevel.getKeeperPosition();
            positions.add((Point) after.clone());

            if(isCrateMoved) {
                if(keeperTarget == GameObject.CRATE) {
                    crates.add((Point) cratePosition.clone());
                } else {
                    crates.add((Point) cratePosition.clone());
                }
            }

            if (currentLevel.isComplete()) {
                levelComplete();
            }
        }
    }

    /**
     * Actions after each level complete.
     *
     * <p>Using Factory pattern to show rank.
     * Load the next level.
     * Clear lists which store keeper positions and crates positions.</p>
     */
    private void levelComplete() {
        if (isDebugActive()) {
            System.out.println("Level complete!");
        }

        // if player is using default levels
        // show rank after finishing one level
        // using factory pattern
        if(!gc.isLoad()) {
            int index = currentLevel.getIndex();
            try {
                Class c = Class.forName("com.ae2dms.model.rank.rank" + index);
                rank r = (rank) c.newInstance();
                r.rankLevel();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
        }

        currentLevel = getNextLevel();
        isCrateMoved = false;
        crates.clear();
        positions.clear();
        setEachLevelCount(0);
    }

    /**
     * Keeper and crate undo.
     */
    public void moveBack() {
        isUndo = 1;
        if (isGameComplete()) {
            return;
        }

        Point cratePosition = currentLevel.getCratePosition();
        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.objectsGrid.getGameObjectAt(keeperPosition);

        if(positions.size() >= 2) {
            Point previousPoint = positions.get(positions.size() - 2);
            positions.remove(positions.size() - 1);
            GameObject previous = currentLevel.objectsGrid.getGameObjectAt(previousPoint);

            if (GameEngine.isDebugActive()) {
                System.out.println("Current level state:");
                System.out.println(currentLevel.toString());
                System.out.println("Keeper pos: " + keeperPosition);
                System.out.println("Movement source obj: " + keeper);
            }

            currentLevel.objectsGrid.removeGameObjectAt(keeperPosition);
            currentLevel.objectsGrid.putGameObjectAt(previous, keeperPosition);
            currentLevel.objectsGrid.putGameObjectAt(GameObject.KEEPER, previousPoint);

            if(crates.size() >= 2) {
                Point previousCrate = crates.get(crates.size() - 2);
                int a = (int) (previousCrate.getX() - cratePosition.getX());
                int b = (int) (previousCrate.getY() - cratePosition.getY());
                if(Math.abs(a) + Math.abs(b) > 1) {
                    currentLevel.objectsGrid.putGameObjectAt(GameObject.CRATE, cratePosition);
                    crates.remove(crates.size() - 1);
                    crates.remove(crates.size() - 2);
                } else {
                    crates.remove(crates.size() - 1);
                    currentLevel.objectsGrid.putGameObjectAt(GameObject.FLOOR, cratePosition);
                    currentLevel.objectsGrid.putGameObjectAt(GameObject.CRATE, previousCrate);
                }

                int xc = (int) (previousCrate.getX() - cratePosition.getX());
                int yc = (int) (previousCrate.getY() - cratePosition.getY());
                cratePosition.translate(xc, yc);
            }

            int x = (int) (previousPoint.getX() - keeperPosition.getX());
            int y = (int) (previousPoint.getY() - keeperPosition.getY());

            keeperPosition.translate(x, y);
            undoNum++;
        } else {
            String title = "Notice!";
            String message = "It is the original position of keeper!\n";

            gc.newDialog(title, message, null, 150);
        }
    }

    /**
     * Reset current level.
     */
    public void moveFirst() {
        if (isGameComplete()) {
            return;
        }

        Point keeperPosition = currentLevel.getKeeperPosition();
        // if level objects has not changed
        if(positions.isEmpty()) {
            return;
        }
        Point firstKeeper = positions.get(0);
        allCrates = currentLevel.getFirstCrates();
        allWalls = currentLevel.getAllWalls();

        for(int col = 0; col < currentLevel.objectsGrid.COLUMNS; col++) {
            for(int row = 0; row < currentLevel.objectsGrid.ROWS; row++) {
                if(currentLevel.objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE || currentLevel.objectsGrid.getGameObjectAt(col, row) == GameObject.KEEPER || currentLevel.objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE_ON_DIAMOND) {
                    currentLevel.objectsGrid.putGameObjectAt(GameObject.FLOOR, col, row);
                }
            }
        }

        for(int i = 0; i < allCrates.size(); i++) {
            currentLevel.objectsGrid.putGameObjectAt(GameObject.CRATE, allCrates.get(i));
        }
        crates.clear();

        for(int j = 0; j < allWalls.size(); j++) {
            currentLevel.objectsGrid.putGameObjectAt(GameObject.WALL, allWalls.get(j));
        }

        currentLevel.objectsGrid.putGameObjectAt(GameObject.KEEPER, firstKeeper);
        int x = (int) (firstKeeper.getX() - keeperPosition.getX());
        int y = (int) (firstKeeper.getY() - keeperPosition.getY());
        keeperPosition.translate(x, y);
        resetNum++;
        setEachLevelCount(0);
        positions.clear();
    }

    /**
     * Sort all scores.
     *
     * <p>This method is used for sorting all existing scores.</p>
     *
     * @param scores a list of scores
     * @return a list of sorted scores
     */
    private List<String> sort(List<String> scores) {
        int len = scores.size();
        int s1, s2;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len - 1; j++) {
                s1 = Integer.parseInt(scores.get(j));
                s2 = Integer.parseInt(scores.get(j+1));
                if(s1 > s2) {
                    String temp = scores.get(j);
                    scores.set(j, scores.get(j+1));
                    scores.set(j+1, temp);
                }
            }
        }
        return scores;
    }

    /**
     * Read all scores from a file.
     *
     * @param file a file stores all scores
     * @return a list of all scores
     */
    private List<String> readRank(File file) {
        List<String> scores = new ArrayList<>();
        String score;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((score = reader.readLine()) != null) {
                scores.add(score);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }


    /**
     * Clear the file which stores all scores before write in new scores.
     * @param filename filepath of the file
     */
    private void clearTheFile(String filename) {
        FileWriter fwOb = null;
        try {
            fwOb = new FileWriter(filename, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        try {
            fwOb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Show rank after completing each level.
     * @param sorted a list of sorted scores not including current score
     */
    private void showRank(List<String> sorted) throws IOException {
        for(int i = 0; i < sorted.size(); i++) {
            int s = Integer.parseInt(sorted.get(i));
            if(s >= getEachLevelCount()) {
                rankNum = i + 1;
                break;
            }
        }

        gc.showRank();
    }

    /**
     * Store scores including current score into the file.
     * @param filename filepath
     * @param sorted a list of sorted scores
     * @throws IOException file may not exist
     */
    private void writeFile(String filename, List<String> sorted) throws IOException {
        File file = new File(filename);
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(file.length() == 0) {
            bw.write(Integer.toString(getEachLevelCount()));
        } else {
            clearTheFile(filename);
            for(int i = 0; i < sorted.size(); i++) {
                bw.write(sorted.get(i));
                bw.newLine();
            }
        }
        bw.close();

    }

    /**
     * Calculate the rank of current score and show rank.
     *
     * <p>This method put all related methods together
     * to show overall rank.</p>
     *
     * @param filepath filepath of rank file
     * @throws IOException file may not exist
     */
    public void ranking(String filepath) throws IOException {
        File file = new File(filepath);

        // read existing scores from file
        List<String> scores = readRank(file);
        // sort existing scores
        List<String> sorted = sort(scores);
        // add new score and sort
        sorted.add(Integer.toString(getEachLevelCount()));
        finalRank = sort(sorted);
        // show rank
        showRank(sorted);
        // write all scores into file
        writeFile(filepath, finalRank);
    }


    /**
     * Destroy walls (Additional feature).
     *
     * <p>If player destroy one wall, move count add 20.</p>
     *
     * @param delta move direction
     */
    private void destroyWall(Point delta) {
        isDestroy = 1;
        if (isGameComplete()) {
            return;
        }

        Point keeperPosition = currentLevel.getKeeperPosition();
        Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.objectsGrid.getGameObjectAt(targetObjectPoint);

        if (keeperTarget == GameObject.WALL) {
            currentLevel.objectsGrid.putGameObjectAt(GameObject.FLOOR, GameGrid.translatePoint(keeperPosition, delta));
            setEachLevelCount(getEachLevelCount() + 20);
            movesCount = movesCount + 20;
            destroyNum++;
        } else {
            move(delta);
        }
    }

    /**
     * Load game from file chosen by player.
     *
     * @param input input file
     * @return a list of levels
     */
    private List<Level> loadGameFile(InputStream input) {
        List<Level> levels = new ArrayList<>(5);
        int levelIndex = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";
            String storedCount = "0";

            while (true) {
                String line = reader.readLine();

                // there is a blank line between two levels
                if (line == null) {
                    if (rawLevel.size() != 0) {
                        // next level
                        Level parsedLevel = new Level(storedCount, levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                    // if the first line is blank
                }

                if(line.contains("MoveCount")) {
                    storedCount = line.replace("MoveCount: ", "");
                    continue;
                }

                if (line.contains("MapSetName")) {
                    mapSetName = line.replace("MapSetName: ", "");
                    continue;
                }

                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(storedCount, levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }

                    levelName = line.replace("LevelName: ", "");
                    continue;
                }

                line = line.trim();
                line = line.toUpperCase();
                if (line.matches(".*W.*W.*")) {
                    rawLevel.add(line);
                }
            }

        } catch (IOException e) {
            logger.severe("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            logger.severe("Cannot open the requested file: " + e);
        }

        return levels;
    }

    /**
     * Determine if game completes.
     *
     * @return true/false
     */
    public boolean isGameComplete() {
        return gameComplete;
    }

    /**
     * Get next level.
     *
     * @return next level
     */
    public Level getNextLevel() {
        if (currentLevel == null) {
            return levels.get(0);  // level 1
        }
        int currentLevelIndex = currentLevel.getIndex();
        if (currentLevelIndex < levels.size()) {
            return levels.get(currentLevelIndex);
        }
        // if no levels left, game complete
        gameComplete = true;
        return null;
    }

    /**
     * Get current level.
     * @return current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Get levels.
     * @return levels
     */
    public List<Level> getLevels() {
        return levels;
    }

    /**
     * Set debug.
     */
    public void toggleDebug() {
        debug = !debug;
    }

    /**
     * Set reverse.
     */
    public void isReverse() {
        reverse = !reverse;
    }

    /**
     * Get isHandle
     * @return isHandle
     */
    public int getIsHandle() {
        return isHandle;
    }

    /**
     * Set isTest.
     * @param isTest whether tests call this class
     */
    public void setIsTest(int isTest) {
        this.isTest = isTest;
    }

    /**
     * Get isDestroy.
     * @return whether use destroy walls.
     */
    public int getIsDestroy() {
        return isDestroy;
    }

    /**
     * Get isUndo.
     * @return whether use undo
     */
    public int getIsUndo() {
        return isUndo;
    }

    /**
     * Get keeperMoved.
     * @return whether keeper has moved
     */
    public boolean isKeeperMoved() {
        return keeperMoved;
    }

    /**
     * Set keeprMoved.
     * @param keeperMoved whether keeper has moved
     */
    public void setKeeperMoved(boolean keeperMoved) {
        this.keeperMoved = keeperMoved;
    }

    /**
     * Get isBuff.
     *
     * @return isBuff
     */
    public boolean isBuff() {
        return isBuff;
    }

    /**
     * Set isBuff.
     * @param buff true/false
     */
    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    /**
     * Get real-time move count for current level.
     * @return move count
     */
    public int getEachLevelCount() {
        return eachLevelCount;
    }

    /**
     * Set real-time move count for current level.
     * @param eachLevelCount move count
     */
    public void setEachLevelCount(int eachLevelCount) {
        this.eachLevelCount = eachLevelCount;
    }
}