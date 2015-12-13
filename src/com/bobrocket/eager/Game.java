package com.bobrocket.eager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.bobrocket.eager.data.Vector;
import com.bobrocket.eager.entity.*;
import com.bobrocket.eager.graphics.Fonts;
import com.bobrocket.eager.input.KeyHandler;
import com.bobrocket.eager.input.MouseHandler;

public class Game extends AbstractGame {

	private JFrame gameFrame;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private Player player;
	
	private List<AbstractEntity> enemies = new ArrayList<AbstractEntity>();
	
	private long enemySpawnConstant = 10000;
	private long lastEnemySpawnTime;
	
	private long coinSpawnConstant = 2500;
	private long lastCoinSpawnTime;
	
	private Random rand = new Random(System.currentTimeMillis());
	
	public List<AbstractEntity> getEnemies() {
		return enemies;
	}
	
	public Game() {
		super();
		
		setMinimumSize(new Dimension(512, 512));
		setMaximumSize(new Dimension(512, 512));
		setPreferredSize(new Dimension(512, 512));
		
		gameFrame = new JFrame("Ludum Dare 34");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(this, BorderLayout.CENTER);
		gameFrame.pack();
		
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		
		keyHandler = new KeyHandler(this);
		mouseHandler = new MouseHandler(this);
		player = new Player(this, 200, 200);
		
		enemies.add(new Enemy(this, 10, 100));
		enemies.add(new Enemy(this, 50, 50));
		
		lastEnemySpawnTime = System.currentTimeMillis();
		lastCoinSpawnTime = System.currentTimeMillis();

		Fonts.loadFont("LD34");
		
		Audio.playLooped("song");
	}
	
	public int rand(int min, int max) {
		int range = max - min;
		return rand.nextInt(range) + min;
	}
	
	public Vector entityRand() {
		int randomX = rand(20, 490);
		while (Math.abs(player.getX() - randomX) <= 50) randomX = rand(20, 490);
		int randomY = rand(20, 490);
		while (Math.abs(player.getY() - randomY) <= 50) randomY = rand(20, 490);
		return new Vector(randomX, randomY);
	}
	
	@Override
	public void tick() {
		if (player.getSize() == 0 && getMouseHandler().isClicked()) {
			enemies = new ArrayList<AbstractEntity>();
			
			player = new Player(this, 200, 200);
			enemies.add(new Enemy(this, 10, 100));
			enemies.add(new Enemy(this, 50, 50));
			
			lastEnemySpawnTime = System.currentTimeMillis();
			lastCoinSpawnTime = System.currentTimeMillis();
		}
		
		if (System.currentTimeMillis() > ((lastEnemySpawnTime + enemySpawnConstant) - (player.getSize() * 50.5) + (enemies.size() * 88))) {
			Vector enemySpawnPoint = entityRand();
			Enemy tmpEnemy = new Enemy(this, (int)enemySpawnPoint.getX(), (int)enemySpawnPoint.getY());
			
			if (rand(0, 50) == 3) tmpEnemy.setSize(player.getSize() + rand(8, 42)); //big ass enemy :^)
			else if (rand(0, 50) == 2) tmpEnemy.setSize(rand(18, 49));
			else if (rand(0, 18) == 4) tmpEnemy = new ZoomerEnemy(this, (int)enemySpawnPoint.getX(), (int)enemySpawnPoint.getY());
			
			enemies.add(tmpEnemy);
			lastEnemySpawnTime = System.currentTimeMillis();
		}
		
		if (System.currentTimeMillis() > ((lastCoinSpawnTime + coinSpawnConstant) + (player.getSize() * 20.5) - (enemies.size() * 40))) {
			enemies.add(new Coin(this, rand(20, 490), rand(20, 490)));
			lastCoinSpawnTime = System.currentTimeMillis();
		}
		
		for (AbstractEntity enemy : enemies) {
			if (enemy.getBounds().intersects(player.getBounds())) enemy.onCollide(player);
			for (AbstractEntity enemy2 : enemies) {
				if (!enemy.equals(enemy2) && enemy.getBounds().intersects(enemy2.getBounds())) enemy.onCollide(enemy2);
			}
			enemy.tick();
		}
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		player.render(g);
		for (AbstractEntity enemy : enemies) enemy.render(g);
		
		g.setColor(Color.WHITE);
		Fonts.drawText(g, "LD34", "" + player.getSize(), 24, 30, 30);
		
		if (player.getSize() == 0) {
			Fonts.drawText(g, "pixelmix_bold", "You are dead!", 36, 75, 230);
			Fonts.drawText(g, "pixelmix_bold", "Click to restart", 18, 150, 270);
		}
	}

	public synchronized void start() {
		isRunning = true;
		mainGameThread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
	}
	
	public static void main(String[] args) {
		new Game().start();
	}
	
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}
	
	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}
	
	public Player getPlayer() {
		return player;
	}
}
