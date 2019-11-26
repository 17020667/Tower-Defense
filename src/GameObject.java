import java.awt.Graphics;

public abstract class GameObject {
	protected int x,y;
	protected ID id;
	protected int velx ,vely; 
	
	public GameObject(int x , int y ,ID id) {
		this.x = x;
		this.y = y;
		this.id  = id;
	}
	
	public abstract void tick ();
	public abstract void render(Graphics g);

	public void setX(int x) {
		this.x =x ;
	}
	
	public void setY(int y) {
		this.y=y;
	}
	
	public void setVelX(int velx) {
		this.velx = velx;
	}
	
	public void setVelY(int vely) {
		this.vely=vely;
	}
	
	public void setId(ID id) {
		this.id = id;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getVelX() {
		return this.velx;
	}
	
	public int getVelY() {
		return this.vely;
	}
	
	public ID getId() {
		return this.id;
	}
}
