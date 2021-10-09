package bounce;

import jdk.internal.util.xml.impl.Pair;
import jig.Collision;
import jig.Entity;
import jig.Vector;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Level extends BasicGameState {
    private int nextState;
//    private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
    private TileMap map;
    private final boolean debug = true;
    private Collidable collidables = new Collidable(0,0);
    private Enemy enemy;
    private int[][] djikstras;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        map = new TileMap(EscapeGame.LEVEL1MAP_IMG_RSC);
        this.djikstras = new int[50][50];
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Layer  collidables= map.getLayer("Collidable");
        int tile;
        this.enemy = new Enemy(200, 200);

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                tile = collidables.data[i][j][2];
                if (tile != 0) {
                    this.collidables.add(i, j);
//                    this.collidables.add(new Collidable(i,j));

                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.drawString("Level", 10, 30);
        int bgIndex = map.getLayerIndex("Background");
        int fgIndex = map.getLayerIndex("Foreground");
        int collidableIndex = map.getLayerIndex("Collidable");

        map.render(0, 0, bgIndex);
        map.render(0, 0, fgIndex);
        if (debug) {
//            map.render(0, 0, collidableIndex);
//            for (Collidable tile : collidables) {
//                tile.render(graphics);
//            }
            collidables.render(graphics);
        }

        this.enemy.render(graphics);

        eg.player.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        int px = (int) (eg.player.getX() - 8) / 16, py = (int) (eg.player.getY() - 8) / 16;
        this.djikstras[px][py] = -1;



        eg.player.setVelocity(new Vector(0f, 0f));

        if (input.isKeyDown(Input.KEY_DOWN)) {
            eg.player.moveDown();
        } else if (input.isKeyDown(Input.KEY_UP)) {
            eg.player.moveUp();
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            eg.player.moveLeft();
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            eg.player.moveRight();
        } else {
            eg.player.moveStill();
        }

        eg.player.checkObject(collidables);
        eg.player.checkObject(enemy);

        eg.player.update(i);
    }

    public int getNextState() { return this.nextState; }

}
