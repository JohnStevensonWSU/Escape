package bounce;

import jig.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {
    private int timer;
    private int nextState = 0;

    public GameOverState() {
        super();
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        timer = 5000;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        if (eg.didWin) {
          graphics.drawString("You Win!",eg.ScreenWidth / 2 - 47, eg.ScreenHeight / 4);
        } else {
          graphics.drawString("Game Over",eg.ScreenWidth / 2 - 55, eg.ScreenHeight / 4);
        }
        graphics.drawString("Time: " + String.format("%02d", eg.runTime / 60000) + ":" +
            String.format("%02d", eg.runTime % 60000 / 1000) + ":" +
            String.format("%02d", eg.runTime % 1000 / 10),
            eg.ScreenWidth / 2 - 75, eg.ScreenHeight / 4 + 50);

        graphics.drawString("Art Credits to:", eg.ScreenWidth / 2 - 80, eg.ScreenHeight / 2);
        graphics.drawString("Wulax (OpenGameArt) -- Character Animations", eg.ScreenWidth / 2 - 220, eg.ScreenHeight / 2 + 40);
        graphics.drawString("Buch (OpenGameArt) -- Dungeon Art", eg.ScreenWidth / 2 - 180, eg.ScreenHeight / 2 + 60);
//        for (int i = 0; i < eg.ScreenWidth; i++) {
//          for (int j = 0; j < eg.ScreenHeight; j++) {
//            if (i == (int) eg.ScreenWidth / 2) {
//              graphics.drawString(".", i, j);
//            }
//            if (j == (int) eg.ScreenHeight / 2) {
//              graphics.drawString(".", i, j);
//            }
//          }
//        }
    }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if (timer < 0) {
            stateBasedGame.enterState(nextState);
        }

        timer -= delta;
    }
}
