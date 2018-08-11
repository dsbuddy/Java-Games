package gamePackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entity.Entity;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.KeyInput;
import input.MouseInput;




public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 8649982753439118376L;
	public static final int WIDTH = 270, HEIGHT = WIDTH * 10 / 14, SCALE = 4;
	private boolean running = false;
	private Thread thread;
	public static SpriteSheet sheet;
	public Camera cam;
	public static boolean nothing = false;
	
	public static BufferedImage Arena;
	public static BufferedImage Chasm;
	
	public static int killcount;
	
	public static Sprite bowman[];
	public static Sprite wall;
	public static Sprite samurai[];
	public static Sprite arrow;
	public static Sprite backarrow;
	
	public static Handler handler;
	
	
	public static int getFrameWidth() {
		return WIDTH*SCALE;
	}
	
	public static int getFrameHeight() {
		return HEIGHT*SCALE;
	}
	

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Bowman Pre-alpha");
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
		
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
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + " fps " + ticks + " updates/sec");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		for (Entity en : handler.entity) {
			if(en.getId() == ID.Player) {
				cam.tick(en);
			}
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX() - 32, cam.getY() - 32);
		handler.render(g);
		g.dispose();
		bs.show();
		
	}

	public void init () {
		handler = new Handler();
		sheet = new SpriteSheet("/SpriteSheet.png");
		wall = new Sprite(sheet, 1, 5);
		arrow = new Sprite(sheet, 5, 1);
		backarrow = new Sprite(sheet, 5, 2);
		
		
		bowman = new Sprite[40];
		
		bowman[0] = new Sprite(sheet, 1, 3);
		bowman[1] = new Sprite(sheet, 2, 3);
		bowman[2] = new Sprite(sheet, 3, 3);
		bowman[3] = new Sprite(sheet, 2, 3);
		
		bowman[4] = new Sprite(sheet, 3, 4);
		bowman[5] = new Sprite(sheet, 2, 4);
		bowman[6] = new Sprite(sheet, 1, 4);
		bowman[7] = new Sprite(sheet, 2, 4);
		
		bowman[8] = new Sprite(sheet, 1, 1);
		bowman[9] = new Sprite(sheet, 4, 2);
		
		bowman[10] = new Sprite(sheet, 3, 2);
		bowman[11] = new Sprite(sheet, 2, 2);
		bowman[12] = new Sprite(sheet, 2, 1);
		bowman[13] = new Sprite(sheet, 3, 1);
		
		bowman[20] = new Sprite(sheet, 6, 4);
		bowman[21] = new Sprite(sheet, 4, 3);
		
		bowman[22] = new Sprite(sheet, 5, 4);
		bowman[23] = new Sprite(sheet, 4, 4);
		bowman[24] = new Sprite(sheet, 5, 3);
		bowman[25] = new Sprite(sheet, 6, 3);
		
		
		
		
		samurai = new Sprite[10];
		
		samurai[0] = new Sprite(sheet, 6, 1);
		samurai[1] = new Sprite(sheet, 7, 1);
		samurai[2] = new Sprite(sheet, 8, 1);
		samurai[3] = new Sprite(sheet, 7, 1);
		
		samurai[4] = new Sprite(sheet, 6, 2);
		samurai[5] = new Sprite(sheet, 7, 2);
		samurai[6] = new Sprite(sheet, 8, 2);
		samurai[7] = new Sprite(sheet, 7, 2);
		
		
		
		cam = new Camera();
		addKeyListener(new KeyInput());
		addMouseListener(new MouseInput());
		
		try {
			Arena = ImageIO.read(getClass().getResource("/Arena.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Chasm = ImageIO.read(getClass().getResource("/Chasm.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handler.createLevel(Chasm);
	}
	
	public void start() {
		if(running) return;
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();
	}
	
	public void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Game () {
		Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}

	public static void reset() {
		handler.entity.clear();
		handler.tile.clear();
		handler.createLevel(Chasm);
	}

}

