package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import gamePackage.Game;
import gamePackage.Handler;
import gamePackage.ID;

public abstract class Entity {
	public int x, y;
	public int width, height;
	public boolean solid;
	public int velX;
	public double velY;
	public ID id;
	public Handler handler;
	public boolean jumping = false;
	public boolean falling = false;
	public double gravity = 1;
	public boolean grounded = true;
	public String facing;
	public double HP;
	public boolean readyToFire = true;
	public boolean charging;
	public double charge;
	public double cooldown;
	public Entity(int x, int y, int width, int height, ID id, boolean solid, Handler handler) {
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
	public int getX() {
		return x;
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
		return new Rectangle(x, y, 5, height - 20);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 5, y + 10, 5, height - 20);
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
	public double getVelY() {
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
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public boolean isJumping() {
		return jumping;
	}
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	public double getGravity() {
		return gravity;
	}
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	public void shoot(double charge) {
			handler.addEntity(new Projectile(this.x + 32, this.y + 32, 40, 3, ID.Arrow, false, Game.handler, charge));
		
	}
	public void die() {
		handler.removeEntity(this);
		System.out.println("An entity has died");
	}
}
