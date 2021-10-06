package bounce;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;

public class TileMap extends TiledMap {

    public TileMap(String ref) throws SlickException {
        super(ref);
    }

    public Layer getLayer(String name) {
        try {
            return (Layer) super.layers.get(this.getLayerIndex(name));
        } catch (Exception e) {
            return null;
        }
    }
}
