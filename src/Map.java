import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
//	25 * 17

import javax.imageio.ImageIO;
public class Map extends GameObject{


	public  static int width = 25;
	public  static int height = 17;
	private static Image grass_tex ;
	private static Image o_grass_tex ;
	private static Image ground_tex;
	private static Image potal_tex;
	private static Image gate_tex;
	private static Image board_tex ;
	private static Image frame_tex;
	private static Image option_frame_tex;
	private static Image[] towers_tex;
	private static boolean is_init;
	private static int padding = 15;
	private static ArrayList<ArrayList<Integer>> map ;
	private int level ;
	public static int BLOCK_SIZE =40;
	private boolean is_choosing;
	private int t_type ;
	private int[] tower_range;
	private Point start;
	public static  boolean invalid;
	public Map() {
		super(0, 0, ID.Level);		
		this.level = 1 ;
		this.loadMap();
	}
	
	public Map(int level) {
		super(0, 0, ID.Level);
		// TODO Auto-generated constructor stub
		this.level = level ;
		this.loadMap();
	}
	
	public void loadMap() {
		//load text
		this.start = new Point(0,0);
		System.out.println("init success");
		if(!is_init) {
			try {
				Map.grass_tex = ImageIO.read(new File("res/grass.jpeg"));
				Map.o_grass_tex = ImageIO.read(new File("res/grass2.jpeg"));
				Map.ground_tex = ImageIO.read(new File("res/ground.jpeg"));
				Map.potal_tex = ImageIO.read(new File("res/portal.png"));
				Map.gate_tex = ImageIO.read(new File("res/gate.png"));
				Map.board_tex = ImageIO.read(new File("res/score_background.jpeg"));
				Map.frame_tex = ImageIO.read(new File("res/frame.png"));
				Map.option_frame_tex = ImageIO.read(new File("res/option_frame.png"));
				towers_tex =new  Image[6];
				tower_range = new int[6];
				
				for (int i=1;i<=6;i++) {
					Map.towers_tex[i-1] = ImageIO.read(new File("res/tower"+Integer.toString(i)+".png"));
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.print("Cant load map");
			}
		}
		
		
		
		String path = "level/"+Integer.toString(this.level)+".level";
		map = new ArrayList<ArrayList<Integer>>();
		for (int i=0;i<Map.height;i++) {
			map.add(new ArrayList<Integer>());
			for(int j=0;j<Map.width;j++)
				map.get(i).add(0);
		}
		
		File f = new File(path);
		try {
			Scanner s = new Scanner(f);
			while(s.hasNext())
			{
				int data = s.nextInt();
				Map.map.get(y).set(x,data);
				if(data==2) {
					this.start.x=x;
					this.start.y=y;
				}
				if(x<Map.width-1)
					x++;
				else
				{
					x=0;
					y++;
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getLevel() {
		return this.level;
	}


	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	public void chnageLevel(int level) {
		this.level = level;
		this.loadMap();
	}
	public static ArrayList<ArrayList<Integer>> getMap(){
		return Map.map;
	}
	
	
	public void setChoosing(boolean is_choosing,int type)
	{
		this.t_type = type;
		this.is_choosing = is_choosing;
	}
	public void  render(Graphics g) {
		for(int i=0;i<map.size();i++) {
			for(int j=0;j<map.get(i).size();j++) {
				int x = j*BLOCK_SIZE;
				int y = i*BLOCK_SIZE;
				switch(map.get(i).get(j))
				{
					case -1:
						g.drawImage(Map.o_grass_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						break;
					case 0:
						g.drawImage(Map.grass_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						break;
					case 1:
						g.drawImage(Map.ground_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						break;
					case 2:
						g.drawImage(Map.ground_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						g.drawImage(Map.potal_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						break;
					case 3:
						g.drawImage(Map.ground_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						g.drawImage(Map.gate_tex,x,y,Map.BLOCK_SIZE,Map.BLOCK_SIZE,null);
						break;
				}
			}
		}

		g.drawImage(Map.board_tex,Map.width*BLOCK_SIZE,0,Game.WIDTH-Map.width*BLOCK_SIZE,Map.height*BLOCK_SIZE,null);
		g.drawImage(Map.frame_tex,Map.width*BLOCK_SIZE,0,Game.WIDTH-Map.width*BLOCK_SIZE,Map.height*BLOCK_SIZE,null);
		//in o chon quan va khung 
		int block_size = (int)((Game.WIDTH- Map.width*BLOCK_SIZE -2*padding )/3);
		g.setColor(new Color(102, 179, 255));
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<3;j++){
				x= Map.width*BLOCK_SIZE+padding+ j*block_size;
				y= Map.height*BLOCK_SIZE - padding - (2-i)*block_size- 20;
				g.fillRect(x+(padding/2), y+(padding/2), block_size-padding, block_size-padding);
				g.drawImage(option_frame_tex, x, y, block_size, block_size, null);
				g.drawImage(towers_tex[j+i*3],x+(padding/2), y+(padding/2), block_size-padding, block_size-padding,null);
			}
		}
		
		
		if(this.is_choosing) {
			g.drawImage(Map.towers_tex[this.t_type],MouseInput.MouseX-Tower.width/2,MouseInput.MouseY-Tower.height/2,Tower.width,Tower.height,null);
			g.setColor(new Color(0,200,200,50));
			if(Map.invalid)
				g.setColor(new Color(200,0,0,50));
			g.fillOval(MouseInput.MouseX-Tower.DEFAULT_RANGE,MouseInput.MouseY-Tower.DEFAULT_RANGE,Tower.DEFAULT_RANGE*2,Tower.DEFAULT_RANGE*2);
		}
	}
	
	public static int getValueAt(int x,int y) {
		return Map.map.get(y).get(x);
	}
	
	public static void setValueAt(int x, int y,int value) {
		Map.map.get(y).set(x, value);
	}
	
	public  Point getStart() {
		return this.start;
	}
	
	public static boolean isValid(int x,int y) {
		return (x>=0&&x<Map.width&&y>=0&&y<Map.height);
	}
}
