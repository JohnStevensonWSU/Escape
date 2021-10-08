package bounce;

import jdk.internal.util.xml.impl.Pair;
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

public class Level extends BasicGameState {
    private int nextState;
    private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
    private TileMap map;
    private final boolean debug = true;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TileMap(EscapeGame.LEVEL1MAP_IMG_RSC);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Layer  collidables= map.getLayer("Collidable");
        int tile;

        System.out.println(collidables);
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                tile = collidables.data[i][j][2];
                if (tile != 0) {
                    this.collidables.add(new Collidable(i,j));
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
            map.render(0, 0, collidableIndex);
        }

        for (Collidable tile : this.collidables) {
            tile.render(graphics);
        }

        eg.player.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        boolean down = false, up = false, left = false, right = false;
        float vy = 0f, vx = 0f;
        int count = 0;
        int px = (int) (eg.player.getX() - 8) / 16, py = (int) (eg.player.getY() - 8) / 16;
//        int[] tl = new int[]{px - 1, py - 1},
//                tm = new int[]{px, py - 1},
//                tr = new int[]{px + 1, py - 1},
//                ml = new int[]{px - 1, py},
//                mm = new int[]{px, py},
//                mr = new int[]{px + 1, py},
//                bl = new int[]{px - 1, py + 1},
//                bm = new int[]{px, py + 1},
//                br = new int[]{px + 1, py + 1};

        eg.player.setVelocity(new Vector(0f, 0f));

        if (input.isKeyDown(Input.KEY_DOWN)) {
            down = true;
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            up = true;
        }

        if (input.isKeyDown(Input.KEY_LEFT)) {
            left = true;
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            right = true;
        }

        if (up) {
            vy = -0.1f;
        }
        if (down) {
            vy = 0.1f;
        }
        if (left) {
            vx = -0.1f;
        }
        if (right) {
            vx = 0.1f;
        }

        for (Collidable tile : this.collidables) {
            if (eg.player.collides((Entity) tile) != null) {
                System.out.println("Collision Detected: " + count++);
            }
        }

        eg.player.setVelocity(new Vector(vx, vy));

        eg.player.update(i);
    }

    public int getNextState() { return this.nextState; }

}
