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

        if (input.isKeyDown(Input.KEY_DOWN)) {
            eg.player.setVelocity(new Vector(0f, 1f));
        } else if (input.isKeyDown(Input.KEY_UP)) {
            eg.player.setVelocity(new Vector(0f, -1f));
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            eg.player.setVelocity(new Vector(-1f, 0f));
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            eg.player.setVelocity(new Vector(1f, 0f));
        }

        eg.player.update(i);

    }
}
