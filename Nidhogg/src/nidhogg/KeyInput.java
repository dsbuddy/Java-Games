package nidhogg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.Entity;

public class KeyInput implements KeyListener {

	private double timer = 0.0;
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		timer += 0.5;
		
		for(Entity entity : Game.handler.entities) {
			if(entity.id == ID.Player1) {
				switch(key) {
				case KeyEvent.VK_W:
					break;
				case KeyEvent.VK_A:
					switch(Game.player1.position) {
					case 2:
						if(timer > 1) entity.setVelX(-7);
						else entity.setVelX(-3);
						Game.player1.facing = 0;
					}
					break;
				case KeyEvent.VK_S:						
					break;
				case KeyEvent.VK_D:
					switch(Game.player1.position) {
					case 2:
						if(timer > 1) entity.setVelX(7);
						else entity.setVelX(3);
						Game.player1.facing = 1;
					}
					break;		
				case KeyEvent.VK_F:
					break;
				case KeyEvent.VK_G:
					if(!Game.player1.jumping) {
						Game.player1.jumping = true;
						Game.player1.gravity = 10.0;
					}
					break;
				}
			} else if(entity.id == ID.Player2) {
				switch(key) {
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_LEFT:
					switch(Game.player2.position) {
					case 2:
						if(timer > 1) entity.setVelX(-7);
						else entity.setVelX(-3);
						Game.player2.facing = 0;
					}
					break;
				case KeyEvent.VK_DOWN:						
					break;
				case KeyEvent.VK_RIGHT:
					switch(Game.player2.position) {
					case 2:
						if(timer > 1) entity.setVelX(7);
						else entity.setVelX(3);
						Game.player2.facing = 1;
					}
					break;		
				case KeyEvent.VK_M:
					break;
				case KeyEvent.VK_N:
					if(!Game.player2.jumping) {
						Game.player2.jumping = true;
						Game.player2.gravity = 10.0;
					}
					break;
				}
			}
		} 
	}


	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(Entity entity : Game.handler.entities) {
			if(entity.id == ID.Player1) {
				switch(key) {
				case KeyEvent.VK_W:
					break;
				case KeyEvent.VK_A:
					switch(Game.player1.position) {
					case 2:
						entity.setVelX(0);
					}
					break;
				case KeyEvent.VK_S:						
					break;
				case KeyEvent.VK_D:
					switch(Game.player1.position) {
					case 2:
						entity.setVelX(0);
					}
					break;		
				case KeyEvent.VK_F:
					break;
				case KeyEvent.VK_G:
				}
			} else if(entity.id == ID.Player2) {
				switch(key) {
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_LEFT:
					switch(Game.player2.position) {
					case 2:
						entity.setVelX(0);
					}
					break;
				case KeyEvent.VK_DOWN:						
					break;
				case KeyEvent.VK_RIGHT:
					switch(Game.player2.position) {
					case 2:
						entity.setVelX(0);
					}
					break;		
				case KeyEvent.VK_M:
					break;
				case KeyEvent.VK_N:
				}
			}
		}
		
		timer = 0;
	}

	public void keyTyped(KeyEvent e) {}

}
