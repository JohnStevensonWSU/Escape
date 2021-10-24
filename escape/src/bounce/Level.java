package bounce;

import com.sun.org.apache.xalan.internal.xsltc.dom.ExtendedSAX;
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

public class Level extends BasicGameState {
    protected int nextState;
    protected TileMap map;
    protected final boolean debug = false;
    protected Collidable[][] terrain;
    protected Enemy enemy;
    protected Dijkstras dijkstras;
    protected int tileWidth;
    protected int tileHeight;
    protected Collidable escapePoint;

    @Override
    public int getID() {
        return -1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
      EscapeGame eg = (EscapeGame) stateBasedGame;

      this.tileWidth = map.getWidth();
      this.tileHeight = map.getHeight();
      this.terrain = new Collidable[tileWidth][tileHeight];
      Layer collidables= map.getLayer("Collidable");
      Layer objects = map.getLayer("Objects");
      int collidable, object;
      this.enemy = new Enemy(20 * eg.TileSize, 20 * eg.TileSize, 0.08f);
      escapePoint = new Collidable(10,31);

        for (int i = 0; i < map.getWidth(); i++) {
          for (int j = 0; j < map.getHeight(); j++) {
            collidable = collidables.data[i][j][2];
            object = objects.data[i][j][2];
            if (object == 416) {
              escapePoint = new Collidable(i,j);
            }
            if (collidable != 0) {
              this.terrain[i][j] = new Collidable(i, j);
            }
          }
        }
        dijkstras = new Dijkstras(tileWidth, tileHeight, (int) eg.player.getX() / eg.TileSize, (int) eg.player.getY() / eg.TileSize, this.terrain);
        eg.player.setCollidables(terrain);

        eg.player.resetHealth();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.drawString("Level", 10, 30);
        int bgIndex = map.getLayerIndex("Background");
        int fgIndex = map.getLayerIndex("Foreground");
        int objectIndex = map.getLayerIndex("Objects");
        int collidableIndex = map.getLayerIndex("Collidable");

//        map.render(0,0);
        map.render(0, 0, bgIndex);
        map.render(0, 0, fgIndex);
        map.render(0,0,objectIndex);
        if (debug) {
            map.render(0, 0, collidableIndex);
        }

//        for (int i = 0; i < this.tileWidth; i++) {
//            for (int j = 0; j < this.tileHeight; j++) {
//                if (dijkstras.getValue(i,j) != 2147483647) {
//                    graphics.drawString(String.valueOf(dijkstras.getValue(i, j)),
//                            i * eg.TileSize + 8,
//                            j * eg.TileSize + 8);
//                }
//            }
//        }

        enemy.render(graphics);
//        escapePoint.render(graphics);
        eg.player.render(graphics);

        for (int i = 0; i <= eg.player.getHealth(); i++) {
            graphics.drawImage(ResourceManager.getImage(EscapeGame.HEART_IMG_RSC), 40 + 32 * i, 900);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        int px = (int) eg.player.getX() / eg.TileSize, py = (int) eg.player.getY() / eg.TileSize;

        if (input.isKeyPressed(Input.KEY_X)) {
          eg.enterState(nextState);
        }
        dijkstras.update(px,py);
        eg.player.checkObject(enemy);
        if (eg.player.getIsDead()) {
            stateBasedGame.enterState(EscapeGame.GAMEOVERSTATE);
        }

        if (eg.player.checkObject(escapePoint)) {
            stateBasedGame.enterState(nextState);
        }

        if (eg.player.isBouncing) {
            for (Collidable[] row : terrain) {
                for (Collidable cell : row) {
                    if (cell != null) {
                        eg.player.checkObject(cell);
                    }
                }
            }
        }
        enemy.update(dijkstras, delta);
        eg.player.update(input, delta);
    }

    public int getNextState() { return this.nextState; }

}
