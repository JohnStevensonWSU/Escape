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
        EscapeGame eg = (EscapeGame) stateBasedGame;

        eg.runTime = 0;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.drawString("Escape!",eg.ScreenWidth / 2 - 40, eg.ScreenHeight / 4);
        graphics.drawString("By John Stevenson",eg.ScreenWidth / 2 - 92, eg.ScreenHeight / 4 + 30);
        graphics.drawString("Press Space to Start...",eg.ScreenWidth / 2 - 105, eg.ScreenHeight / 2);
//      for (int i = 0; i < eg.ScreenWidth; i++) {
//        for (int j = 0; j < eg.ScreenHeight; j++) {
//          if (i == (int) eg.ScreenWidth / 2) {
//            graphics.drawString(".", i, j);
//          }
//          if (j == (int) eg.ScreenHeight / 2) {
//            graphics.drawString(".", i, j);
//          }
//        }
//      }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            stateBasedGame.enterState(nextState);
        }
    }
}
