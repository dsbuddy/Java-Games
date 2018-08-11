package nidhogg;

import java.awt.Graphics;
import java.util.LinkedList;

import entities.Entity;
import tiles.Tile;

public class Handler {

	public LinkedList<Entity> entities = new LinkedList<Entity>();
	public LinkedList<Tile> tiles = new LinkedList<Tile>();
	
	public void render(Graphics g) {
		for(Tile tile : tiles) {
			tile.render(g);
		}
		for(Entity entity : entities) {
			entity.render(g);
		}
	}
	
	public void tick() {
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).tick();
		}
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public void removeTile(Tile tile) {
		tiles.remove(tile);
	}
}
