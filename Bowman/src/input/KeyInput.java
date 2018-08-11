package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Entity;
import gamePackage.Game;
import gamePackage.Handler;
import gamePackage.ID;

public class KeyInput implements KeyListener {
	Handler handler;

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if(en.getId() == ID.Player) {
			switch (key) {
			case KeyEvent.VK_W:
				if(!en.falling && en.grounded){
				en.jumping = true;
				en.grounded = false;
				en.setVelY(-9);
				}
				break;
			case KeyEvent.VK_A:
				en.setVelX(-3);
				en.facing = "Left";
				break;
			case KeyEvent.VK_D:
				en.setVelX(3);
				en.facing = "Right";
				break;
			case KeyEvent.VK_S:
				break;
			}
		}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if (en.getId() == ID.Player) {
			switch (key) {
			case KeyEvent.VK_W:
				en.jumping = false;
				en.falling = true;				
				break;
			case KeyEvent.VK_A:
				en.setVelX(0);
				break;
			case KeyEvent.VK_D:
				en.setVelX(0);
				break;
			case KeyEvent.VK_S:
				break;
			}
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
