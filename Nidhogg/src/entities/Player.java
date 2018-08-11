package entities;

import java.awt.Color;
import java.awt.Graphics;

import nidhogg.Game;
import nidhogg.Handler;
import nidhogg.ID;
import tiles.Tile;

public class Player extends Entity {

	public boolean hasSword = true, jumping = false, falling = false;
	public int position = 2;	//0 = crouching; 1 = low; 2 =  middle; 3 = high; 4 = throwing;
	public int facing = 1;		//0 = left; 1 = right; 2 = dead;
	public double gravity = 0.0;
	public Sword sword;
	
	public Player(int x, int y, int width, int height, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
		switch(id) {
		case Player1:
			sword = new Sword(x + (width * 20 / 32), y - (height * 22 / 32), width, height, ID.Sword1, handler);
			handler.addEntity(sword);
			break;
		case Player2:
			sword = new Sword(x + (width * 20 / 32), y - (height * 22 / 32), width, height, ID.Sword2, handler);
			handler.addEntity(sword);
			facing = 0;
			break;
		default:
			break;
		}
	}

	public void render(Graphics g) {
		switch(id) {
		case Player1:
			g.drawImage(Game.p1[Game.player1.facing].getBufferedImage(), x, y, width, height, null);
			break;
		case Player2:
			g.drawImage(Game.p2[Game.player2.facing].getBufferedImage(), x, y, width, height, null);
			break;
		default:
			 break;
		}
		
		if(Game.showAllBounds == true) {
			g.setColor(Color.red);
			switch(id) {
			case Player1:
				g.drawRect(getBounds(Game.p1[Game.player1.facing].getBufferedImage()).x, getBounds(Game.p1[Game.player1.facing].getBufferedImage()).y, 
						getBounds(Game.p1[Game.player1.facing].getBufferedImage()).width, getBounds(Game.p1[Game.player1.facing].getBufferedImage()).height);
				break;
			case Player2:
				g.drawRect(getBounds(Game.p2[Game.player2.facing].getBufferedImage()).x, getBounds(Game.p2[Game.player2.facing].getBufferedImage()).y, 
						getBounds(Game.p2[Game.player2.facing].getBufferedImage()).width, getBounds(Game.p2[Game.player2.facing].getBufferedImage()).height);
				break;
			default:
				 break;
			}
		}
	}

	public void tick() {
		x += velX;
		y += velY;
		
		checkCollision();
		
		if(jumping) {
			gravity -= 0.16;
			setVelY((int) -gravity);
			if(gravity <= 0.0) {
				jumping = false;
				falling = true;
			}
		}
		
		if(falling) {
			gravity += 0.32;
			setVelY((int) gravity);
		}
	}

	private void checkCollision() {
		for(Tile tile : Game.handler.tiles) {		//block collision
			if(tile.id == ID.BasicTile) {
				if(getBounds(Game.p1[Game.player1.facing].getBufferedImage()).intersects(tile.getBoundsBottom())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBounds(Game.p1[Game.player1.facing].getBufferedImage()).intersects(tile.getBoundsTop())) {
					setVelY(0);
					if(falling) falling = false;
				} else {
					if(!falling && !jumping) {
						gravity = 0.0;
						falling = true;
					}
				}
			}
		}
		
		for(int i = 0; i < handler.entities.size(); i++) {	//entity collision
			if(handler.entities.get(i).id == ID.Sword1 && this.id == ID.Player2) {
				if(getBounds(Game.p2[Game.player2.facing].getBufferedImage()).intersects(handler.entities.get(i).getBoundsSword(Game.sword[Game.player1.facing].getBufferedImage()))) {
					//System.out.println("player 1 hits player 2");
					Game.player1Kills++;
					Game.handler.removeEntity(Game.player1);
					Game.handler.removeEntity(Game.player1.sword);
					Game.handler.removeEntity(Game.player2);
					Game.handler.removeEntity(Game.player2.sword);
					Game.spawn();
					
				}
			} else if(handler.entities.get(i).id == ID.Sword2 && this.id == ID.Player1) {
				if(getBounds(Game.p1[Game.player1.facing].getBufferedImage()).intersects(handler.entities.get(i).getBoundsSword(Game.sword[Game.player2.facing].getBufferedImage()))) {
					//System.out.println("player 2 hits player 1");
					Game.player2Kills++;
					Game.handler.removeEntity(Game.player1);
					Game.handler.removeEntity(Game.player1.sword);
					Game.handler.removeEntity(Game.player2);
					Game.handler.removeEntity(Game.player2.sword);
					Game.spawn();
				}
			}
		}
	
	}
	
}
