package entity;
import java.awt.Graphics;

import gamePackage.Game;
import gamePackage.Handler;
import gamePackage.ID;

public class Projectile extends Entity{
	
	String facing = "tbd";

	public Projectile(int x, int y, int width, int height, ID id, boolean solid, Handler handler, double charge) {
		super(x, y, width, height, id, solid, handler);
		for (int z = 0; z < Game.handler.entity.size(); z++) {
			if(Game.handler.entity.get(z).id == ID.Player) {
				if(Game.handler.entity.get(z).facing== "Left") {
					
					velX = 11;
					facing = "Left";
				}
				else {
					velX = -11;
					facing = "Right";
				}
			}
		}
		this.charge = charge;
		
	}


	public void tick() {
		x -= velX;
		y += velY;
		
			for(int i = 0; i < Game.handler.entity.size(); i++) {
				if(Game.handler.entity.get(i).id == ID.Enemy) {
					if (this.getBounds().intersects(Game.handler.entity.get(i).getBounds())) {
						System.out.println(charge);
						Game.handler.entity.get(i).HP -= charge * 2.5;
						handler.removeEntity(this);
					}
				}
			}
			
			for(int i = 0; i < Game.handler.tile.size(); i++) {
				if(Game.handler.tile.get(i).solid == false) break;
				if (this.getBounds().intersects(Game.handler.tile.get(i).getBounds())) {
					Game.handler.entity.remove(this);
				}
			}
	}

	public void render(Graphics g) {
			if (facing.equals("Right")) {
				g.drawImage(Game.arrow.getBufferedImage(), x, y, width, height, null);
			}
			if (facing.equals("Left")) {
				g.drawImage(Game.backarrow.getBufferedImage(), x, y, width, height, null);
			}
		}
	
		
}



