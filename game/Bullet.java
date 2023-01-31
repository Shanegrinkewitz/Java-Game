package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
	
	private int x;
	private int y;
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	private final int SPEED = 8;
	
	private Game game;
	
	public Bullet(Game game, int x, int y) {
		this.x = x - WIDTH;
		this.y = y - HEIGHT / 2;
		this.game = game;
	}
	
	public void move() {
		x += SPEED;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
  	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public void checkIfOffScreen() {
		if (x > game.getWidth()) {
			game.bullets.remove(this);
		}
	}
}
