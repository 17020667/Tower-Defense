import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import java.util.Random;
public class Game  extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1902147405213257243L;
	public static final int WIDTH =1300, HEIGHT =740;
	private Thread thread ;
	private Handler handle ;
	private Map map;
	private boolean running =false;
	public Game() {
		Random rand = new Random(System.currentTimeMillis());
		this.map = new Map();
		handle = new Handler();
		handle.addObject(map);
		this.addKeyListener(new KeyInput(this.handle));
		MouseInput adapter = new MouseInput(this.handle);
		this.addMouseListener(adapter);
		this.addMouseMotionListener(adapter);
		new Window(WIDTH,HEIGHT,"Tower Defence ",this);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception  e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTick = 60.0;
		double ns = 1000000000/amountOfTick;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames =0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta>=1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			}
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer +=1000;
//				System.out.println("FPS "+frames);
				frames =0;
			}
		}
		stop();
	}
	
	private void tick() {
		this.handle.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs ==null) {
			this.createBufferStrategy(3);
			return ;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0, WIDTH, HEIGHT);
		this.map.render(g);
		this.handle.render(g);
		g.dispose();
		bs.show();
	}
	
	
	
	public static void main(String args[]) {
		new Game();
	} 
}
