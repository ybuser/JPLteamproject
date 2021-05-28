import java.awt.*;


//Making Store
public class Shop {
	
	public static int shopNum = 3; //������ ����
	public static int buttonSize = 52;
	public static int buttonSpace = 2; //������ ĭ�� ������ �Ÿ�
	public static int awayFromRoom = 29;
	
	//����, ���� �������
	public static int statusSize = 20;
	public static int statusSpace = 3;
	public static int statusText = 15;
	
	public static int shopIn = 4;
	
	//���콺 ��� ������
	public static int heldTowerID = -1;
	public static int heldButtonIndex = -1;
	public boolean isHold = false;
	
	//������ ��ư ����
	public static int[] shopID = {IDnum.airTowerMyeongRyun, IDnum.airTowerYulJeon, IDnum.airItemCancel};
	public static int[] shopPrice = { 3, 4, 0 };

	
	public Rectangle[] shop = new Rectangle[shopNum];
	public Rectangle rectHealth;
	public Rectangle rectCoins;
	

	
	public Shop() {
		for(int i = 0; i < shop.length; i++) {
			shop[i] = new Rectangle(Game.map.tileSize*5 + (buttonSize+buttonSpace)*i, Game.map.tileSize/2*17, buttonSize, buttonSize);
		}
		
		rectHealth = new Rectangle(Game.map.tile[0][0].x - 1, shop[0].y + buttonSize/2, statusSize, statusSize);
		rectCoins = new Rectangle(Game.map.tile[0][0].x + buttonSize, shop[0].y + buttonSize/2, statusSize, statusSize);
	}
	
	public void click(int mouseButton) {
		//Ÿ�� Ŭ��
		if(mouseButton == 1) {
			 for(int i= 0; i<shop.length; i++) {
				 if(shop[i].contains(Game.mouse)) {
					 if(shopID[i] != IDnum.airAir) {
						 if(shopID[i] != IDnum.airItemCancel) {
							 heldTowerID = shopID[i];
							 heldButtonIndex = i;
							 isHold = true;
						 } 
						 else 
							 isHold = false;
						 
					 }
				 }
			 }
			 
			 //Ÿ�� ��ġ
			 if (isHold && Game.coinNum >= shopPrice[heldButtonIndex]){
				for (int y = 0; y < Game.map.tileHeigntNum; y++){
					for (int x = 0; x < Game.map.tileWidthNum; x++){
						if (Game.map.tile[y][x].contains(Game.mouse) && Game.map.tile[y][x].groundID != IDnum.groundRoad && Game.map.tile[y][x].airID == IDnum.airAir){
							Game.map.tile[y][x].airID = heldTowerID;
							Game.coinNum -= shopPrice[heldButtonIndex];
						}
					}
				}
			 }
			 
		}
	}
	
	public void draw(Graphics g) {
		
		for(int i = 0; i < shop.length; i++) {
			
			//����� ������ �̹��� ���
			g.drawImage(Game.restImageFile[0], shop[i].x, shop[i].y, shop[i].width, shop[i].height, null);
			g.drawImage(Game.airImageFile[shopID[i]], shop[i].x + shopIn, shop[i].y + shopIn, shop[i].width - 2*shopIn, shop[i].height - 2*shopIn, null);
			
			//���� ���
			if (shopPrice[i] > 0)
			{
				g.setColor(new Color(0, 0, 0));
				g.setFont(new Font("Courier New", Font.BOLD, 14));
				g.drawString(shopPrice[i] + "won", shop[i].x + shopIn*2, shop[i].y - shopIn);
			}
		}
		
		//����, ���� ȭ�鿡 ����
		g.drawImage(Game.restImageFile[1], rectHealth.x, rectHealth.y, rectHealth.width, rectHealth.height, null);
		g.drawImage(Game.restImageFile[2] ,rectCoins.x, rectCoins.y, rectCoins.width, rectCoins.height, null);
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		g.setColor(new Color(0, 0, 0));
		g.drawString("" + Game.healthNum, rectHealth.x + rectHealth.width + statusSpace, rectHealth.y + statusText);
		g.drawString("" + Game.coinNum, rectCoins.x + rectCoins.width + statusSpace, rectCoins.y + statusText);

		//�������� Ŭ�������� �̹��� ��������� ����
		if(isHold) {
			g.drawImage(Game.airImageFile[heldTowerID], Game.mouse.x - ((shop[0].width - shopIn*2)/2) +shopIn, Game.mouse.y - ((shop[0].width - shopIn*2)/2) +shopIn, shop[0].width - 2*shopIn, shop[0].height - 2*shopIn, null);
		}
	}
}


//import java.awt.*;
//
//
////Making Store
//public class Store {
//	
//	public static int shopNum = 8; //������ ����
//	public static int buttonSize = 52;
//	public static int buttonSpace = 2; //������ ĭ�� ������ �Ÿ�
//	public static int awayFromRoom = 29;
//	
//	//����, ���� �������
//	public static int iconSize = 20;
//	public static int iconSpace = 3;
//	public static int iconTextY = 15;
//	
//	public static int itemIn = 4;
//	
//	//���콺 ��� ������
//	public static int heldTowerID = -1;
//	public static int heldButtonIndex = -1;
//	public boolean holdsItem = false;
//	
//	//������ ��ư ����
//	public static int[] buttonID = {Value.airTowerMyeongRyun, Value.airTowerYulJeon, -1, -1, -1, -1, -1, Value.airItemCancel};
//	public static int[] buttonPrice = { 3, 4, 0, 0, 0, 0, 0, 0 };
//
//	
//	public Rectangle[] button = new Rectangle[shopNum];
//	public Rectangle rectHealth;
//	public Rectangle rectCoins;
//	
//
//	
//	public Store() {
//		define();
//	}
//	
//	public void click(int mouseButton) {
//		//Ÿ�� Ŭ��
//		if(mouseButton == 1) {
//			 for(int i= 0; i<button.length; i++) {
//				 if(button[i].contains(Screen.mouse)) {
//					 if(buttonID[i] != Value.airAir) {
//						 if(buttonID[i] == Value.airItemCancel) {
//							 holdsItem = false;
//						 }else {
//							 heldTowerID = buttonID[i];
//							 heldButtonIndex = i;
//							 holdsItem = true;
//						 }
//					 }
//				 }
//			 }
//			 
//			 //Ÿ�� ��ġ
//			 if (holdsItem){
//				if (Screen.coinNum >= buttonPrice[heldButtonIndex]){
//					for (int y = 0; y < Screen.map.block.length; y++){
//						for (int x = 0; x < Screen.map.block[0].length; x++){
//							if (Screen.map.block[y][x].contains(Screen.mouse)){
//								if (Screen.map.block[y][x].groundID != Value.groundRoad && Screen.map.block[y][x].airID == Value.airAir){
//										Screen.map.block[y][x].airID = heldTowerID;
//										Screen.coinNum -= buttonPrice[heldButtonIndex];
//								}
//							}
//						}
//					}
//				}
//			 }
//			 
//		}
//	}
//	
//	//���� ��ư ����, ü��/���� ���� ����
//	public void define() {
//		for(int i = 0; i < button.length; i++) {
//			button[i] = new Rectangle((Screen.myWidth/2)- ((shopNum*(buttonSize+buttonSpace)/2)) + (buttonSize+buttonSpace)*i, (Screen.map.block[Screen.map.blockHeightNum-1][0].y)+Screen.map.blockSize+awayFromRoom, buttonSize, buttonSize);
//		}
//		
//		rectHealth = new Rectangle(Screen.map.block[0][0].x - 1, button[0].y, iconSize, iconSize);
//		rectCoins = new Rectangle(Screen.map.block[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize);
//	}
//	
//	
//	public void draw(Graphics g) {
//		
//		for(int i = 0; i < button.length; i++) {
//			//���콺�� ��ư ���� �ö��� �� ���� ���
//			if(button[i].contains(Screen.mouse)) {
//				g.setColor(new Color(255, 255, 255, 150));
//				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
//			}
//			
//			//����� ������ �̹��� ���
//			g.drawImage(Screen.restImageFile[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
//			if(buttonID[i] != Value.airAir) 
//				g.drawImage(Screen.airImageFile[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - 2*itemIn, button[i].height - 2*itemIn, null);
//			
//			//���� ���
//			if (buttonPrice[i] > 0)
//			{
//				g.setColor(new Color(255, 255, 255));
//				g.setFont(new Font("Courier New", Font.BOLD, 14));
//				g.drawString("$" + buttonPrice[i], button[i].x + itemIn, button[i].y + itemIn + 10);
//			}
//		}
//		
//		//����, ���� ȭ�鿡 ����
//		g.drawImage(Screen.restImageFile[1], rectHealth.x, rectHealth.y, rectHealth.width, rectHealth.height, null);
//		g.drawImage(Screen.restImageFile[2] ,rectCoins.x, rectCoins.y, rectCoins.width, rectCoins.height, null);
//		g.setFont(new Font("Courier New", Font.BOLD, 14));
//		g.setColor(new Color(0, 0, 0));
//		g.drawString("" + Screen.healthNum, rectHealth.x + rectHealth.width + iconSpace, rectHealth.y + iconTextY);
//		g.drawString("" + Screen.coinNum, rectCoins.x + rectCoins.width + iconSpace, rectCoins.y + iconTextY);
//
//		//�������� Ŭ�������� �̹��� ��������� ����
//		if(holdsItem) {
//			g.drawImage(Screen.airImageFile[heldTowerID], Screen.mouse.x - ((button[0].width - itemIn*2)/2) +itemIn, Screen.mouse.y - ((button[0].width - itemIn*2)/2) +itemIn, button[0].width - 2*itemIn, button[0].height - 2*itemIn, null);
//		}
//	}
//}
