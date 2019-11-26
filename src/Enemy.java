import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
public class Enemy extends GameObject{
	private static Image tex;
	private int frame_x,frame_y;
	private  int width=50;
	private  int height=50;
	private int max_heart ;
	private int remain_heart;
	private int gold;
	private int score;
	private int ix ,iy;
	private Point target;
	private Point last ;
	private long start_time ;
	private boolean reached= false;
	public Enemy(int x, int y) {
		super(x, y, ID.Enemy);
		Random rand = new Random();
		start_time = System.currentTimeMillis();
		// TODO Auto-generated constructor stub
		int rand_num = rand.nextInt(24);
		this.max_heart = rand.nextInt(100)+30;
		this.remain_heart = this.max_heart;
		this.velx = 2;
		this.gold = (rand.nextInt(3)+1)*10;
		this.score = 100;
		this.vely = this.velx;
		this.frame_x = rand_num%6;
		this.frame_y = rand_num/6;
		this.ix = (int)((this.x+width/2)/Map.BLOCK_SIZE);
		this.iy = (int)((this.y+height/2)/Map.BLOCK_SIZE);

		this.target= new Point(x+width/2,y+height/2);
		this.last = new Point(ix,iy);
		try {
			tex = ImageIO.read(new File("res/monsters.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {

		this.ix = (int)((this.x+width/2)/Map.BLOCK_SIZE);
		this.iy = (int)((this.y+height/2)/Map.BLOCK_SIZE);

		// TODO Auto-generated method stub
		if(Map.getValueAt(this.ix, this.iy)==3)
			this.reached =true;
		if(System.currentTimeMillis()-this.start_time>1000) {
			this.start_time = System.currentTimeMillis();
			//change block
			ArrayList<Integer> path =new ArrayList<Integer>();
			if(Map.isValid(this.ix+1, this.iy)&&(Map.getValueAt(this.ix+1,this.iy)==1||Map.getValueAt(this.ix+1,this.iy)==3)&&(this.ix+1)!=last.x ) {
				path.add(1);
			}
			if(Map.isValid(this.ix-1, this.iy)&&(Map.getValueAt(this.ix-1,this.iy)==1||Map.getValueAt(this.ix-1,this.iy)==3)&&(this.ix-1)!=last.x ) {
				path.add(2);
			}
			if(Map.isValid(this.ix, this.iy+1)&&(Map.getValueAt(this.ix,this.iy+1)==1||Map.getValueAt(this.ix,this.iy+1)==3)&&(this.iy+1)!=last.y ) {
				path.add(3);
			}
			if(Map.isValid(this.ix, this.iy-1)&&(Map.getValueAt(this.ix,this.iy-1)==1||Map.getValueAt(this.ix,this.iy-1)==3)&&(this.ix-1)!=last.y ) {
				path.add(4);
			}
			last.x = this.ix;
			last.y= this.iy;
			if(path.size()!=0) {
				int dir = path.get((new Random()).nextInt(path.size()));
				switch(dir) {
					case 1:
						this.ix += 1;
						this.x = (this.ix)*Map.BLOCK_SIZE;
						break;
					case 2:
						this.ix -= 1;
						this.x = (this.ix)*Map.BLOCK_SIZE;
						break;
					case 3:
						this.iy += 1;
						this.y = (this.iy)*Map.BLOCK_SIZE;
						break;
					case 4:
						this.iy -= 1;
						this.y = (this.iy)*Map.BLOCK_SIZE;
						break;
					default:
						break;
				}
			}
		}
		
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		int i_width = tex.getWidth(null);
		int i_height = tex.getHeight(null);
		g.drawImage(tex,x,y,x+width,y+height,(i_width/6)*(frame_x),(i_height/4)*(frame_y),(i_width/6)*(frame_x+1),(i_height/4)*(frame_y+1),null);
		renderHeartBar(g);
	}
	
	private void renderHeartBar(Graphics g) {
		int bar_height = 10;
		int padding =5;
		g.setColor(Color.black);
		g.drawRect(this.x,this.y-padding -bar_height, this.width, bar_height);
		g.setColor(Color.red);
		g.fillRect(this.x,this.y-padding -bar_height,  (int)((float)this.remain_heart*this.width/this.max_heart),  bar_height);
		
	}
	
	public void damaged(int damage) {
		this.remain_heart = (this.remain_heart-damage <0 ? 0 : this.remain_heart-damage);
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public  int getHeight() {
		return this.height;
	}
	
	public int getRemainHeart() {
		return this.remain_heart;
	}
	
	public int getGold() {
		return this.gold;
	}
	
	public int getScore() {
		return this.score;
	}
	public boolean isReached() {
		return this.reached;
	}
	
}
