package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public abstract class Enemy {
	
	protected int x;
	protected int y;
	protected int speed;
	protected int hitPoints;
	protected final int WIDTH = 50;
	protected final int HEIGHT = 50;
	
	protected Game game;
	
	public Enemy(Game game) {
		this.game = game;
		Random random = new Random();
		x = game.getWidth();
		y = random.nextInt(game.getHeight() - HEIGHT);
	}
	
	public Enemy(Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		x -= speed;
	}
	
	public abstract void draw(Graphics g);
	
	public void checkIfOffScreen() {
		if (x < 0) {
			game.enemies.remove(this);
			game.score -= 250;
			if (game.score < 0) {
				game.score = 0;
			}
		}
	}
	
	public void checkCollisions(Player player) {
		if (getBounds().intersects(player.getBounds())) {
			game.enemies.remove(this);
			game.gameOver();
		}
	}
	
	public void checkCollisions(Bullet bullet) {
		if (getBounds().intersects(bullet.getBounds())) {
			hitPoints--;
			if (hitPoints <= 0) {
				game.enemies.remove(this);
			}
			game.bullets.remove(bullet);
			game.score += 50;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
