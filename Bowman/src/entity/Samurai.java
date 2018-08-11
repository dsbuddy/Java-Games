package entity;

import java.awt.Color;
import java.awt.Graphics;

import gamePackage.Game;
import gamePackage.Handler;
import gamePackage.ID;
import tile.Tile;

public class Samurai extends Entity{
	
	int frameDelay = 0;
	int frame = 0;
	int maxHP;
	int prevMov = 0;
	public Samurai(int x, int y, int width, int height, ID id, boolean solid, Handler handler, int HP) {
		super(x, y, width, height, id, solid, handler);
		velX = -1;
		facing = "Left";
		gravity = 3.0;
		this.HP = HP;
		maxHP = HP;
	}

	public void tick() {
		x += velX;
		y += velY;
		y += gravity;
		
		
		grounded = false;
		for (Tile ti : handler.tile) {
			if(ti.getID() == ID.Wall) {
				if(getBoundsLeft().intersects(ti.getBounds())) {
					setVelX(1);
					facing = "Right";
					setX(ti.getX() + 66);
				}
				if(getBoundsRight().intersects(ti.getBounds())) {
					facing = "Left";
					setVelX(-1);
					setX(ti.getX() - 66);
				}
					
				if(getBoundsBottom().intersects(ti.getBounds())) {
					setVelY(0);
					setY(ti.y - 64);
					gravity = 5.0;
					falling = false;
					grounded = true;
				}

			}
		}
			
		if(velX != 0) {
			frameDelay++;
			if(frameDelay >= 34) {
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
			

		if(HP <= 0) {
			die();
			Game.killcount++;
		}
	}
	

	public void render(Graphics g) {
		if(velX != 0) {
			if(facing.equals("Left")) {
				g.drawImage(Game.samurai[frame + 4].getBufferedImage(), x, y, null);
			}
			else {
				g.drawImage(Game.samurai[frame].getBufferedImage(), x, y, null);
			}
		}
		else {
			if (facing == "Left") g.drawImage(Game.samurai[5].getBufferedImage(), x, y, null);
			if (facing == "Right") g.drawImage(Game.samurai[1].getBufferedImage(), x, y, null);
		}

		
		g.setColor(Color.RED);
		g.fillRect(x, y - 16, 64, 5);
		g.setColor(Color.GREEN.brighter());
		g.fillRect(x, y - 16, (int) (64 * (HP / maxHP)), 5);
	}
}
	
