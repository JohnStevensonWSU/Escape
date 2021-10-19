package bounce;

import com.sun.jndi.dns.ResourceRecord;
import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class EscapeGame extends StateBasedGame {
	public final int ScreenWidth;
	public final int ScreenHeight;
	public final int TileSize;
	public final int TileWidth;
	public final int TileHeight;

	public static final String PLAYER_IMG_RSC = "bounce/resource/player.png";
	public static final String TILESET_IMG_RSC = "bounce/resource/map/tileset.png";
	public static final String TILESET1_IMG_RSC = "bounce/resource/map/tileset1.png";
	public static final String LEVEL1MAP_IMG_RSC = "bounce/resource/map/map.tmx";

	public static final String PLAYERBODYWALKDOWN_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_DOWN.png";
	public static final String PLAYERBODYWALKUP_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_UP.png";
	public static final String PLAYERBODYWALKLEFT_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_LEFT.png";
	public static final String PLAYERBODYWALKRIGHT_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_RIGHT.png";

	public static final String PLAYERBODYWALKDOWNSTILL_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_STILL_DOWN.png";
	public static final String PLAYERBODYWALKRIGHTSTILL_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_STILL_RIGHT.png";
	public static final String PLAYERBODYWALKUPSTILL_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_STILL_UP.png";
	public static final String PLAYERBODYWALKLEFTSTILL_IMG_RSC = "bounce/resource/walking/BODY_MALE_WALKING_STILL_LEFT.png";

	public static final String ENEMYBODYWALKDOWN_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_DOWN.png";
	public static final String ENEMYBODYWALKUP_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_UP.png";
	public static final String ENEMYBODYWALKLEFT_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_LEFT.png";
	public static final String ENEMYBODYWALKRIGHT_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_RIGHT.png";

	public static final String ENEMYBODYWALKDOWNSTILL_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_STILL_DOWN.png";
	public static final String ENEMYBODYWALKRIGHTSTILL_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_STILL_RIGHT.png";
	public static final String ENEMYBODYWALKUPSTILL_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_STILL_UP.png";
	public static final String ENEMYBODYWALKLEFTSTILL_IMG_RSC = "bounce/resource/walking/BODY_SKELETON_WALKING_STILL_LEFT.png";

	public static final int STARTUPSTATE = 0;
	public static final int LEVEL_1 = 1;

	public Player player;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public EscapeGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;
		TileSize = 16;
		TileWidth = ScreenWidth / TileSize;
		TileHeight = ScreenHeight / TileSize;
		Entity.setDebug(true);

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new Level());
		addState(new GameOverState());

		ResourceManager.loadImage(PLAYER_IMG_RSC);
		ResourceManager.loadImage(TILESET_IMG_RSC);
		ResourceManager.loadImage(TILESET1_IMG_RSC);

		ResourceManager.loadImage(PLAYERBODYWALKDOWN_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKUP_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKLEFT_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKRIGHT_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKDOWNSTILL_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKUPSTILL_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKRIGHTSTILL_IMG_RSC);
		ResourceManager.loadImage(PLAYERBODYWALKLEFTSTILL_IMG_RSC);

		ResourceManager.loadImage(ENEMYBODYWALKDOWN_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKUP_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKLEFT_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKRIGHT_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKDOWNSTILL_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKUPSTILL_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKRIGHTSTILL_IMG_RSC);
		ResourceManager.loadImage(ENEMYBODYWALKLEFTSTILL_IMG_RSC);

		player = new Player(50,50,0.1f);
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new EscapeGame("Escape!", 800, 600));
			app.setDisplayMode(800, 800, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	
}
