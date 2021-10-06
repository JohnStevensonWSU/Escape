package bounce;

import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Floor extends Background {
    public Floor(int x, int y) {
        super(x, y);
        addImage(ResourceManager.getImage(EscapeGame.TILESET_IMG_RSC)
                .getSubImage(96, 32, 16, 16));
    }
}
