package tile;

import java.awt.Graphics;
import java.awt.Rectangle;

import gamePackage.Handler;
import gamePackage.ID;

public abstract class Tile {
	public int x, y;
	public int width, height;
	public boolean solid;
	public int velX, velY;
	public ID id;
	public Handler handler;

	public Tile(int x, int y, int width, int height, boolean solid, ID id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	
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
		return new Rectangle(x + 10, y, 5, height - 20);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 5, y + 10, 5, height - 20);
	}
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
	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
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
	public ID getID() {
		return id;
	}
	public void setID(ID id) {
		this.id = id;
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
}
