package nidhogg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

	public static int updates = 0, clock = 60;
	
	public void tick() {
		updates++;
		if(updates % 60 == 0) clock--;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("verdana", Font.PLAIN, 48));
		g.drawString(clock / 60 + ":" + String.format("%02d", clock % 60), Game.WIDTH*Game.SCALE / 2 - 100, 100);
	}
	
}
