package com.ae2dms.model;

import javafx.scene.media.AudioClip;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Play and stop music.
 *
 * <p>This class is used for playing and stopping music.</p>
 *
 * @author Sijin WANG
 * @since 26 October 2020
 */
public class MusicPlayer {
    private Clip clip;

    //AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/music/puzzle_theme.wav").toUri().toString());
    //AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/music/EndlessDaydream.mp3").toUri().toString());

    /**
     * Control play or stop music.
     *
     * <p>If there is no music is playing, play music.
     * Otherwise, stop the current music.</p>
     */
    public void toggleMusic(Clip clip) {
        this.clip = clip;
        if (!clip.isActive()) {
            playMusic();
        } else {
            stopMusic();
        }
    }

    /**
     * Play music.
     *
     * <p>And set {@code musicFlag} to true.</p>
     */
    public void playMusic() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stop music.
     *
     * <p>And set {@code musicFlag} to false.</p>
     */
    public void stopMusic() {
        clip.stop();
    }
}
