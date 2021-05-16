import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

public class Screen extends JPanel implements Runnable{
	public Thread thread = new Thread(this);
	
	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];
	
	public static int myWidth, myHeight;
	
	public static boolean isFirst = true;
	
	public static Point mse = new Point(0,0);
	
	public static Room room;
	public static Save save;
	public static Store store;
	
	public Screen() {
		thread.start();
	}
	
	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();
		
		for(int i=0; i<tileset_ground.length;i++) {
			tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
			tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		for(int i=0; i<tileset_air.length;i++) {
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
		save.loadSave(new File("save/mission1.ulixava"));
	}
	
	public void paintComponent(Graphics g) {
		if(isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			
			isFirst = false;
		}
		
		g.setColor(new Color(50,50,50));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0,0,0));
		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y+room.blockSize); //Drawing the left line
		g.drawLine(room.block[0][room.worldWidth-1].x, 0, room.block[0][room.worldWidth-1].x, room.block[room.worldHeight-1][0].y+room.blockSize); //Drawing the right line

		room.draw(g); //Drawing the room
		store.draw(g); //Drawing the store
	}
	
	public void run() {
		while(true) {
			if(!isFirst) {
				room.physic(); //For all the physics in the game
			}
			
			repaint();
			
			try{
				Thread.sleep(1);
			} catch(Exception e) { }
		}
	}
}
