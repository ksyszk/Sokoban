package com.ae2dms.model;

import com.ae2dms.controller.settingController;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

/**
 * Set images to game objects.
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class GraphicObject extends ImageView {
    /**
     * Image of objects.
     */
    public ImageView iv = new ImageView();

    /**
     * Set images to different objects.
     * @param obj game objects
     */
    public GraphicObject(GameObject obj) {
        int floor = settingController.getChosenFloor();
        int wall = settingController.getChosenWall();
        int keeper = settingController.getChosenKeeper();
        int crate = settingController.getChosenCrate();

        switch (obj) {
            case WALL:
                if(wall == 1 && floor == 1) {
                    iv.setImage(ImageLoader.getImageWall());
                } else if(wall == 1 && floor == 2) {
                    iv.setImage(ImageLoader.getImageWall3());
                } else {
                    iv.setImage(ImageLoader.getImageWall2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);
                break;

            case CRATE:
                if(crate == 1 && floor == 1) {
                    iv.setImage(ImageLoader.getImageCrate());
                } else if(crate == 1 && floor == 2) {
                    iv.setImage(ImageLoader.getImageCrate3());
                } else if(crate == 2 && floor == 1) {
                    iv.setImage(ImageLoader.getImageCrate4());
                } else {
                    iv.setImage(ImageLoader.getImageCrate2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);
                break;

            case DIAMOND:
                if(keeper == 1 && floor == 1) {
                    iv.setImage(ImageLoader.getImageDiamond());
                } else if(keeper == 1 && floor == 2) {
                    iv.setImage(ImageLoader.getImageDiamond4());
                } else if(keeper == 2 && floor == 1) {
                    iv.setImage(ImageLoader.getImageDiamond3());
                } else {
                    iv.setImage(ImageLoader.getImageDiamond2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);

                if (GameEngine.isDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }
                break;

            case KEEPER:
                if(keeper == 1 && floor == 1) {
                    iv.setImage(ImageLoader.getImageKeeper());
                } else if(keeper == 1 && floor == 2) {
                    iv.setImage(ImageLoader.getImageKeeper3());
                } else if(keeper == 2 && floor == 1) {
                    iv.setImage(ImageLoader.getImageKeeper4());
                } else {
                    iv.setImage(ImageLoader.getImageKeeper2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);
                break;

            case FLOOR:
                if(floor == 1) {
                    iv.setImage(ImageLoader.getImageFloor());
                } else {
                    iv.setImage(ImageLoader.getImageFloor2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);
                break;

            case CRATE_ON_DIAMOND:
                if(crate == 1 && floor == 1) {
                    iv.setImage(ImageLoader.getImageCrateOnDiamond());
                } else if(crate == 1 && floor == 2) {
                    iv.setImage(ImageLoader.getImageCrateOnDiamond3());
                } else if(crate == 2 && floor == 1) {
                    iv.setImage(ImageLoader.getImageCrateOnDiamond4());
                } else {
                    iv.setImage(ImageLoader.getImageCrateOnDiamond2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);
                break;

            case KEEPER_ON_DIAMOND:
                if(keeper == 1) {
                    iv.setImage(ImageLoader.getImageKeeperOnDiamond());
                } else {
                    iv.setImage(ImageLoader.getImageKeeperOnDiamond2());
                }
                iv.setFitHeight(30);
                iv.setFitWidth(30);
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameEngine.logger.severe(message);
                throw new AssertionError(message);
        }
    }
}


