import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;;
public class Tower extends GameObject{
	private int type;
	private int damage;
	private int range ;
	public static int width = 50;
	public static int height = 60;
	public static int DEFAULT_RANGE=120;
	private Image texture;
	public boolean show_range ;
	private int bullet_delay = 40;
	private int counter =0;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static int[] prices= {50,70,90,110,160,200};
	public Tower(int x, int y,int type) {
		super(x, y, ID.Tower);
		this.type = type;
		this.range = DEFAULT_RANGE;
		// TODO Auto-generated constructor stub
		try {
			switch(type) {
			case 1:
				this.texture = ImageIO.read(new File("res/tower1.png"));
				this.damage=10;
				break;
			case 2:
				this.texture = ImageIO.read(new File("res/tower2.png"));
				this.damage=15;
				break;
			case 3:
				this.texture = ImageIO.read(new File("res/tower3.png"));
				this.damage=20;
				break;
			case 4:
				this.texture = ImageIO.read(new File("res/tower4.png"));
				this.damage=25;
				break;
			case 5:
				this.texture = ImageIO.read(new File("res/tower5.png"));
				this.damage=30;
				break;
			case 6:
				this.texture = ImageIO.read(new File("res/tower6.png"));
				this.damage=35;
				break;
			default: 
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public void attack(Enemy e) {
		
		counter +=1;
		if(counter >=bullet_delay) {
			this.bullets.add(new Bullet(this.getX(),this.getY(),e,this.damage));
			counter =0;
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		for(int i=0;i<bullets.size();i++)
		{
			
			if(bullets.get(i)!=null) {
				
				if(bullets.get(i).is_reached())
					this.bullets.remove(i);
				else
					this.bullets.get(i).tick();
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.texture,this.x-Tower.width/2-4,this.y-Tower.height/3,Tower.width,Tower.height,null);
		g.setColor(Color.blue);
		if(this.show_range) {
			g.setColor(new Color(0,200,0,80));
			g.fillOval(this.x-this.range,this.y-this.range,this.range*2,this.range*2);
		}
		for(int i=0;i<bullets.size();i++)
		{
			if(bullets.get(i)!=null&!bullets.get(i).is_reached())
				this.bullets.get(i).render(g);
		}
	}
	
}
