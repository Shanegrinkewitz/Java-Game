package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, Runnable {
	
	private final int WIDTH = 640;
	private final int HEIGHT = 320;

	private static final double TARGET_FPS = 60.0;
	
	private Thread gameThread;
	private boolean running = true;
	
	public Player player;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private EnemySpawn enemySpawn;
	
	public boolean spaceBarDown = false;
	private boolean enemyCreated = false;
	
	public int score = 0;

	public static void main(String[] args) {
		JFrame window = new JFrame("Game");
		Game game = new Game();
		game.setPreferredSize(new Dimension(game.WIDTH, game.HEIGHT));
		window.setContentPane(game);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		System.out.println(game.getWidth());
		System.out.println(game.getHeight());
		game.gameThread.start();
	}
	
	public Game() {
		setBackground(Color.WHITE);
		setFocusable(true);
		addKeyListener(this);
		gameThread = new Thread(this);
		gameThread.setName("Game Thread");
	}
	
	public void run() {
		init();
		startGameLoop();
	}
	
	public void init() {
		player = new Player(this);
		score = 0;
		spaceBarDown = false;
		enemyCreated = false;
		running = true;
		enemySpawn = new EnemySpawn();
		enemySpawn.setName("Enemy Spawn");
		enemySpawn.start();
	}
	
	private void startGameLoop() {
		while (running) {
			long beforeUpdate = System.currentTimeMillis();
			if (spaceBarDown) {
				player.shoot();
			}
			if (enemyCreated) {
				Random random = new Random();
				int randomNumber = random.nextInt(10);
				if (randomNumber > 2) {
					enemies.add(new RedEnemy(this));
				}else {
					enemies.add(new BlueEnemy(this));
				}
				enemyCreated = false;
			}
			update();
			repaint();
			checkCollisions();
			long afterUpdate = System.currentTimeMillis() - beforeUpdate;
			System.out.println("after: " + afterUpdate);
			long delay = (long) (1000 / TARGET_FPS - afterUpdate);
			try {
				Thread.sleep(delay);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		player.move();
		for (Bullet bullet : bullets) {
			bullet.move();
		}
		
		for (Enemy enemy : enemies) {
			enemy.move();
		}
	}
	
	private void checkCollisions() {
		ArrayList<Bullet> bulletListClone = new ArrayList<Bullet>(bullets);
		for (Bullet bullet : bulletListClone) {
			bullet.checkIfOffScreen();
		}
		
		ArrayList<Enemy> enemyListClone = new ArrayList<Enemy>(enemies);
		for (Enemy enemy : enemyListClone) {
			enemy.checkIfOffScreen();
		}
		
		enemyListClone = new ArrayList<Enemy>(enemies);
		for (Enemy enemy : enemyListClone) {
			enemy.checkCollisions(player);
			
			bulletListClone = new ArrayList<Bullet>(bullets);
			for (Bullet bullet : bulletListClone) {
				enemy.checkCollisions(bullet);
			}
		}

	}
	
	public void gameOver() {
		running = false;
		enemySpawn.interrupt();
		
		//Empty the bullets list and enemies list.
		bullets.clear();
		enemies.clear();
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (!running) {
			g2.setColor(Color.BLACK);
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
			g2.drawString("Game Over", getWidth() / 2 - 75, getHeight() / 2 - 50);
			g2.drawString("Score: " + score, getWidth() / 2 - 75, getHeight() / 2);
			return;
		}
		
		if (player == null) return;
		
		player.draw(g2);
		ArrayList<Bullet> bulletListClone = new ArrayList<Bullet>(bullets);
		for (Bullet bullet : bulletListClone) {
			bullet.draw(g2);
		}
		ArrayList<Enemy> enemyListClone = new ArrayList<Enemy>(enemies);
		for (Enemy enemy : enemyListClone) {
			enemy.draw(g2);
		}
		drawScore(g2);
	}
	
	private void drawScore(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		g.drawString("Score: " + score, getWidth() / 2 - 25, 20);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (running) {
			player.keyPressed(key);
		}
		
		//If the R key is pressed while the game is no longer running, the game restarts.
		if (!running && key == KeyEvent.VK_R) {
			gameThread = new Thread(this);
			gameThread.setName("Game Thread");
			gameThread.start();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (running) {
			player.keyReleased(e.getKeyCode());
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	
	private class EnemySpawn extends Thread {
		
		public void run() {
			System.out.println("started");
			Random random = new Random();
			while (running) {
				try {
					Thread.sleep(random.nextInt(2001) + 500);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
					break;
				}
				enemyCreated = true;
			}
		}
	}	
}
