package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nidhogg.Game;
import nidhogg.Handler;
import nidhogg.ID;

public class BasicTile extends Tile {

	public BufferedImage image;
	
	public BasicTile(int x, int y, int width, int height, BufferedImage image, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
		this.image = image;
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
		
		if(Game.showAllBounds == true) {
			g.setColor(Color.red);
			//g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
			g.drawRect(getBoundsTop().x, getBoundsTop().y, getBoundsTop().width, getBoundsTop().height);
			g.drawRect(getBoundsBottom().x, getBoundsBottom().y, getBoundsBottom().width, getBoundsBottom().height);
			g.drawRect(getBoundsLeft().x, getBoundsLeft().y, getBoundsLeft().width, getBoundsLeft().height);
			g.drawRect(getBoundsRight().x, getBoundsRight().y, getBoundsRight().width, getBoundsRight().height);
		}
	}

	public void tick() {
		
	}

}