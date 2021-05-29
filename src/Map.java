import java.awt.*;


public class Map {
	//���� ���ó�� Ȱ��(��ǥ ����)
	public int tileWidthNum = 12; //Ÿ�� ���� ����
	public int tileHeigntNum = 8; //Ÿ�� ���� ����
	public int tileSize = 52; //Ÿ�� ���� ���� ����(���簢��)
	
	public Tile[][] tile;
	
	
	public Map() {
		define();
	}
	
	
	public void define() {
		//Ÿ�� �迭 ����
		tile = new Tile[tileHeigntNum][tileWidthNum];
		
		for(int y=0;y<tileHeigntNum;y++) {
			for(int x=0;x<tile[0].length;x++) {				
				//Ÿ�� ����, Ÿ�ϵ��� ��� �г� ����� ����
				tile[y][x] = new Tile(28 + x*tileSize, y*tileSize, tileSize, tileSize, IDnum.groundGrass, IDnum.airAir);
			}
		}
	}
	
	//tile physic ����
	public void physic() {
		for(int y=0;y<tileHeigntNum;y++)	{
			for(int x=0;x<tileWidthNum;x++) {
				tile[y][x].physic();
			}
		}
	}
	
	//tile �̹��� ����
	public void draw(Graphics g) {
		//��� draw
		for(int y=0;y<tileHeigntNum;y++) {
			for(int x=0;x<tileWidthNum;x++) {
				tile[y][x].draw(g);
			}
		}
		
		//���� ����
		for(int y=0;y<tileHeigntNum; y++) {
			for(int x=0; x<tileWidthNum; x++) {
				tile[y][x].attack(g);
			}
		}
	}
	
	
	class Tile extends Rectangle{
		//Ÿ�� ���� ���� ����(�簢��)
		public Rectangle towerAttackRange;
		public int towerAttackRangeSize = 150;
		
		//���� ����
		public int groundID;
		public int airID;
		
		//Ÿ���� ���� ���� �ӵ�
		public int loseTime = 100, loseFrame = 0;
		
		//Ÿ�� ���� ����
		public int shotMob = -1;
		public boolean shoting = false;
		
		//��� ����, ���� ���� ����
		public Tile(int x, int y, int width, int height, int groundID, int airID) {
			setBounds(x, y, width, height);
			towerAttackRange = new Rectangle(x - (towerAttackRangeSize/2), y - (towerAttackRangeSize/2), width + (towerAttackRangeSize), height + (towerAttackRangeSize));
			this.groundID = groundID;
			this.airID = airID;
		}
		
		//�̹��� ����
		public void draw(Graphics g) {
			//ground �̹��� �׸���
			g.drawImage(Game.groundImageFile[groundID], x, y, width, height, null);
			
			//air�� ���𰡰� ������ �� �̹��� ����
			if(airID != IDnum.airAir) {
				g.drawImage(Game.airImageFile[airID], x, y, width, height, null);
			}
			
		}
		
		
		//���⼭ ����� ������ ������ �������� �ҵ� 
		//���� ����
		public void physic() {
			//���� ���� ����
			if(shotMob != -1 && towerAttackRange.intersects(Game.mobs[shotMob])) {
				shoting = true;
			} else {
				shoting = false;
			}
			
			//���͵��� ���� �ȿ� �������� Ȯ��
			if(!shoting) {
				shoting = false;
					if(airID != IDnum.airAir && (airID == IDnum.airTowerMyeongRyun || airID == IDnum.airTowerYulJeon)) {
						for(int i=0;i<Game.mobs.length;i++) {
							if(Game.mobs[i].isLiving) {
								if(towerAttackRange.intersects(Game.mobs[i])) {
									shoting = true;
									shotMob = i;
								}
							}
						}
					}
			}
			
			//���� ����
			if(shoting) {					
				if(loseFrame >= loseTime) {
					Game.mobs[shotMob].loseMobHealth(1);
					
					
					loseFrame = 0;
				} else {
					loseFrame += 1;
				}
				
				//���͸� �������� ��
				if(Game.mobs[shotMob].isDead()) {
					shoting = false;
					shotMob = -1;
					
					Game.deadMob += 1;
					
					Game.clear();
				}
			}
		}
		
		
		//���� óġ�� �� ȹ��
		public void getMoney(int mobID) {
			Game.coinNum += IDnum.mobReward[mobID];
		}
		
		//Ÿ���� ���� ����
		public void attack(Graphics g) {
			//���� ǥ��(���� ���·� ǥ��)
			if(shoting) {  					
				g.setColor(new Color(255,255,0));
				g.drawLine(x + (width/2), y + (height/2), Game.mobs[shotMob].x +  (Game.mobs[0].mobSize/2), Game.mobs[shotMob].y +  (Game.mobs[0].mobSize/2));
			}	
		}
	
	}
	
	
}
