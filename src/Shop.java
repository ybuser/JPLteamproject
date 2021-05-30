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
	public static int[] shopID = {IDnum.charTowerMyeongRyun, IDnum.charTowerYulJeon, IDnum.charItemCancel};
	public static int[] shopPrice = { 3, 4, 0 };

	
	public Rectangle[] shop = new Rectangle[shopNum];
	public Rectangle healthImage;
	public Rectangle rectCoins;
	

	
	public Shop() {
		for(int i = 0; i < shop.length; i++) {
			shop[i] = new Rectangle(Game.map.tileSize*5 + (buttonSize+buttonSpace)*i, Game.map.tileSize/2*17, buttonSize, buttonSize);
		}
		
		healthImage = new Rectangle(Game.map.tile[0][0].x - 1, shop[0].y + buttonSize/2, statusSize, statusSize);
		rectCoins = new Rectangle(Game.map.tile[0][0].x + buttonSize, shop[0].y + buttonSize/2, statusSize, statusSize);
	}
	
	public void click(int mouseButton) {
		//Ÿ�� Ŭ��
		if(mouseButton == 1) {
			 for(int i= 0; i<shop.length; i++) {
				 if(shop[i].contains(Game.mouse)) {
					 if(shopID[i] != IDnum.charAir) {
						 if(shopID[i] != IDnum.charItemCancel) {
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
						if (Game.map.tile[y][x].contains(Game.mouse) && Game.map.tile[y][x].groundID != IDnum.groundRoad && Game.map.tile[y][x].charID == IDnum.charAir){
							Game.map.tile[y][x].charID = heldTowerID;
							Game.coinNum -= shopPrice[heldButtonIndex];
						}
					}
				}
			 }
			 
		}
	}
	
	public void draw(Graphics g) {
		
		for(int i = 0; i < shop.length; i++) {
			
			//���� ������ �̹��� ���
			g.drawImage(Game.restImageFile[0], shop[i].x, shop[i].y, shop[i].width, shop[i].height, null);
			g.drawImage(Game.charImageFile[shopID[i]], shop[i].x + shopIn, shop[i].y + shopIn, shop[i].width - 2*shopIn, shop[i].height - 2*shopIn, null);
			
			//���� ���
			if (shopPrice[i] > 0)
			{
				g.setColor(new Color(0, 0, 0));
				g.setFont(new Font("Courier New", Font.BOLD, 14));
				g.drawString(shopPrice[i] + "won", shop[i].x + shopIn*2, shop[i].y - shopIn);
			}
		}
		
		//����, ���� ȭ�鿡 ����
		g.drawImage(Game.restImageFile[1], healthImage.x, healthImage.y, healthImage.width, healthImage.height, null);
		g.drawImage(Game.restImageFile[2] ,rectCoins.x, rectCoins.y, rectCoins.width, rectCoins.height, null);
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		g.drawString("" + Game.healthNum, healthImage.x + healthImage.width + statusSpace, healthImage.y + statusText);
		g.drawString("" + Game.coinNum, rectCoins.x + rectCoins.width + statusSpace, rectCoins.y + statusText);

		//�������� Ŭ�������� �̹��� ��������� ����
		if(isHold) {
			g.drawImage(Game.charImageFile[heldTowerID], Game.mouse.x - ((shop[0].width - shopIn*2)/2) +shopIn, Game.mouse.y - ((shop[0].width - shopIn*2)/2) +shopIn, shop[0].width - 2*shopIn, shop[0].height - 2*shopIn, null);
		}
	}
}
