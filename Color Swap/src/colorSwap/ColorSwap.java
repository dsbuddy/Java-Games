package colorSwap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Spring;
import javax.swing.Timer;
import javax.swing.text.html.StyleSheet;




public class ColorSwap implements ActionListener, MouseListener, KeyListener{

	public static ColorSwap colorSwap;
	public final int WIDTH = 400, HEIGHT = 800;
	public Renderer renderer;
	public int ticks, score;
	public Rectangle player;
	public ArrayList<Rectangle> tiles;
	public double yMotion;
	public boolean gameOver, started;
	public Random rand1, rand2;
	public Timer newTileTimer = new Timer(5000, this);
	public int count = 0;
	public static graphicsd.SpriteSheet sheet;
	public static graphicsd.Sprite tilesG, playerG;

	public ColorSwap(){
		JFrame jframe = new JFrame();
		Timer timer = new Timer(25, this);
		
		tiles = new ArrayList<Rectangle>();
		
		renderer = new Renderer();
		rand1 = new Random();

		jframe.add(renderer);
		jframe.setTitle("Color Swap");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
		sheet = new graphicsd.SpriteSheet("/doodle.png");
		tilesG = new graphicsd.Sprite(sheet, 1, 2);
		playerG = new graphicsd.Sprite(sheet, 1, 1);
		player = new Rectangle(WIDTH / 2, HEIGHT / 2, 32, 32);
		
	
		addTiles(true);
		addTiles(true);
		


		timer.start();
		newTileTimer.start();
	}



	private void addTiles(boolean start) {

		int xCoord = 0, yCoord, space = 60;

		rand1 = new Random();
		rand2 = new Random();
		int yy = 470;

		for (int i = 0; i <= 4; i++) {
			xCoord = rand1.nextInt(WIDTH - 60);
			yCoord = player.y + 25;

			if (start) {
				if (i == 0) {
					tiles.add(new Rectangle(player.x - 20, yCoord - space * i, 60, 5));
				} else {
					tiles.add(new Rectangle(xCoord, yCoord - space * i, 60, 5));

//					if (yCoord - space * i > yy) {
//						yy = yCoord - space * i;
//					}
				}

			} 

		}
		for (int i = 0; i <= 2; i++) {
			xCoord = rand1.nextInt(WIDTH - 60);
		if (!start) {
//			if (tiles.get(tiles.size()).y >= 470 + space) {
//				yy = yy + tiles.get(tiles.size()).y + space;
//			}
			tiles.add(new Rectangle(xCoord, yy - space * i, 60, 5));
		}
		}
	}





	public void jump(){
		if (gameOver){
			player = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 +310, 20, 20);
			
			yMotion = 0;
			score = 0;
			tiles.clear();
			
			addTiles(true);
			addTiles(true);
		

			
			gameOver = false;
		}

		if (!started){
			started = true; 
		}
		else if (!gameOver){
			if (yMotion > 0){
				yMotion = 0;
			}

			yMotion -= 6;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		double speed = 1;
	
		ticks++;

		if (started){
			for (int i = 0; i < tiles.size(); i++) {
				 
				tiles.get(i).y += speed;
				
			}

			if (ticks % 2 == 0 && yMotion < 15){
				yMotion += .5;
			}

			for (int i = 0; i < tiles.size(); i++) {
				Rectangle tile = tiles.get(i);
				if (tile.y + tile.height >800) { // pipe offscreen
					
					System.out.println(count);
					tiles.remove(tile);
					
					if(count % 2 == 0 && score <= 10){
						addTiles(false);
					}
					else if(count % 4 == 0){
						addTiles(false);
					}
			
				}
				
			}
			
//			if(count % 5 == 0){
//				addTiles(false);
//			}
//			if(player.y <= tiles.get(tiles.size()).y){
//				addTiles(false);	
//			}
			
			player.y += yMotion;


//
//			if (player.y > HEIGHT || player.y < 0){
//				gameOver = true;
//			}

			if (player.y + yMotion >= HEIGHT){
				player.y = HEIGHT - player.height;
				gameOver = true;
			}
		}
		if(yMotion > 2){
	for (Rectangle tiles : tiles) {
		if(tiles.intersects(player)){
			jump();
			score+=1;
			count++;
			
		}
	}
		}
		renderer.repaint();
	}
	

	public void repaint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
  
		

		
		g.drawImage(playerG.getBufferedImage(), player.x, player.y, player.width, player.height, null);

		
		g.setColor(Color.GREEN.brighter());
		for (Rectangle tiles : tiles) {
			g.fillRect(tiles.x,  tiles.y,  tiles.width, tiles.height);
			
		}
		
		g.setColor(Color.WHITE);
		if (!started){
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}

		if (gameOver){
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
		}

		if (!gameOver && started){
			g.drawString(String.valueOf(score), 50, 100);
		}
		
		
		
	}

	public static void main(String[] args){
		colorSwap = new ColorSwap();
	}

	@Override
	public void mouseClicked(MouseEvent e){
		
	}

	@Override
	public void keyReleased(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			started = true;
			gameOver = false;
			
		}
//		if (e.getKeyCode() == KeyEvent.VK_A){
//			moveLeft();
//		}
//		if (e.getKeyCode() == KeyEvent.VK_D){
//			moveRight();
//		}
	}
	
	private void moveRight() {
		player.x += 20;
	}





	private void moveLeft() {
		player.x -= 20;		
	}





	@Override
	public void mousePressed(MouseEvent e){
		
	}

	@Override
	public void mouseReleased(MouseEvent e){
		
	}

	@Override
	public void mouseEntered(MouseEvent e){
	}

	@Override
	public void mouseExited(MouseEvent e){
		
	}

	@Override
	public void keyTyped(KeyEvent e){

	}

	@Override
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_A){
			moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_D){
			moveRight();
		}
	}

}