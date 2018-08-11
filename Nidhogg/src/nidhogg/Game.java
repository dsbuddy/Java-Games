package nidhogg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;

import entities.Player;
import gfx.Sprite;
import gfx.SpriteSheet;
import tiles.BasicTile;

/**
 * 
 * @author Jared Wyce, Daniel Schwartz
 * @version Alpha 1.0
 * Sprites redone by Abbey Hebler or taken from Faithful Minecraft texturepack
 * 
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 2640539109652803937L;
	public static final int WIDTH = 270, HEIGHT = WIDTH * 9 / 16, SCALE = 4;
	public static final String TITLE = "Nidhogg";
	private Thread thread;
	private boolean running;
	public static boolean showAllBounds = false;
	public static Handler handler;
	public static HUD hud;
	public static Player player1, player2;
	
	public static SpriteSheet sprites;
	public static Sprite stone, p1[], p2[], sword[];
	
	public static int player1Kills, player2Kills;
	
	public STATE gameState;
	public enum STATE {
		Menu,
		Help,
		Game,
		End
	};
	
	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		handler = new Handler();
		hud = new HUD();
		sprites = new SpriteSheet("/sprites.png");

		p1 = new Sprite[3];
		p2 = new Sprite[3];
		sword = new Sprite[2];
		
		stone = new Sprite(sprites, 1, 18);
		
		p1[0] = new Sprite(sprites, 1, 6);
		p1[1] = new Sprite(sprites, 1, 5);
		p1[2] = new Sprite(sprites, 14, 17);
		
		p2[0] = new Sprite(sprites, 1, 8);
		p2[1] = new Sprite(sprites, 1, 7);
		p2[2] = new Sprite(sprites, 16, 17);
		
		sword[0] = new Sprite(sprites, 13, 17);
		sword[1] = new Sprite(sprites, 12, 17);
				
		addKeyListener(new KeyInput());
		
		gameState = STATE.Game;
		
		for(int i = 0; i < Game.WIDTH*Game.SCALE/64 + 2; i++) {
			handler.addTile(new BasicTile(i*64, HEIGHT*SCALE - 50, 64, 64, stone.getBufferedImage(), ID.BasicTile, handler));
		}
		
		spawn();
	}
	
	public static void spawn() {
		player1 = new Player(50, 150, 128, 128, ID.Player1, handler);
		player2 = new Player(WIDTH*SCALE - 178, 150, 128, 128, ID.Player2, handler);
		
		handler.addEntity(player1);
		handler.addEntity(player2);
	}

	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + " fps " + ticks + " ups");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	private void render() {
		BufferStrategy bs  = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		if(gameState == STATE.Game){
			g.setColor(Color.DARK_GRAY.darker().darker());
			g.fillRect(0, 0, getWidth(), getHeight());

			handler.render(g);
			hud.render(g);
		} else if(gameState == STATE.Menu) {
			
		} else if(gameState == STATE.End) {
			handler.tiles.clear();
			handler.entities.clear();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setFont(new Font("verdana", Font.BOLD, 64));
			
			if(player1Kills > player2Kills) {
				g.setColor(Color.yellow);
				g.drawString("Winner!", WIDTH*SCALE / 2 - 175, 200);
			} else {
				g.setColor(new Color(230, 126, 34));
				g.drawString("Winner!", WIDTH*SCALE / 2 - 175, 200);
			}
			
			g.setFont(new Font("verdana", Font.PLAIN, 32));
			g.setColor(Color.yellow);
			g.drawString("player 1 kills", 100, 400);
			g.drawString(Integer.toString(player1Kills), 120, 450);
			
			g.setColor(new Color(230, 126, 34));
			g.drawString("player 2 kills", WIDTH*SCALE - 250, 400);
			g.drawString(Integer.toString(player2Kills), WIDTH*SCALE - 230, 450);
		}
		
		g.dispose();
		bs.show();
	}

	private void tick() {
		handler.tick();
		hud.tick();
		
		if(HUD.clock == 0) gameState = STATE.End;
	}

	public Game() {
		Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}
}
