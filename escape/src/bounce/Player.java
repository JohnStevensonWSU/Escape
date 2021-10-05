package bounce;

import jig.Entity;
import jig.ResourceManager;

public class Player extends Entity {

    public Player(int x, int y) {
        super(x,y);
        addImageWithBoundingBox(ResourceManager.getImage(EscapeGame.PLAYER_IMG_RSC));
    }


}
