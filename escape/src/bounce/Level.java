package bounce;

import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level extends BasicGameState {
    private int nextState;
    private Floor[][] floor;
    private TiledMap map;
    private final boolean debug = false;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("bounce/resource/map/map.tmx");
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        EscapeGame eg = (EscapeGame) stateBasedGame;
        floor = new Floor[25][18];
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 18; j++) {
                floor[i][j] = new Floor(i * eg.TileSize, j * eg.TileSize);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.drawString("Level", 10, 30);
        int bgIndex = map.getLayerIndex("Background");
        int fgIndex = map.getLayerIndex("Foreground");
        int collidableIndex = map.getLayerIndex("Collidables");

        map.render(0, 0, bgIndex);
        map.render(0, 0, fgIndex);
        if (debug) {
            map.render(0, 0, collidableIndex);
        }

        eg.player.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        boolean down = false, up = false, left = false, right = false;
        float vy = 0f, vx = 0f;

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
            vy = -1f;
        }
        if (down) {
            vy = 1f;
        }
        if (left) {
            vx = -1f;
        }
        if (right) {
            vx = 1f;
        }

        eg.player.setVelocity(new Vector(vx, vy));

        eg.player.update(i);
    }

    public int getNextState() { return this.nextState; }

}
