package tile;

import java.awt.Graphics;

import gamePackage.Game;
import gamePackage.Handler;
import gamePackage.ID;

public class BaseWall extends Tile {

	public BaseWall(int x, int y, int width, int height, ID id, boolean solid, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		if(solid) {
		g.drawImage(Game.wall.getBufferedImage(), x, y, width, height, null);
		}
	}

}
