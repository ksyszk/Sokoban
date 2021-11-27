package com.ae2dms.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Each record in the rank.
 *
 * @author Sijin WANG
 * @since 1 December 2020
 */
public class record {
    private final SimpleStringProperty num;
    private final SimpleStringProperty move;
    private final SimpleStringProperty score;

    /**
     * To store rank, steps and score
     *
     * @param num rank
     * @param move move count
     * @param score score
     */
    public record(String num, String move, String score) {
        this.num = new SimpleStringProperty(num);
        this.move = new SimpleStringProperty(move);
        this.score = new SimpleStringProperty(score);
    }

    /**
     * Get num
     * @return num
     */
    public String getNum() {
        return num.get();
    }

    /**
     * Get move
     * @return move
     */
    public String getMove() {
        return move.get();
    }

    /**
     * Get score
     * @return score
     */
    public String getScore() {
        return score.get();
    }

    /**
     * Set num
     * @param newNum num
     */
    public void setNum(String newNum) {
        num.set(newNum);
    }

    /**
     * Set move
     * @param newMove move
     */
    public void setMove(String newMove) {
        num.set(newMove);
    }

    /**
     * Set score
     * @param newScore score
     */
    public void setScore(String newScore) {
        num.set(newScore);
    }
}
