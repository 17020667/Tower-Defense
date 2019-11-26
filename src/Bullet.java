import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Bullet extends GameObject{
	private Enemy target ;
	private Image tex; 
	private boolean reached =false;
	private int damage;
	public static int size =7;
	public Bullet(int x, int y,Enemy e,int damage) {
		super(x, y, ID.Bullet);
		this.damage = damage;
		this.velx= 2;
		this.vely =2;
		this.target = e;
		try {
			this.tex = ImageIO.read(new File("res/b"+Integer.toString(new Random().nextInt(6)+1)+".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	public boolean is_reached() {
		return this.reached;
	}

	@Override
	public void tick() {	
		// TODO Auto-generated method stub
		if(!reached) {
			this.x+=velx*(x -(target.getX()+target.getWidth()/2) >0 ? -1: 1);
			this.y+=vely*(y -(target.getY()+target.getHeight()/2) >0 ? -1: 1);
			if((int)Math.sqrt(Math.pow((this.getX()-(target.getX()+target.getWidth()/2)), 2)+Math.pow((this.getY()-(target.getY()+target.getHeight()/2)), 2))<=10) {
				this.reached = true;
				target.damaged(this.damage);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(tex, x, y, size, size, null);
	}

}
