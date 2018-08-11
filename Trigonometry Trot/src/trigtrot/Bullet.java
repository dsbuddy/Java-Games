package trigtrot;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Bullet implements ActionListener, MouseListener, KeyListener{

	public static Gui gui;
	public static final int WIDTH = 791, HEIGHT = 600;
	public Renderer renderer;
	public Rectangle flippy;
	public ArrayList<Rectangle> blocks;
	public int ticks, yMotion, jumpCount, distance;
	public static int style = 0; //1 paint, 2 Bullet and 3 bullet;
	public static BufferedImage bullet, gun, ClickToStart, GAMEOVER, Ground;
	public Random rand;
	public boolean falling = false, gameOver, started, onTheGround;

	public Bullet(){
		System.out.println(style);
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);

		renderer = new Renderer();
		rand = new Random();

		jframe.add(renderer);
		jframe.setTitle("Trigonometry Trot");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);


		flippy = new Rectangle(200, HEIGHT - 200 - 130, 80, 130);
		try {
			bullet = ImageIO.read(getClass().getResource("bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		blocks = new ArrayList<Rectangle>();
		try {
			gun = ImageIO.read(getClass().getResource("gun.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			ClickToStart = ImageIO.read(getClass().getResource("ClickToStart3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			GAMEOVER = ImageIO.read(getClass().getResource("GAMEOVER3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Ground = ImageIO.read(getClass().getResource("Ground3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		addblock(true);
		addblock(true);
		addblock(true);
		addblock(true);

		timer.start();
	}

	public void addblock(Boolean old){
		
		int space = 400 + 60 * rand.nextInt(5);
		int width = 30;
		int height = 130;
		if(old) blocks.add(new Rectangle(WIDTH + width + blocks.size() * space, HEIGHT - height - 198, width, height)); 
		else blocks.add(new Rectangle(blocks.get(blocks.size() - 1).x + space * 2, HEIGHT - height - 198, width, height));

	}


	public void paintblocks(Graphics g, Rectangle block){
//		g.setColor(Color.gray);
//		g.fillRect(block.x, block.y, block.width, block.height);
		g.drawImage(gun, block.x, block.y, null);
	}


	public void start(){
		if(gameOver){
			flippy = new Rectangle(200, HEIGHT - 200 - 130, 90, 130);
			blocks.clear();
			yMotion = 0;
			jumpCount= 0;
			ticks = 0;
			onTheGround = true;

			addblock(true);
			addblock(true);
			addblock(true);
			addblock(true);

			gameOver = false;
		}
		if(!started){
			started = true;
		}
	}

	public void jump(){
		if(!gameOver && falling == true){	
			if(yMotion > 0){
				yMotion = 0;
			}

			yMotion -= 22;
			jumpCount += 1;
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		
		int speed = 10;
		
		if(jumpCount >= 10){
			speed = 12;
		}
		if(jumpCount >= 20){
			speed = 15;
		}
		if(jumpCount >= 30){
			speed = 18;
		}

		distance = ticks * speed;
		
		if(distance/40 > 2000) speed = 30;
		
		ticks++;

		if(started){
			for(int i = 0; i < blocks.size(); i++){
				Rectangle block = blocks.get(i);
				block.x -= speed;
			}
			if(ticks % 2 == 0 && yMotion < 33){
				yMotion += 2;
				//gravity
			}
			for(int i = 0; i < blocks.size(); i++){
				Rectangle block = blocks.get(i);

				if(block.x + block.width < 0){
					blocks.remove(block);
					addblock(false);
				}
			}

			if(falling == true){  
				flippy.y += yMotion;
			}

			//after flippy is moved, check for collision
			for(Rectangle block : blocks){
				if(block.intersects(flippy)){
					gameOver = true;
					flippy.x = block.x - flippy.width;
					speed = 0;
				}
				if(flippy.y + flippy.height > HEIGHT - 200){
					falling = false;
					onTheGround = true;
				}
				else{
					falling = true;
					onTheGround = false;
				}
			}
		}

		renderer.repaint();
	}

	public void repaint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.drawImage(Ground, 0, HEIGHT - 198, null);

//		g.setColor(Color.gray.darker().darker());
//		g.fillRect(flippy.x, flippy.y, flippy.width, flippy.height);
		g.drawImage(bullet, flippy.x, flippy.y, null);

		for(Rectangle block: blocks){
			paintblocks(g, block);
		}


		if(!started){
			g.drawImage(ClickToStart, 100, 50, null);
			g.setColor(Color.black);
			g.drawString("DIRECTIONS:", 300, 160);
			g.drawString("- Jump to avoid guns", 300, 175);
			g.drawString("- Bullet will jump when space bar is released", 300, 190);
			g.drawString("- Make sure to read the game over screen", 300, 205);
		}
		if(gameOver){
			g.drawImage(GAMEOVER, 0, 0, null);
		}
		if(!gameOver && started){
			g.setColor(Color.black);
			g.drawString("guns jumped: " + jumpCount, 100, 85);
			g.drawString("distance dashed: " + String.valueOf(distance/40) + "m", 100, 100);
			g.drawString("Don't let the bullet drop into the gun!", 300, 175);
			if(distance/40 > 2000){
				g.setFont(new Font("Arial", 1, 100));
				g.drawString("DIE!", 100, 190);
			}
		}
	}

//	public static void main(String[] args){
////		Bullet = new Bullet();
//		gui = new Gui();
//	}

	public void mouseClicked(MouseEvent arg0) {
		start();
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(onTheGround){
				falling = true;
				jump();
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {

	}

}