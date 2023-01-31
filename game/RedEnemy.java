package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class RedEnemy extends Enemy {
	
	public RedEnemy(Game game) {
		super(game);
		hitPoints = 1;
		Random random = new Random();
		speed = random.nextInt(8) + 3;
	}
	
	public RedEnemy(Game game, int x, int y) {
		super(game, x, y);
		hitPoints = 1;
		Random random = new Random();
		speed = random.nextInt(8) + 3;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}
