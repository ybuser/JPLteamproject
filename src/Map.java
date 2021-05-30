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
				tile[y][x] = new Tile(28 + x*tileSize, y*tileSize, tileSize, tileSize, IDnum.groundGrass, IDnum.charAir);
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
		public int charID;
		
		//Ÿ���� ���� ���� �ӵ�
		public int attackTime = 100, attackFrame = 0;
		
		//Ÿ�� ���� ����
		public boolean attacking = false;
		public int attackMob = -1;
		
		//��� ����, ���� ���� ����
		public Tile(int x, int y, int width, int height, int groundID, int airID) {
			setBounds(x, y, width, height);
			towerAttackRange = new Rectangle(x - (towerAttackRangeSize/2), y - (towerAttackRangeSize/2), width + (towerAttackRangeSize), height + (towerAttackRangeSize));
			this.groundID = groundID;
			this.charID = airID;
		}
		
		//�̹��� ����
		public void draw(Graphics g) {
			//ground �̹��� �׸���
			g.drawImage(Game.groundImageFile[groundID], x, y, width, height, null);
			
			//air�� ���𰡰� ������ �� �̹��� ����
			if(charID != IDnum.charAir) {
				g.drawImage(Game.charImageFile[charID], x, y, width, height, null);
			}
			
		}
		
		
		//���⼭ ����� ������ ������ �������� �ҵ� 
		//���� ����
		public void physic() {
			//���� ���� ����
			if(attackMob != -1 && towerAttackRange.intersects(Game.monsters[attackMob])) {
				attacking = true;
			} else {
				attacking = false;
			}
			
			//���͵��� ���� �ȿ� �������� Ȯ��
			if(!attacking) {
					if(charID != IDnum.charAir && (charID == IDnum.charTowerMyeongRyun || charID == IDnum.charTowerYulJeon)) {
						for(int i=0;i<Game.monsters.length;i++) {
							if(Game.monsters[i].isLiving) {
								if(towerAttackRange.intersects(Game.monsters[i])) {
									attackMob = i;
									attacking = true;
								}
							}
						}
					}
			}
			
			//���� ����
			if(attacking) {			
				//���͸� ����
				if(attackFrame >= attackTime) {
					if(charID == IDnum.charTowerMyeongRyun)
						Game.monsters[attackMob].loseMobHealth(1);
					else
						Game.monsters[attackMob].loseMobHealth(2);
					
					
					attackFrame = 0;
				} else {
					attackFrame += 1;
				}
				
				//���͸� �������� ��
				if(Game.monsters[attackMob].isDead()) {
					attacking = false;
					attackMob = -1;
					
					
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
			if(attacking) {  					
				g.setColor(new Color(100,255,100));
				g.drawLine(x + (width/2), y + (height/2), Game.monsters[attackMob].x +  (Game.monsters[0].monsterSize/2), Game.monsters[attackMob].y +  (Game.monsters[0].monsterSize/2));
			}	
		}
	
	}
	
	
}
