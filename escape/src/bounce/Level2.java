package bounce;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Level2 extends Level {
  @Override
  public int getID() {
    return 2;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
      super.init(gameContainer, stateBasedGame);
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
    EscapeGame eg = (EscapeGame) stateBasedGame;

    eg.player.setPosition(2 * eg.TileSize + eg.TileSize / 2, 3 * eg.TileSize + eg.TileSize / 2);
    map = new TileMap(EscapeGame.LEVEL2MAP_IMG_RSC);
    super.enter(gameContainer, stateBasedGame);
    enemies.add(new Enemy(20 * eg.TileSize - eg.TileSize / 2, 15 * eg.TileSize - eg.TileSize / 2, 0.08f));
    enemies.add(new Enemy(9 * eg.TileSize - eg.TileSize / 2, 26 * eg.TileSize - eg.TileSize / 2, 0.07f));
    enemies.add(new Enemy(23 * eg.TileSize - eg.TileSize / 2, 4 * eg.TileSize - eg.TileSize / 2, 0.07f));
    enemies.add(new Enemy(27 * eg.TileSize - eg.TileSize / 2, 12 * eg.TileSize - eg.TileSize / 2, 0.07f));
    nextState = EscapeGame.GAMEOVERSTATE;
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
