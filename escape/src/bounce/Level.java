package bounce;

import com.sun.org.apache.xalan.internal.xsltc.dom.ExtendedSAX;
import jig.ResourceManager;
import jig.Vector;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.Layer;

import javax.annotation.Resource;
import java.util.ArrayList;

public class Level extends BasicGameState {
    protected int nextState;
    protected TileMap map;
    protected final boolean debug = false;
    protected Collidable[][] terrain;
    protected ArrayList<Enemy> enemies;
    protected Dijkstras dijkstras;
    protected int tileWidth;
    protected int tileHeight;
    protected Collidable escapePoint;
    protected Boolean isStarted;

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
      Input input = gameContainer.getInput();
      enemies = new ArrayList<Enemy>();
      eg.player.moveStill();

      this.tileWidth = map.getWidth();
      this.tileHeight = map.getHeight();
      this.terrain = new Collidable[tileWidth][tileHeight];
      Layer collidables= map.getLayer("Collidable");
      Layer objects = map.getLayer("Objects");
      int collidable, object;

        for (int i = 0; i < map.getWidth(); i++) {
          for (int j = 0; j < map.getHeight(); j++) {
            collidable = collidables.data[i][j][2];
            object = objects.data[i][j][2];
            if (object == 176) {
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
        isStarted = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.setAntiAlias(true);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA_SATURATE, GL11.GL_ONE);
        int px = (int) (eg.player.getX() / eg.TileSize), py = (int) (eg.player.getY() / eg.TileSize);
        int bgIndex = map.getLayerIndex("Background");
        int fgIndex = map.getLayerIndex("Foreground");
        int objectIndex = map.getLayerIndex("Objects");
        int collidableIndex = map.getLayerIndex("Collidable");

//        for (int i = 0; i < this.tileWidth; i++) {
//            for (int j = 0; j < this.tileHeight; j++) {
//                if (dijkstras.getValue(i,j) != 2147483647) {
//                    graphics.drawString(String.valueOf(dijkstras.getValue(i, j)),
//                            i * eg.TileSize + 8,
//                            j * eg.TileSize + 8);
//                }
//            }
//        }

        graphics.drawString("Time: " + String.format("%02d", eg.runTime / 60000) + ":" +
                        String.format("%02d", eg.runTime % 60000 / 1000) + ":" +
                        String.format("%02d", eg.runTime % 1000 / 10),
                10, 5);
        for (int i = 0; i <= eg.player.getHealth(); i++) {
            graphics.drawImage(ResourceManager.getImage(EscapeGame.HEART_IMG_RSC), 32 + 32 * i, 25);
        }
        if (!isStarted) {
            graphics.drawString("Press Space to Start...",eg.ScreenWidth / 2 - 105, eg.ScreenHeight / 2);
        }

        eg.player.render(graphics);

        for (int i = 0; i < tileWidth; i++) {
            for (int j = 0; j < tileHeight; j++) {
                if (i > px + 4 || i < px - 4 || j < py - 4 || j > py + 4) {
                    graphics.drawImage(ResourceManager.getImage(EscapeGame.FOG_IMG_RSC), i * eg.TileSize, j * eg.TileSize);
                }
            }
        }

        for (Enemy enemy : enemies) {
            enemy.render(graphics);
        }

        map.render(0,0,objectIndex);
        if (debug) {
            map.render(0, 0, collidableIndex);
        }
        map.render(0, 0, fgIndex);
        map.render(0, 0, bgIndex);

//        map.render(0,0);



//        escapePoint.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_X)) {
            eg.enterState(nextState);
        }

      if (!isStarted) {
          if (input.isKeyPressed(Input.KEY_SPACE)) {
              isStarted = true;
          } else {
            return;
          }
        }
        int px = (int) eg.player.getX() / eg.TileSize, py = (int) eg.player.getY() / eg.TileSize;

        dijkstras.update(px,py);
        for (Enemy enemy : enemies) {
            if (eg.player.checkObject(enemy)) {
                isStarted = false;
                for (Enemy enemy1 : enemies) {
                    enemy1.reset();
                }
            }
        }
        if (eg.player.getIsDead()) {
            eg.didWin = false;
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
        for (Enemy enemy: enemies) {
            enemy.update(dijkstras, delta);
        }
        eg.player.update(input, delta);
        eg.runTime += delta;
    }

    public int getNextState() { return this.nextState; }

}
