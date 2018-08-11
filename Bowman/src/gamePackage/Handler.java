package gamePackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entity.Entity;
import entity.Player;
import entity.Samurai;
import tile.BaseWall;
import tile.Tile;

public class Handler {
	
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	
	public Handler() {
	
	}
	
	public void createLevel(BufferedImage level) {
		int width = level.getWidth();
		int height = level.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = level.getRGB(x, y);
				
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 0 && green == 0 && blue == 0) addTile(new BaseWall(x*64, y*64, 64, 64, ID.Wall, true, this));
				if(red == 0 && green == 0 && blue == 255) addEntity(new Player(x*64, y*64, 64, 64, ID.Player, true, this));
				if(red == 255 && green == 0 && blue == 0) addEntity(new Samurai(x*64, y*64, 64, 64, ID.Enemy, true, this, 100));
				if(red == 0 && green == 255 && blue == 0) addTile(new BaseWall(x*64, y*64, 64, 64, ID.Wall, false, this));
				if(red == 0 && green == 255 && blue == 181) addEntity(new Samurai(x*64, y*64, 64, 64, ID.Enemy, true, this, 325));
				if(red == 255 && green == 0 && blue == 189) addEntity(new Samurai(x*64, y*64, 64, 64, ID.Enemy, true, this, 25210));
			}
		}
	}

	public void render(Graphics g) {
		
		for (int x = 0; x < Game.handler.tile.size(); x++) {
			Game.handler.tile.get(x).render(g);
		}
		for (int x = 0; x < Game.handler.entity.size(); x++) {
			Game.handler.entity.get(x).render(g);
		}
	
	}
	public void addEntity(Entity en) {
		entity.add(en);
	}
	
	public void removeEntity(Entity en) {
		entity.remove(en);
	}
	
	public void addTile(Tile ti) {
		tile.add(ti);
	}
	public void removeTile(Tile ti) {
		tile.remove(ti);
	}

	public void tick() {
		for(int i = 0; i < entity.size(); i++) {
			entity.get(i).tick();
		}
		
		for (int i = 0; i < Game.handler.entity.size(); i++) {
			Game.handler.entity.get(i).tick();
		}
		
		for (Tile ti : tile) {
			ti.tick();
		}
		
		if(Game.killcount == 5) {
			createLevel(Game.Chasm);
			Game.killcount = 0;
		}
	}

}
