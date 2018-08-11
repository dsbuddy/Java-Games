package entities;

import java.awt.Color;
import java.awt.Graphics;

import nidhogg.Game;
import nidhogg.Handler;
import nidhogg.ID;

public class Sword extends Entity {

	public Sword(int x, int y, int width, int height, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
	}

	public void render(Graphics g) {
		switch(id) {
		case Sword1:
			if(Game.player1.facing == 0) {
				g.drawImage(Game.sword[Game.player1.facing].getBufferedImage(), 
						Game.player1.x - getBounds(Game.p1[Game.player1.facing].getBufferedImage()).width + (this.width / 32), y, width, height, null);
			} else {
				g.drawImage(Game.sword[Game.player1.facing].getBufferedImage(), x, y, width, height, null);
			}
			break;
		case Sword2:
			if(Game.player2.facing == 0) {
				g.drawImage(Game.sword[Game.player2.facing].getBufferedImage(), 
						Game.player2.x - getBounds(Game.p2[Game.player2.facing].getBufferedImage()).width + (this.width / 32), y, width, height, null);
			} else {
				g.drawImage(Game.sword[Game.player2.facing].getBufferedImage(), x, y, width, height, null);
			}
			break;
		case SwordDropped:
			break;
		default:
			break;
		}
	
		if(Game.showAllBounds == true) {
			g.setColor(Color.red);
			switch(id) {
			case Sword1:
				if(Game.player1.facing == 0) {
					g.drawRect(getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).x, getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).y, 
							getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).width, getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).height);
				} else {
					g.drawRect(getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).x, getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).y, 
							getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).width, getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()).height);
				}
				break;
			case Sword2:
				if(Game.player2.facing == 0) {
					g.drawRect(getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).x, getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).y, 
							getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).width, getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).height);
				} else {
					g.drawRect(getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).x, getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).y, 
							getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).width, getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()).height);
				}
				break;
			case SwordDropped:
				break;
			default:
				break;
			}
		}
	}

	public void tick() {
		switch(id) {
		case Sword1:
			x += Game.player1.velX;
			y += Game.player1.velY;
			break;
		case Sword2:
			x += Game.player2.velX;
			y += Game.player2.velY;
			break;
		case SwordDropped:
			break;
		default:
			break;
		}
	}

}
