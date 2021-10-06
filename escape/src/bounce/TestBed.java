package bounce;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TestBed extends Level {

    @Override
    public int getID() { return -1; }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        super.render(gameContainer, stateBasedGame, graphics);
        graphics.drawString("Hello World!", 100, 100);
    }
}
