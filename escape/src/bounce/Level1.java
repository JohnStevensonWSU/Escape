package bounce;

import jig.ResourceManager;
import jig.ResourceManager;
import jig.Vector;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.Layer;

public class Level1 extends Level {
  @Override
  public int getID() {
    return 1;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
      super.init(gameContainer, stateBasedGame);
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
    EscapeGame eg = (EscapeGame) stateBasedGame;

    eg.player.setPosition(2 * eg.TileSize + eg.TileSize / 2, 3 * eg.TileSize + eg.TileSize / 2);
    map = new TileMap(EscapeGame.LEVEL1MAP_IMG_RSC);
    super.enter(gameContainer, stateBasedGame);
    enemies.add(new Enemy(13 * eg.TileSize - eg.TileSize / 2, 19 * eg.TileSize - eg.TileSize / 2, 0.05f));
    enemies.add(new Enemy(17 * eg.TileSize - eg.TileSize / 2, 5 * eg.TileSize - eg.TileSize / 2, 0.08f));
    enemies.add(new Enemy(21 * eg.TileSize - eg.TileSize / 2, 17 * eg.TileSize - eg.TileSize / 2, 0.07f));
    nextState = EscapeGame.LEVEL_2;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    super.render(gameContainer, stateBasedGame, graphics);
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
   super.update(gameContainer, stateBasedGame, delta);
  }

  public int getNextState() { return this.nextState; }

}
