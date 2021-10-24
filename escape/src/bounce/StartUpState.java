package bounce;

import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartUpState extends BasicGameState {
    int nextState = EscapeGame.LEVEL_1;

    public StartUpState () {
        super();
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.drawImage(ResourceManager.getImage(EscapeGame.PRESSSPACE_IMG_RSC), eg.ScreenWidth / 2 - 187 / 2, eg.ScreenHeight / 2 + 100);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            stateBasedGame.enterState(nextState);
        }
    }
}
