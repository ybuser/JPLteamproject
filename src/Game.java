import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

public class Game extends JPanel implements Runnable{
	public Thread thread = new Thread(this);
	
	//�̹�����
	public static Image[] groundImageFile = new Image[10];
	public static Image[] airImageFile = new Image[10];
	public static Image[] restImageFile = new Image[10];
	public static Image[] mobImageFile = new Image[10];
	
	public static int myWidth, myHeight;
	public static int coinNum = 10, healthNum = 100;
	public static int killed = 0, killsToWin = 0, level = 1, maxLevel = 3;
	public static int winTime = 4000, winFrame = 0;
	
	public static boolean isFirst = true;
	public static boolean isDebug = false;
	public static boolean isWin = false;
	
	public static Point mouse = new Point(0, 0);
	
	public static Map map;
	
	public static Mapinfo mapInfo;
	
	public static Shop shop;
	
	public static Mob[] mobs = new Mob[100];
	
	//���� ����
	public Game(Frame frame) {
		frame.addMouseListener(new MouseHandel());
		frame.addMouseMotionListener(new MouseHandel());
		
		thread.start();
	}
	
	//���� Ŭ���� ����
	public static void hasWon() {
		if(killed == killsToWin) {
			isWin = true;
			killed = 0;
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
			groundImageFile[i] = new ImageIcon("res/tileset_ground.png").getImage();
			
			//�̹��� �߶󳻱�
			groundImageFile[i] = createImage(new FilteredImageSource(groundImageFile[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		//air �̹���
		for(int i = 0; i<airImageFile.length; i++) {
			airImageFile[i] = new ImageIcon("res/airTestFile.png").getImage();
			airImageFile[i] = createImage(new FilteredImageSource(airImageFile[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
		//������ �̹�����
		restImageFile[0] = new ImageIcon("res/cell.png").getImage();
		restImageFile[1] = new ImageIcon("res/heart.png").getImage();
		restImageFile[2] = new ImageIcon("res/coin.png").getImage();
		
		mobImageFile[0] = new ImageIcon("res/mob.png").getImage(); //�� �̹���
		
		//��(��) �����
		mapInfo.loadMap(new File("save/map" + level + ".txt"));
		
		//���� ����
		for(int i = 0; i < mobs.length; i++) {
			mobs[i] = new Mob();
		}
	
	}
	
	public void paintComponent(Graphics g) {
		
		//����
		if(isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			
			isFirst = false;
		}
		
		//��� �� ���
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		
		map.draw(g); //�� ���
		
		//�½��� ����
		for(int i = 0; i < mobs.length; i++) {
			if(mobs[i].isLiving) {
				mobs[i].draw(g);
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
		if(isWin) {
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
			for(int i = 0; i < mobs.length; i++) {
				if(!mobs[i].isLiving) {
					mobs[i].spawnMob(IDnum.mobYellow);
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
			if(!isFirst && healthNum > 0 && !isWin) {
				map.physic();
				mobSpawner();
				for(int i = 0; i < mobs.length; i++) {
					if(mobs[i].isLiving) {
						mobs[i].physic();
					}
				}
			}else {
				if(isWin) {
					if(winFrame >= winTime) {
						if(level == maxLevel) {
							System.exit(0);
						} else {
							//level ����
							level += 1;
							define();
							spawnTime -= 400;
							for(int i = 0; i < mobs.length; i++) {
								mobs[i].levelUp();
							}
							isWin = false;
						}	
							winFrame = 0;
					} else {
						winFrame += 1;
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
