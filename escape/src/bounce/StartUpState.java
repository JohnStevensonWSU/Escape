package bounce;

import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartUpState extends BasicGameState {

    public StartUpState () {
        super();
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;

        eg.player.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        boolean down = false, up = false, left = false, right = false;
        float vy = 0f, vx = 0f;

        eg.player.setVelocity(new Vector(0f, 0f));
        if (input.isKeyDown(Input.KEY_DOWN)) {
            down = true;
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            up = true;
        }

        if (input.isKeyDown(Input.KEY_LEFT)) {
            left = true;
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            right = true;
        }

        if (up) {
            vy = -1f;
        }
        if (down) {
            vy = 1f;
        }
        if (left) {
            vx = -1f;
        }
        if (right) {
            vx = 1f;
        }

        eg.player.setVelocity(new Vector(vx, vy));

        eg.player.update(i);

    }
}
