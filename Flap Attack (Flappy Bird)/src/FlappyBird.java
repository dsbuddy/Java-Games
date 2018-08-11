import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener{
	
	int[] highscore = new int[10];
	public static FlappyBird flappyBird;
	public Renderer renderer;
	public final int WIDTH = 900, HEIGHT = 600;
	public Random rand;
	public Rectangle bird;
	public ArrayList<Rectangle> pipes;
	public boolean started, gameOver, cheat, up;
	public int ticks, yMotion, score;
	public int max;
	
	public static void main(String[] args) {
		flappyBird = new FlappyBird();

	}

	public FlappyBird() {
		JFrame frame = new JFrame();
		Timer timer = new Timer(20, this);
		
		renderer = new Renderer();
		rand = new Random();
		
		frame.add(renderer);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Floppy Disk");
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		
		frame.setVisible(true);
		
		bird = new Rectangle(WIDTH/2-10, HEIGHT/2-10, 20, 20);
		pipes = new ArrayList<Rectangle>();
		
		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);
		addPipe(true);
		
		timer.start();
		
	}

	
	private void addPipe(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		int distance = 250 + rand.nextInt(50);
		
		if(start) {
			pipes.add(new Rectangle(WIDTH + width + pipes.size() * distance, HEIGHT - height - 120, width, height));
			pipes.add(new Rectangle(WIDTH + width + (pipes.size() - 1) * distance, 0, width, HEIGHT - height - space));
		}
		else{
			pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 2 * distance, HEIGHT - height - 120, width, height));
			pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space));
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (cheat) {
			bird.x = MouseInfo.getPointerInfo().getLocation().x;
			bird.y = MouseInfo.getPointerInfo().getLocation().y;
		}
				
		int speed = 10;
		
		ticks++;
		
		if(started) {
			for(int i = 0; i < pipes.size(); i++) {
				pipes.get(i).x -= speed;
			}
			if(ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2; //gravity

			}
//			for (int i = 0; i < pipes.size(); i++) {
//				if (up) {
//					pipes.get(i).height = (int) (pipes.get(i).getHeight() + 1);
//					up = false;
//				} else {
//					pipes.get(i).height = (int) (pipes.get(i).getHeight() - 1);
//					up = true;
//				}
//			}
			
			for (int i = 0; i < pipes.size(); i++) {
				Rectangle pipe = pipes.get(i);
				if(pipe.x + pipe.width < 0) { 
					pipes.remove(pipe);
					if(pipe.y == 0) {
						addPipe(false);
					}
				}
			}
			
			bird.y += yMotion;
			
			for (Rectangle pipe : pipes) {
				if (pipe.y == 0 && bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 10 && bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 10) {
//				if (bird.x + 20 > pipe.x + 100) {
//					score+=pipes.size()-4;
//				}
					score++;
				}
//				score += pipes.size() - 4;
				
				if (pipe.intersects(bird)) {
					gameOver = true;
					
					if(bird.x <= pipe.x) {
						bird.x = pipe.x - bird.width;
					}
					else{
						if(pipe.y != 0) {
							bird.y = pipe.y - bird.height;
						}
						else if(bird.y < pipe.height) {
							bird.y = pipe.height;
						}
					}
				}
			}
			
			
			
			if(bird.y > HEIGHT - 120 || bird.y < 0) {
				gameOver = true;
			}
			
			if(bird.y + yMotion >= HEIGHT - 120) {
				bird.y = HEIGHT - 120 - bird.height;
				gameOver = true;
			}
			
			renderer.repaint();
			
		}
	}

	public void repaint(Graphics g) {
		g.setColor(Color.CYAN.brighter());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//ground
		g.setColor(Color.decode("#" + (100000 + rand.nextInt(900000))));
		g.setColor(Color.WHITE.darker());
		g.fillRect(0, HEIGHT - 120, WIDTH, HEIGHT);
			

		
		//pipes
		g.setColor(Color.decode("#" + (100000 + rand.nextInt(900000))));
		for(Rectangle pipe : pipes) {
			g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Verdana", 1, 72));
		//bird
		g.setColor(Color.BLACK);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		g.setColor(Color.WHITE);
		g.fillOval(bird.x + 1, bird.y + 1, bird.width - 3, bird.height - 3);
		g.setColor(Color.GRAY);
		g.fillOval(bird.x + 3, bird.y + 3, bird.width - 7, bird.height - 7);
		
		g.setColor(Color.WHITE.darker().darker());
		if (score > 10) g.drawString("Type J for Cheats", 100, 550);
		
		if(!started) {
			g.drawString("Click to Start!", 75, HEIGHT/2 - 50);
		}
		
		if(gameOver) {
			try (Scanner scan = new Scanner(new File("scores.txt"))) {
				max = scan.nextInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		if (score > max) {
			try {
				BufferedWriter writer = new BufferedWriter (new FileWriter(".\\scores.txt"));
				if (score > max) writer.write(Integer.toString(score));
				
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			g.drawString("Your score: " + score, 100, HEIGHT / 2 - 50);
			g.drawString("High score: " + max, 100, HEIGHT / 2 + 10);
		}
			
		if (!gameOver && started) {
			g.drawString(Integer.toString(score), WIDTH / 2 - 25, 100);
		}
		
	}

	
	private void jump() {
		if(gameOver) {
			bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			pipes.clear();
			yMotion = 0;
			score = 0;
			
			addPipe(true);
			addPipe(true);
			addPipe(true);
			addPipe(true);
			
			gameOver = false;
		}
		
		if(!started) {
			started = true;
		}
		
		else if(!gameOver) {
			if(yMotion > 0) {
				yMotion = 0;
			}
			
			yMotion -= 10;
		}
		
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
		if (e.getKeyCode() == KeyEvent.VK_J) {
			cheat = true;
		} else {
			cheat = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
