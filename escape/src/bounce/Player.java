package bounce;

import jig.ResourceManager;

public class Player extends Character {

    public Player(int x, int y) {
        super(x,y);
        addImageWithBoundingBox(ResourceManager.getImage(EscapeGame.PLAYER_IMG_RSC));
    }


}
