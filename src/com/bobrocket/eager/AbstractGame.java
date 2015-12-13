package com.bobrocket.eager;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public abstract class AbstractGame extends Canvas implements Runnable {

	private long lastTimeFrames, lastTimeTicks;
	protected boolean isRunning = true;
	protected Thread mainGameThread;
	protected int frames, ticks;
	
	public AbstractGame() {
		mainGameThread = new Thread(this);
	}
	
	@Override
	public void run() {
		lastTimeFrames = System.currentTimeMillis();
		lastTimeTicks = System.currentTimeMillis();
		
		long lastTimer = System.currentTimeMillis();
		
		while (isRunning) {
			
			if ((System.currentTimeMillis() - lastTimeFrames) >= Constants.MILLIS_PER_FRAME) {
				doRender();
				
				frames++;
				lastTimeFrames = System.currentTimeMillis();
			}
			
			if ((System.currentTimeMillis() - lastTimeTicks) >= Constants.MILLIS_PER_FRAME) {//Constants.MILLIS_PER_TICK) {
				doTick();
				
				ticks++;
				lastTimeTicks = System.currentTimeMillis();
			}
			
			if ((System.currentTimeMillis() - lastTimer) >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void doTick() {
		tick();
	}
	
	public void doRender() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bufferStrategy.getDrawGraphics();
		
		render(g);
		
		g.dispose();
		bufferStrategy.show();
	}

}
