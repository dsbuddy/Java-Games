package tiles;

import java.awt.Graphics;
import java.awt.Rectangle;

import nidhogg.Handler;
import nidhogg.ID;

public abstract class Tile {
	public int x, y, width, height;
	public ID id;
	public Handler handler;
	
	public Tile(int x, int y, int width, int height, ID id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.handler = handler;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(x + 10, y, width - 20, 5);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 10, y + height - 5, width - 20, 5);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 10, 5, height - 20);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 5, y + 10, 5, height - 20);
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

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
}
