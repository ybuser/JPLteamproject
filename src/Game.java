import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

public class Game extends JPanel implements Runnable{
	public Thread thread = new Thread(this);
	
	//�̹�����
	public static Image[] groundImageFile = new Image[10];
	public static Image[] charImageFile = new Image[10];
	public static Image[] restImageFile = new Image[10];
	public static Image[] mobImageFile = new Image[10];
	
	public static Mob[] monsters = new Mob[100];
	
	public static boolean first = true;
	
	public static int myWidth, myHeight;
	public static int coinNum = 10, healthNum = 100;
	public static int deadMob = 0, goalNum = 0;
	public static int clearTime = 2000, clearFrame = 0;
	public static int level = 1, maxLevel = 3;
	

	public static boolean flag_Win = false;
	
	public static Point mouse = new Point(0, 0);
	
	public static Map map;
	
	public static Mapinfo mapInfo;
	
	public static Shop shop;
	
	//���� ����
	public Game(Frame frame) {
		frame.addMouseListener(new MouseHandel());
		frame.addMouseMotionListener(new MouseHandel());
		
		thread.start();
	}
	
	//���� Ŭ���� ����
	public static void clear() {
		if(deadMob == goalNum) {
			deadMob = 0;		
			flag_Win = true;
			coinNum = 0;	
		}
	}
	
	//��� ���� ����
	public void define() {
		map = new Map();
		mapInfo = new Mapinfo();
		shop = new Shop();
		
		coinNum = 10;
		healthNum = 20;
		
		//ground �̹���
		for(int i = 0; i<groundImageFile.length; i++) {
			//�̹��� ��ü ����
			groundImageFile[i] = new ImageIcon("image/tile.png").getImage();
			
			//�̹��� �߶󳻱�
			groundImageFile[i] = createImage(new FilteredImageSource(groundImageFile[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		//air �̹���
		for(int i = 0; i<charImageFile.length; i++) {
			charImageFile[i] = new ImageIcon("image/characters.png").getImage();
			charImageFile[i] = createImage(new FilteredImageSource(charImageFile[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
		//������ �̹�����
		restImageFile[0] = new ImageIcon("image/cell.png").getImage();
		restImageFile[1] = new ImageIcon("image/heart.png").getImage();
		restImageFile[2] = new ImageIcon("image/coin.png").getImage();
		
		mobImageFile[0] = new ImageIcon("image/mob_eunhang.png").getImage(); //�� �̹���
		
		//��(��) �����
		mapInfo.loadMap(new File("save/map" + level + ".txt"));
		
		//���� ����
		for(int i = 0; i < monsters.length; i++) {
			monsters[i] = new Mob();
		}
	
	}
	
	public void paintComponent(Graphics g) {
		
		//����
		if(first) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			
			first = false;
		}
		
		//��� �� ���
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		
		map.draw(g); //�� ���
		
		//�½��� ����
		for(int i = 0; i < monsters.length; i++) {
			if(monsters[i].isLiving) {
				monsters[i].draw(g);
			}
		}
		
		shop.draw(g); //Drawing the store
		
		//���� ������
		if(healthNum < 1) {
			g.setColor(new Color(240, 20,20 ,20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color (255, 255, 255));
			g.setFont(new Font ("Courier New", Font.BOLD, 70));
			//�� �κп��� ���� ������ �̹��� ���� ����
			g.drawString("Game Over", 150, 50);
		}
		
		//�̰�����, �����ܰ� �Ǵ� ���� ����
		if(flag_Win) {
			g.setColor(new Color (255,255,255));
			g.fillRect( 0, 0, getWidth(), getHeight());
			g.setColor(new Color (0, 0, 0));
			g.setFont(new Font ("Courier New", Font.BOLD, 14));
			if(level == maxLevel ) {
				g.drawString("You won the whole game!", 10, 20);
			} else {
			g.drawString("next level", 10, 20);
			}
		}
		
	}
	
	//���� ���� �ֱ�(�ð�)
	public int spawnTime = 2400, spawnFrame = 0;
	public void mobSpawner() {
		if(spawnFrame >= spawnTime) {
			for(int i = 0; i < monsters.length; i++) {
				if(!monsters[i].isLiving) {
					monsters[i].spawnMob(IDnum.mobYellow);
					break;
				}
			}
			
			spawnFrame = 0;
		}else {
			spawnFrame += 1;
		}
	}
	

	public void run() {
		while(true) {
			if(!flag_Win && healthNum > 0 && !first) {
				map.physic();
				mobSpawner();
				for(int i = 0; i < monsters.length; i++) {
					if(monsters[i].isLiving) {
						monsters[i].physic();
					}
				}
			}
			else {
				if(flag_Win) {
					if(clearFrame >= clearTime) {
						if(level == maxLevel) {
							System.exit(0);
						} else {
							//level ����
							level += 1;
							define();
							Game.coinNum = 10;
							spawnTime -= 400;
							for(int i = 0; i < monsters.length; i++) {
								monsters[i].levelUp();
							}
							flag_Win = false;
						}	
						clearFrame = 0;
					} 
					else {
						clearFrame += 1;
					}
				}
			}
			
			repaint();
			
			try{
				Thread.sleep(1);
			} catch(Exception e) { }
		}
	}
}
