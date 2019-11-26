import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MouseInput extends MouseAdapter{
	public static int MouseX=0;
	public static int MouseY=0;
	private Handler handler;
	private boolean choosing = false;
	private int type ;
	private int ix;
	private int iy;
								//BLOCK_SIZE +padding		
	public static int option_size =(int)((Game.WIDTH- Map.width*40 -2*15 )/3);
	public static Point top_op1 = new Point (Map.width*40+15,Map.height*Map.BLOCK_SIZE - 15- (2)*option_size);

	
	public MouseInput(Handler handler) {
		this.handler = handler;
	}
	 public void mouseClicked(MouseEvent e) {
		 MouseInput.MouseX = e.getX();
		 MouseInput.MouseY =e.getY();
		 ArrayList<GameObject> objects = handler.getObjects(ID.Level);
			 for(int in=0 ; in<objects.size();in++) {
				 GameObject ob = objects.get(in);
				 if(ob.getId()==ID.Level) {
					 
					 Map m = (Map)ob;
					 
					 if(this.choosing) {
//						
						 	if(Map.getValueAt(ix,iy)==0&&MouseX < Map.width*Map.BLOCK_SIZE-10) {
						 		if(handler.getGold()>=Tower.prices[this.type-1]) {
						 			handler.setGold(handler.getGold()-Tower.prices[this.type-1]);
									this.choosing = false;
									int x= (int)ix*Map.BLOCK_SIZE+Tower.width/2;
									int y =(int)iy*Map.BLOCK_SIZE;
									this.handler.addObject(new Tower(x,y,this.type)); 
									Map.setValueAt(ix, iy,-1);
									m.setChoosing(this.choosing,-1);
									
						 		}
						 	}
						 	else {
						 		
						 	}
					 }
					 
					 for(int i =0;i<2;i++)
					 {
						 for(int j=0;j<3;j++) {
							 int top_x = top_op1.x+j*option_size ;
							 int top_y=top_op1.y+i*option_size;
							 if(e.getX()>top_x&& e.getX()<top_x+option_size&&
							    e.getY()>top_y && e.getY()<top_y+option_size) {
								 
								 int index = i*3+j;
								 System.out.println("enter option" + Integer.toString(index));
								 this.choosing = true;
								 this.type = index+1;
								 m.setChoosing(this.choosing,index);
							 }
						 }
					 }
	
				 }
			 }
	 }
	 public void mouseEntered(MouseEvent e) {
		 MouseInput.MouseX = e.getX();
		 MouseInput.MouseY =e.getY();
	 }
	 public void mouseReleased(MouseEvent e) {
		 MouseInput.MouseX = e.getX();
		 MouseInput.MouseY =e.getY();
		 
	 }
	 public void mousePressed(MouseEvent e) {
		 MouseInput.MouseX = e.getX();
		 MouseInput.MouseY =e.getY();
		 
	 }
	 public void mouseDragged(MouseEvent e) {
		 MouseInput.MouseX = e.getX();
		 MouseInput.MouseY =e.getY();

	 }
	 public void  mouseMoved(MouseEvent e) {
		 MouseInput.MouseX = e.getX();
		 MouseInput.MouseY =e.getY();
		 if(this.choosing) {
			 if(MouseX < Map.width*Map.BLOCK_SIZE-10) {
					this.ix= (int)(Math.floor(MouseX /Map.BLOCK_SIZE));
					this.iy =(int)(Math.floor(MouseY /Map.BLOCK_SIZE));
					System.out.println(ix+" "+iy+" "+Map.getValueAt(ix,iy));
					
					 if(Map.getValueAt(ix, iy)!=0){
						 Map.invalid =true;
					 }else {
						 Map.invalid= false;
					 }
			 }
			 
		 }
	 }
}
