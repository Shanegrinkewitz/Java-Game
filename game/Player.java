package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
	
	private final int X = 40;
	private int y;
	private int velocityY = 0;
	private final int SPEED = 5;
	private int WIDTH = 50;
	private int HEIGHT = 50;
	
	private boolean canShoot = true;
	private Thread cooldown;
	
	private Game game;
	
	public Player(Game game) {
		this.game = game;
		y = game.getHeight() / 2 - HEIGHT / 2;
	}
	
	public void move() {
		if (velocityY == -SPEED && y > 0) {
			y += velocityY;
		}else if (velocityY == SPEED && y < game.getHeight() - HEIGHT) {
			y += velocityY;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(X, y, WIDTH, HEIGHT);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(X, y, WIDTH, HEIGHT);
	}

	public void keyPressed(int key) {
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			velocityY = -SPEED;
		}else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			velocityY = SPEED;
		}else if (key == KeyEvent.VK_SPACE){
			game.spaceBarDown = true;
		}
	}

	public void keyReleased(int key) {
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_D || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			velocityY = 0;
		}else if (key == KeyEvent.VK_SPACE) {
			game.spaceBarDown = false;
		}
	}
	
	public void shoot() {
		if (canShoot) {
			game.bullets.add(new Bullet(game, X + WIDTH, y + HEIGHT / 2));
			canShoot = false;
			cooldown = new Thread() {
				public void run() {
					try {
						Thread.sleep(500);
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
					canShoot = true;
				}
			};
			cooldown.start();
		}
	}
}
	
	
	
	
	
	
	