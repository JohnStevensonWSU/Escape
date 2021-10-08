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

public class Level extends BasicGameState {
    private int nextState;
//    private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
    private TileMap map;
    private final boolean debug = true;
    private Collidable collidables = new Collidable(0,0);

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

        eg.player.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        float vy = 0f, vx = 0f;
        int count = 0;
        Collision collision;
        final Vector left = new Vector(-1f, 0f),
                right = new Vector(1f, 0f),
                up = new Vector(0f, -1f),
                down = new Vector(0f, 1f);
        Vector unit;

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
            eg.player.setVelocity(new Vector(0f, 0.1f));
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            eg.player.setVelocity(new Vector(0f, -0.1f));
        }

        if (input.isKeyDown(Input.KEY_LEFT)) {
            eg.player.setVelocity(new Vector(-0.1f, 0f));
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            eg.player.setVelocity(new Vector(0.1f, 0f));
        }

        eg.player.checkEnvironment(collidables);

        eg.player.update(i);
    }

    public int getNextState() { return this.nextState; }

}
