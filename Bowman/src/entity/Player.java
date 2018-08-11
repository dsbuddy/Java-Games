package entity;

import java.awt.Color;
import java.awt.Graphics;

import gamePackage.Game;
import gamePackage.Handler;
import gamePackage.ID;
import tile.Tile;


public class Player extends Entity{
	private int frame = 0;
	private int frameDelay = 0;
	private boolean immunity = false;
	private int immLS = 0;
	int maxHP;
	int drawtime;

	public Player (int x, int y, int width, int height, ID id, boolean solid, Handler handler) {
		super(x, y, width, height, id, solid, handler);
		this.gravity = 5.0;
		facing = "Right";
		HP = 250;
		maxHP = (int) HP;
	}
	public void tick() {
		if(immunity = true && immLS > 0) immLS--;

		if(immLS == 0) immunity = false;
		
		x += velX;
		
		y += velY;
		y += gravity;
		
		if(charging) charge += 0.5;
		if(charge >= 50) charge = 50;
		
		if(charge <= 25) drawtime = 1;
		if(charge > 25) drawtime = 0;
		
		if(!readyToFire) cooldown--;
		if(cooldown <= 0) {
			readyToFire = true;
			cooldown = 0;
		}
		
		
		if(jumping) gravity += 0.05;
		if(falling) velY += 0.1;
		
		grounded = false;
		for (Tile ti : handler.tile) {
			if(ti.solid) {
			if(ti.getID() == ID.Wall) {
				if(getBoundsTop().intersects(ti.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.0;
						falling = true;
					}
					setY(ti.y + 64);
				
				}
				if(getBoundsBottom().intersects(ti.getBounds())) {
					setVelY(0);
					setY(ti.y - 64);
					gravity = 5.0;
					jumping = false;
					falling = false;
					grounded = true;
				}

				if(getBoundsLeft().intersects(ti.getBounds())) {
					setVelX(0);
					x = ti.getX() + ti.getWidth();
				}
				if(getBoundsRight().intersects(ti.getBounds())) {
					setVelX(0);
					x = ti.getX() - getWidth();
				}
			}
			}
		}
		
		for (int i = 0; i < Game.handler.entity.size(); i++) {
			if (Game.handler.entity.get(i).id == ID.Enemy) {
				if(getBounds().intersects(Game.handler.entity.get(i).getBounds()) && immunity == false) {
					System.out.println(HP);
					HP -= 50;
					System.out.println(HP);
					immunity = true;
					immLS = 64;
				}
			}
		}
		
		if(velX != 0 && !jumping) {
			frameDelay++;
			if(frameDelay >= 24) {
				frame++;
				if(frame >= 4) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
		else {
			frameDelay = 0;
			frame = 0;
		}
		
		if(HP == 0) Game.reset();


	}

	public void render(Graphics g) {
		if(velX != 0) {
			if(facing.equals("Left")) {
				if(!grounded) {
					if (charging) {
						g.drawImage(Game.bowman[23 - drawtime].getBufferedImage(), x, y, null);
					}
					else {
					g.drawImage(Game.bowman[20].getBufferedImage(), x, y, null);
					}
				}
				else {
					g.drawImage(Game.bowman[frame + 4].getBufferedImage(), x, y, null);
				}
			}
			else {
				if(!grounded) {
					if (charging) {
						g.drawImage(Game.bowman[25 - drawtime].getBufferedImage(), x, y, null);
					}
					else {
					g.drawImage(Game.bowman[21].getBufferedImage(), x, y, null);
					}
				}
				else{
				g.drawImage(Game.bowman[frame].getBufferedImage(), x, y, null);
				}
			}
		}
		else {
			if(grounded) {
			if(facing.equals("Left")) {
				if(charging) {
					g.drawImage(Game.bowman[11 - drawtime].getBufferedImage(), x, y, null);
				}
				else {
				g.drawImage(Game.bowman[9].getBufferedImage(), x, y, null);
				}
			}
			else {
				if (charging) {
					g.drawImage(Game.bowman[13 - drawtime].getBufferedImage(), x, y, null);
				}
				else {
					g.drawImage(Game.bowman[8].getBufferedImage(), x, y, null);
				}
			}
			}
			else if(facing.equals("Left")) {
				if(charging) {
					g.drawImage(Game.bowman[23].getBufferedImage(), x, y, null);
				}
				else {
					g.drawImage(Game.bowman[20].getBufferedImage(), x, y, null);
				}
			}
			else if(facing.equals("Right")) {
				if(charging) {
					g.drawImage(Game.bowman[24].getBufferedImage(), x, y, null);
				}
				else {
					g.drawImage(Game.bowman[21].getBufferedImage(), x, y, null);
				}
			}
		}
		
		g.setColor(Color.RED);
		g.fillRect(x - ((Game.WIDTH * Game.SCALE) / 2) + 80, y - ((Game.HEIGHT * Game.SCALE) / 2) + 80, maxHP, 30);
		g.setColor(Color.GREEN.brighter());
		g.fillRect(x - ((Game.WIDTH * Game.SCALE) / 2) + 80, y - ((Game.HEIGHT * Game.SCALE) / 2) + 80, (int) HP, 30);
		
		if(readyToFire) {
			g.setColor(Color.ORANGE.darker().darker());
			g.fillRect(x, y - 16, 64, 3);
			g.setColor(Color.ORANGE.brighter());
			g.fillRect(x, y - 16, (int) (64 * (charge / 50)), 3);
		}
		else {
			g.setColor(Color.GREEN.darker());
			g.fillRect(x, y - 16, 64, 3);
			g.setColor(Color.BLUE.brighter());
			g.fillRect(x, y - 16, (int) (64 * (cooldown / 75)), 3);
		}
		
		
	}
	

}
