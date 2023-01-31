package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class BlueEnemy extends Enemy {

	public BlueEnemy(Game game) {
		super(game);
		hitPoints = 2;
		Random random = new Random();
		speed = random.nextInt(5) + 3;
	}
	
	public BlueEnemy(Game game, int x, int y) {
		super(game, x, y);
		hitPoints = 2;
		Random random = new Random();
		speed = random.nextInt(5) + 3;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
	
}
	
