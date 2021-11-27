package com.ae2dms.controller;

import com.ae2dms.model.*;

import java.awt.*;
import java.io.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.sampled.*;

/**
 * Controls the game page which shows game grid
 *
 * <p>This class delivers the functionality of loading game file,
 * loading game objects, handling player's actions
 * and applying all features listed in the menu</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class gameController {
    /**
     * A grid shows the game page.
     */
    public GridPane game;

    /**
     * Create a new stage to show the game.
     */
    private Stage primaryStage = new Stage();

    /**
     * Use static game engine.
     */
    private static GameEngine gameEngine;

    /**
     * Determine whether save the first level.
     */
    private int saveNum = 0;

    /**
     * Determine whether used load game feature.
     */
    public static boolean isLoad = false;

    /**
     * Determine whether used destroy wall for 10 times.
     */
    public static boolean isAchieveDestroy = false;

    /**
     * Determine whether used undo for 10 times.
     */
    public static boolean isAchieveUndo = false;

    /**
     * Determine whether used reset feature for 3 times.
     */
    public static boolean isAchieveReset = false;

    /**
     * Determine whether pirst time play music.
     */
    public static boolean isFirstMusic = true;

    private Clip clip;

    {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML // fx:id="saveGame"
    private MenuItem saveGame; // Value injected by FXMLLoader

    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader

    @FXML // fx:id="moveCount"
    private Label moveCount; // Value injected by FXMLLoader

    @FXML // fx:id="image1"
    private ImageView image1; // Value injected by FXMLLoader

    @FXML // fx:id="image2"
    private ImageView image2; // Value injected by FXMLLoader

    @FXML // fx:id="image3"
    private ImageView image3; // Value injected by FXMLLoader

    @FXML // fx:id="levelName"
    private Label levelName; // Value injected by FXMLLoader

    public gameController() {
    }

    /**
     * Update move count get from {@link GameEngine} and show on the top of the page.
     *
     * <p>This method shows move count by updating text in {@code moveCount}.
     * The count will be updated after every movement.</p>
     */
    private void updateMoveCount() {
        int count = gameEngine.getEachLevelCount();
        moveCount.setText(String.valueOf(count));
    }

    /**
     * Update lavel name get from {@link GameEngine} and show on the top of the page.
     */
    private void updateLevelName() {
        String name = gameEngine.getCurrentLevel().getName();
        levelName.setText(name);
    }

    /**
     * Update image of golden cups on the right top corner of the page.
     *
     * <p>After getting an achievement,
     * the corresponding cup will be shown.</p>
     */
    private void updateAchieve() {
        if(isAchieveDestroy) {
            image1.setImage(ImageLoader.getImageAchievement());
        }

        if (isAchieveUndo()) {
            image2.setImage(ImageLoader.getImageAchievement());
        }

        if(isAchieveReset()) {
            image3.setImage(ImageLoader.getImageAchievement());
        }
    }

    /**
     * Show achievement if conditions met (Additional feature).
     *
     * <p>Determine the conditions of each achievement have been met.
     * If player destroys 10 walls, the first achievement get.
     * If player undo for 10 times, the second achievement get.
     * If player reset for 3 times, the third achievement get.
     * If player load his/her own game file, there will be no rank.</p>
     */
    private void achievement() throws IOException {
        if(gameEngine.destroyNum == 10 && !isAchieveDestroy) {
            showAchievementDestroy();
            isAchieveDestroy = true;
        } else if(gameEngine.undoNum == 10 && !isAchieveUndo()) {
            showAchievementUndo();
            isAchieveUndo = true;
        } else if(gameEngine.resetNum == 3 && !isAchieveReset()) {
            showAchievementReset();
            isAchieveReset = true;
        }
    }

    /**
     * Reload game after each movement
     *
     * <p>Before game complete,
     * this method is mainly used for reload {@code gameGrid}.
     * And it also calls {@code achievement()} {@code updateMoveCount()}
     * {@code updateAchieve} to show real-time move count and achievement.</p>
     */
    void reloadGrid() {
        if (gameEngine.isGameComplete()) {
            showVictoryMessage();
            return;
        }

        // show achievement
        try {
            achievement();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(gameEngine.isBuff()) {
            try {
                showBuff();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameEngine.setBuff(false);
        }

        Level currentLevel = gameEngine.getCurrentLevel();
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();

        gameGrid.getChildren().clear(); // clear all children

        while (levelGridIterator.hasNext()) {
            addObjectToGrid(levelGridIterator.next(), levelGridIterator.getCurrentPosition());
        }

        updateMoveCount();
        updateAchieve();
        updateLevelName();
        gameGrid.autosize();
        primaryStage.sizeToScene();
    }

    /**
     * Add corresponding object to specified position.
     *
     * @param gameObject the property of an object such as WALL, FLOOR.
     * @param location the coordinate of the point which will be set object
     */
    private void addObjectToGrid(GameObject gameObject, Point location) {
        GraphicObject graphicObject = new GraphicObject(gameObject);
        gameGrid.add(graphicObject.iv, location.y, location.x);
    }

    /**
     * Save game file(*.skb) to local.
     *
     * <p>This method deliver the functionality of saving current level
     * and following levels.</p>
     *
     * @throws IOException the chosen file may not be exist
     */
    @FXML
    void saveGame() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sokoban save file", "*.skb");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        String exportFilePath = file.getAbsolutePath();

        Level currentLevel = gameEngine.getCurrentLevel();
        int index = currentLevel.getIndex();
        writeFile(currentLevel, new File(exportFilePath), false);

        // save the following levels
        while (index < gameEngine.getLevels().size()){
            currentLevel = gameEngine.getLevels().get(index);
            writeFile(currentLevel, new File(exportFilePath), true);
            index++;
        }
    }

    /**
     * Write levels to the chosen file.
     *
     * @param currentLevel current level
     * @param exportFilePath the file path which player want to save file
     * @param newLine if newLine is true, it is the start of a level
     * @throws IOException file may not be exist
     */
    private void writeFile(Level currentLevel, File exportFilePath, boolean newLine) throws IOException {
        int symbolNum = 0;
        String[][] obj = new String[20][20];
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFilePath, true));

        if(saveNum == 0){
            bw.write("MapSetName: Example Game!");
            bw.newLine();
            bw.write("LevelName: " + currentLevel.getName());
            bw.newLine();
            bw.write("MoveCount: " + gameEngine.getEachLevelCount());
            bw.newLine();
        }

        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();
        if(newLine) {
            bw.newLine();
            bw.newLine();
            bw.write("LevelName: " + currentLevel.getName());
            bw.newLine();
        }
        while (levelGridIterator.hasNext()) {
            int i, j;
            for(i = 0; i < 20; i++) {
                for(j = 0; j < 20; j++) {
                    obj[j][i] = levelGridIterator.next().getStringSymbol();
                }
            }
            for(i = 0; i < 20; i++) {
                for(j = 0; j < 20; j++) {
                    if (symbolNum < 20) {
                        bw.write(obj[i][j]);
                        symbolNum++;
                    } else {
                        bw.newLine();
                        bw.write(obj[i][j]);
                        symbolNum = 1;
                    }
                }
            }
        }
        saveNum++;
        bw.flush();
        bw.close();
    }

    /**
     * Call {@code loadGameFile()} to load file.
     *
     * @throws IOException file may not exist
     */
    public void loadGame() throws IOException {
        isLoad = true;
        loadGameFile();
    }

    /**
     * Load game file from local.
     *
     * <p>Player can choose the path and file thay want to load
     * by using file chooser.</p>
     *
     * @throws IOException file may not exist
     */
    private void loadGameFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        File saveFile = fileChooser.showOpenDialog(primaryStage);

        if (saveFile != null) {
            if (GameEngine.isDebugActive()) {
                GameEngine.logger.info("Loading save file: " + saveFile.getName());
            }
            initializeGame(new FileInputStream(saveFile));
        }
    }

    /**
     * Close the game.
     */
    public void closeGame() {
        System.exit(0);
    }

    /**
     * Undo.
     *
     * <p>Call {@code moveBack()} from {@link GameEngine}.</p>
     */
    public void undo() {
        gameEngine.moveBack();
        reloadGrid();
    }

    /**
     * Play/Stop music.
     *
     * <p>Call {@code toggleMusic()} from {@link MusicPlayer}</p>
     */
    public void toggleMusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File path = new File(System.getProperty("user.dir")  + "/src/main/resources/music/WingsOfPiano.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(path);
        if(isFirstMusic) {
            clip.open(audioInput);
            isFirstMusic = false;
        }

        MusicPlayer mp = new MusicPlayer();
        mp.toggleMusic(clip);
    }

    /**
     * Start/Stop debug.
     *
     * <p>Call {@code toggleDebug()} from {@link GameEngine}.</p>
     */
    public void toggleDebug() {
        gameEngine.toggleDebug();
        reloadGrid();
    }

    /**
     * Reset current level.
     *
     * <p>Call {@code moveFirst()} from {@link GameEngine}.</p>
     */
    public void resetLevel() {
        gameEngine.moveFirst();
        reloadGrid();
    }

    /**
     * Show something about this game.
     */
    public void showAbout() {
        SingleObjectShowAbout objectShowAbout = SingleObjectShowAbout.getInstance();
        objectShowAbout.showAbout();
    }

    /**
     * Load default game file and initialize game.
     *
     * <p>Load game file, call {@code initializeGame(in)} to initialize game,
     * and call {@code setEventFilter()} to input key press.</p>
     *
     * @param primaryStage stage that perform the game
     * @param gameGrid grid that show the game
     * @throws IOException file may not exist
     */
    public void loadDefaultSaveFile(Stage primaryStage, GridPane gameGrid) throws IOException {
        this.gameGrid = gameGrid;
        this.primaryStage = primaryStage;
        InputStream in = getClass().getClassLoader().getResourceAsStream("level/game.skb");
        initializeGame(in);
        setEventFilter();   // input key press
    }

    /**
     * Initialize game.
     *
     * <p>Create a new game engine used for playing game.</p>
     *
     * @param input default game file
     */
    void initializeGame(InputStream input){
        gameEngine = new GameEngine(input, true);
        gameEngine.setEachLevelCount(Integer.parseInt(gameEngine.getCurrentLevel().getCount()));
        reloadGrid();
    }

    /**
     * Get key press and pass to {@link GameEngine}.
     */
    private void setEventFilter() {
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            gameEngine.handleKey(event.getCode());
            reloadGrid();  // reload after move one step
        });
    }

    /**
     * After completing all levels,
     * a new stage contains victory message will be shown.
     */
    void showVictoryMessage() {
        String dialogTitle = "Game Over!";
        String dialogMessage = "You completed " + gameEngine.mapSetName + " in " + gameEngine.movesCount + " moves!";
        MotionBlur mb = new MotionBlur(2, 3);

        newDialog(dialogTitle, dialogMessage, mb, 150);
    }

    /**
     * Create a new stage.
     *
     * @param dialogTitle the title of the stage
     * @param dialogMessage the message on the stage
     * @param dialogMessageEffect the effect of the message
     * @param height the height of the stage
     */
    public void newDialog(String dialogTitle, String dialogMessage, Effect dialogMessageEffect, int height) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle(dialogTitle);

        Text text1 = new Text(dialogMessage);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(javafx.scene.text.Font.font(14));

        if (dialogMessageEffect != null) {
            text1.setEffect(dialogMessageEffect);
        }

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setBackground(Background.EMPTY);
        dialogVbox.getChildren().add(text1);

        Scene dialogScene = new Scene(dialogVbox, 350, height);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Show portal message (Additional feature).
     *
     * <p>If player reach some specified point,
     * an invisible portal will send keeper to the end of level.</p>
     */
    void showBuff() throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle("Congratulations!");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/portal.fxml"));
        Parent r = loader.load();
        Scene scene = new Scene(r);
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Show rank after completing each level
     */
    public void showRank() throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle("High score Pop-up");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/rank.fxml"));
        Parent r = loader.load();
        Scene scene = new Scene(r);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("rank.css").toExternalForm());
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Show achievement of destroying walls.
     *
     * <p>If player destroys 10 walls, a golden cup will be shown.</p>
     */
    void showAchievementDestroy() throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle("Achievement");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/achieve2.fxml"));
        Parent r = loader.load();
        Scene scene = new Scene(r);
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Show achievement of undo.
     *
     * <p>If player undo for 10 times, a golden cup will be shown.</p>
     */
    void showAchievementUndo() throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle("Achievement");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/achieve1.fxml"));
        Parent r = loader.load();
        Scene scene = new Scene(r);
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Show achievement of reset.
     *
     * <p>If player reset for 3 times, a golden cup will be shown.</p>
     */
    void showAchievementReset() throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle("Achievement");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/achieve3.fxml"));
        Parent r = loader.load();
        Scene scene = new Scene(r);
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Open/Close reverse move state (Additional feature).
     *
     * <p>Call {@code isReverse()} from {@link GameEngine}.
     * Player can switch to reverse move state.</p>
     */
    public void Reverse() {
        gameEngine.isReverse();
        reloadGrid();
    }

    /**
     * Get isLoad.
     *
     * <p>isLoad demonstrate whether player has loaded his/her own game file.</p>
     *
     * @return isLoad
     */
    public boolean isLoad() {
        return isLoad;
    }

    /**
     * Get current game engine.
     *
     * @return gameEngine
     */
    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Show all the achievements player has got.
     * @param mouseEvent mouse click
     */
    public void showAllAchieve(MouseEvent mouseEvent) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle("Achievements");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/achievement.fxml"));
        Parent r = loader.load();
        Scene scene = new Scene(r);
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Get whether undo 10 times.
     * @return true/false
     */
    public boolean isAchieveUndo() {
        return isAchieveUndo;
    }

    /**
     * Get whether reset 3 times.
     * @return true/false
     */
    public boolean isAchieveReset() {
        return isAchieveReset;
    }
}

