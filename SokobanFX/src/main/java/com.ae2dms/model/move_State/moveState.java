package com.ae2dms.model.move_State;

/**
 * Keeper movement in different state (State pattern).
 *
 * <p>State pattern.</p>
 * @author Sijin WANG
 * @since 24 November 2020
 */
public class moveState {
    private moveHandle state;

    /**
     * Set state.
     * @param state state(normal/reverse)
     */
    public moveState(moveHandle state) {
        this.state = state;
    }

    /**
     * Move up.
     */
    public void goUp() {
        state.goUp();
    }

    /**
     * Move down.
     */
    public void goDown() {
        state.goDown();
    }

    /**
     * Move left.
     */
    public void goLeft() {
        state.goLeft();
    }

    /**
     * Move right.
     */
    public void goRight() {
        state.goRight();
    }
}
