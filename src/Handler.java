import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Handler {
	ArrayList<GameObject> towers = new ArrayList<GameObject>();
	ArrayList<GameObject> maps = new ArrayList<GameObject>();
	ArrayList<GameObject> enemys = new ArrayList<GameObject>();
	public boolean end=false;
	private Image l_heart_tex;
	private Image h_heart_tex;
	private int score=0;
	private int gold =100;
	private int life =5;
	private int remain_life;
	private long start_time;
	
	//cap nat object
	public void tick() {
		if(this.remain_life<=0)
			this.end=true;
		if(!this.end) {
			for(int i=0 ;i<this.maps.size();i++)
				maps.get(i).tick();	
			for(int i=0 ;i<this.towers.size();i++)
				towers.get(i).tick();	
			for(int i=0 ;i<this.enemys.size();i++)
				enemys.get(i).tick();
			
			
	
			for(int i=0 ;i<this.towers.size();i++)
			{
				int min = Integer.MAX_VALUE;
				int minj=0;
				for(int j=0 ;j<this.enemys.size();j++) {
					if(Math.sqrt(Math.pow((towers.get(i).getX()-enemys.get(j).getX()), 2)+Math.pow((towers.get(i).getY()-enemys.get(j).getY()), 2))<min) {
						min =(int)Math.sqrt(Math.pow((towers.get(i).getX()-enemys.get(j).getX()), 2)+Math.pow((towers.get(i).getY()-enemys.get(j).getY()), 2));
						minj =j;
						System.out.print(min+" "+minj);
					}
				}
				if(this.enemys.size()!=0) {
					Tower t = (Tower)towers.get(i);
					if(min<=t.getRange())
						t.attack((Enemy) this.enemys.get(minj));
				}
			}
			
			for(int i=0;i<this.enemys.size();i++) {
				Enemy e = (Enemy) this.enemys.get(i);
				if(e.getRemainHeart()<=0) {
					this.gold+=e.getGold();
					this.score+=e.getScore();
					this.enemys.remove(e);
					
				}
				if(e.isReached()) {
					this.remain_life-=1;
					this.enemys.remove(e);
				}
			}
		}
	}
	
	//ve object
	public void render(Graphics g) {
		if(!this.end) {
			for(int i=0 ;i<this.maps.size();i++)
				maps.get(i).render(g);	
			for(int i=0 ;i<this.towers.size();i++)
				towers.get(i).render(g);	
			for(int i=0 ;i<this.enemys.size();i++)
				enemys.get(i).render(g);	
			g.setColor(Color.red);
		    g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
			g.drawString("Score : "+Integer.toString(this.score),Map.width*Map.BLOCK_SIZE+25,70);
			g.setColor(Color.yellow);
			g.drawString("Gold : "+Integer.toString(this.gold),Map.width*Map.BLOCK_SIZE+25,120);
			renderLife(g);
		}else
		{
			g.setColor(Color.black);
			g.fillRect(Game.WIDTH/2-300, Game.HEIGHT/2-200, 600,400);
			g.setColor(Color.red);
			g.drawString("Game Over", Game.WIDTH/2-200, Game.HEIGHT/2-50);
		}
	}
		//them object
		public void addObject(GameObject object) {
			switch(object.getId()) {
				case Level:
					this.maps.add(object);
					break;
				case Tower:
					this.towers.add(object);
					break;
				case Enemy:
					this.enemys.add(object);
					break;
			default:
				break;
			}
		}
	
	
	//xoa object
	public void removeObject(GameObject object) {
		switch(object.getId()) {
		case Level:
			this.maps.remove(object);
			break;
		case Tower:
			this.towers.remove(object);
			break;
		case Enemy:
			this.enemys.remove(object);
			break;
		default:
			break;
		}	
	}
	
	public ArrayList<GameObject> getObjects(ID id ){
		switch(id) {
		case Level:
			return this.maps;
		case Tower:
			return this.towers;
		case Enemy:
			return this.enemys;
		default:
			break;
		}	
		return new ArrayList<GameObject>();
	}
	
	
	public Handler() {
		this.start_time = System.currentTimeMillis();
		this.remain_life = life;
		try {
			this.l_heart_tex =ImageIO.read(new File("res/heart_low.png"));
			this.h_heart_tex =ImageIO.read(new File("res/heart_hight.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int size(ID id) {
		switch(id) {
		case Level:
			return this.maps.size();
		case Tower:
			return this.towers.size();
		case Enemy:
			return this.enemys.size();
		default:
			break;
		}
		return 0;
	}
	public void renderLife(Graphics g) {
		if(System.currentTimeMillis()-this.start_time> 5000) {
			Map map= (Map)this.maps.get(0);
			if(map !=null) {
				Point start_point = map.getStart();
				Enemy t_e=new Enemy(start_point.x*Map.BLOCK_SIZE,start_point.y*Map.BLOCK_SIZE);
				this.enemys.add(t_e);
			}
			this.start_time = System.currentTimeMillis();
			//add new monster 	
		}
		for(int i=0;i<this.remain_life;i++) {
			g.drawImage(this.h_heart_tex,Map.width*Map.BLOCK_SIZE+25+i*50,170,50,50,null);
		}
		for(int i=0;i<this.life-this.remain_life;i++) {
			g.drawImage(this.l_heart_tex,Map.width*Map.BLOCK_SIZE+25+(this.remain_life+i)*50,170,50,50,null);
		}
		
	}
	
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getGold() {
		return this.gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public int getLife() {
		return this.life;
	}
}
