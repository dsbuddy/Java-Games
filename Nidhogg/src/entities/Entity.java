package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import nidhogg.Game;
import nidhogg.Handler;
import nidhogg.ID;

public abstract class Entity {
	public int x, y, width, height, velX, velY;
	public ID id;
	public Handler handler;
	
	public Entity(int x, int y, int width, int height, ID id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.handler = handler;
	}
	
	public Rectangle getBounds(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int rightBound = 0, topBound = -1;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);
				
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if((red == 255 && green == 255 && blue == 0) || (red == 255 && green == 164 && blue == 65)
						|| (red == 255 && green == 255 && blue == 255)) {
					if(topBound == -1) topBound = y;
					if(rightBound < x) rightBound = x;
				}
			}
		}
		return new Rectangle(this.x, this.y + (this.height * topBound / 32), 
				this.width * (rightBound + 1) / 32, this.height - (this.height * topBound / 32));
	}
	
	public Rectangle getBoundsSword(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int rightBound = 0, topBound = -1;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);
				
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if((red == 255 && green == 255 && blue == 0) || (red == 255 && green == 164 && blue == 65)
						|| (red == 255 && green == 255 && blue == 255)) {
					if(topBound == -1) topBound = y;
					if(rightBound < x) rightBound = x;
				}
			}
		}
		if(this.id == ID.Sword1 && Game.player1.facing == 0){
			return new Rectangle(Game.player1.x - getBounds(Game.p1[Game.player1.facing].getBufferedImage()).width + (this.width / 32), 
					this.y + (this.height * topBound / 32), this.width * (rightBound + 1) / 32, this.height - (this.height * topBound / 32));
		} else if(this.id == ID.Sword2 && Game.player2.facing == 0){
			return new Rectangle(Game.player2.x - getBounds(Game.p2[Game.player2.facing].getBufferedImage()).width + (this.width / 32), 
					this.y + (this.height * topBound / 32), this.width * (rightBound + 1) / 32, this.height - (this.height * topBound / 32));
		} else {
			return new Rectangle(this.x, this.y + (this.height * topBound / 32), 
					this.width * (rightBound + 1) / 32, this.height - (this.height * topBound / 32));
		}
	}
	
	public abstract void render(Graphics g);
	public abstract void tick();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
}
