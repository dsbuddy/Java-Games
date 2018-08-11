package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import gamePackage.Game;
import gamePackage.ID;

public class MouseInput implements MouseListener{

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < Game.handler.entity.size(); i++) {
			if(Game.handler.entity.get(i).getId() == ID.Player) {
				if(Game.handler.entity.get(i).readyToFire) {
					Game.handler.entity.get(i).charging = true;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		for (int i = 0; i < Game.handler.entity.size(); i++) {
			if(Game.handler.entity.get(i).getId() == ID.Player && Game.handler.entity.get(i).charging == true) {
				Game.handler.entity.get(i).charging = false;
				Game.handler.entity.get(i).readyToFire = false;
				Game.handler.entity.get(i).shoot(Game.handler.entity.get(i).charge);
				Game.handler.entity.get(i).charge = 0;
				Game.handler.entity.get(i).cooldown = 75;
				
			}
		}
	}

}
