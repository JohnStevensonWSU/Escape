package bounce;

import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.Layer;

public class Level extends BasicGameState {
    private int nextState;
    private TileMap map;
    private final boolean debug = false;
    private Collidable[][] terrain;
    private Enemy enemy;
    private Dijkstras dijkstras;
    private int tileWidth;
    private int tileHeight;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        EscapeGame eg = (EscapeGame) stateBasedGame;
        map = new TileMap(EscapeGame.LEVEL1MAP_IMG_RSC);
        this.tileWidth = map.getWidth();
        this.tileHeight = map.getHeight();
        this.terrain = new Collidable[tileWidth][tileHeight];
        Layer  collidables= map.getLayer("Collidable");
        int tile;
        this.enemy = new Enemy(10 * eg.TileSize, 5 * eg.TileSize);

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                tile = collidables.data[i][j][2];
                if (tile != 0) {
                    this.terrain[i][j] = new Collidable(i, j);
                }
            }
        }
        dijkstras = new Dijkstras(tileWidth, tileHeight, (int) eg.player.getX() / eg.TileSize, (int) eg.player.getY() / eg.TileSize, this.terrain);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        graphics.drawString("Level", 10, 30);
        int bgIndex = map.getLayerIndex("Background");
        int fgIndex = map.getLayerIndex("Foreground");
        int collidableIndex = map.getLayerIndex("Collidable");

        map.render(0,0);
//        map.render(0, 0, bgIndex);
//        map.render(0, 0, fgIndex);
//        if (debug) {
//            map.render(0, 0, collidableIndex);
//        }

//        for (int i = 0; i < this.tileWidth; i++) {
//            for (int j = 0; j < this.tileHeight; j++) {
//                graphics.drawString(String.valueOf(dijkstras.getValue(i,j)),
//                        i * eg.TileSize,
//                        j * eg.TileSize);
//            }
//        }

        this.enemy.render(graphics);

        eg.player.render(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        EscapeGame eg = (EscapeGame) stateBasedGame;
        Input input = gameContainer.getInput();
        int px = (int) (eg.player.getX() + 8) / 16, py = (int) (eg.player.getY() + 8) / 16;

        for (Collidable[] tileColumn : terrain) {
            for (Collidable tile : tileColumn) {
                try {
                    eg.player.checkObject(tile);
                } catch (Exception e) {
                    // no terrain at this tile
                }
            }
        }
        eg.player.checkObject(enemy);
        eg.player.update(input, delta);
        enemy.update(dijkstras, delta);
        dijkstras.update(px,py);
    }

    public int getNextState() { return this.nextState; }

}
