import java.awt.*;


public class Map {
	//맵을 블록처럼 활용(좌표 같이)
	public int tileWidthNum = 12; //타일 가로 개수
	public int tileHeigntNum = 8; //타일 새로 개수
	public int tileSize = 52; //타일 가로 세로 길이(정사각형)
	
	public Tile[][] tile;
	
	
	public Map() {
		define();
	}
	
	
	public void define() {
		//타일 배열 생성
		tile = new Tile[tileHeigntNum][tileWidthNum];
		
		for(int y=0;y<tileHeigntNum;y++) {
			for(int x=0;x<tile[0].length;x++) {				
				//타일 생성, 타일들을 가운데 패널 가운데로 정렬
				tile[y][x] = new Tile(28 + x*tileSize, y*tileSize, tileSize, tileSize, IDnum.groundGrass, IDnum.charAir);
			}
		}
	}
	
	//tile physic 실행
	public void physic() {
		for(int y=0;y<tileHeigntNum;y++)	{
			for(int x=0;x<tileWidthNum;x++) {
				tile[y][x].physic();
			}
		}
	}
	
	//tile 이미지 생성
	public void draw(Graphics g) {
		//블록 draw
		for(int y=0;y<tileHeigntNum;y++) {
			for(int x=0;x<tileWidthNum;x++) {
				tile[y][x].draw(g);
			}
		}
		
		//공격 실행
		for(int y=0;y<tileHeigntNum; y++) {
			for(int x=0; x<tileWidthNum; x++) {
				tile[y][x].attack(g);
			}
		}
	}
	
	
	class Tile extends Rectangle{
		//타워 공격 범위 설정(사각형)
		public Rectangle towerAttackRange;
		public int towerAttackRangeSize = 150;
		
		//사진 설정
		public int groundID;
		public int charID;
		
		//타워의 몬스터 공격 속도
		public int attackTime = 100, attackFrame = 0;
		
		//타워 공격 설정
		public boolean attacking = false;
		public int attackMob = -1;
		
		//블록 생성, 공격 범위 설정
		public Tile(int x, int y, int width, int height, int groundID, int airID) {
			setBounds(x, y, width, height);
			towerAttackRange = new Rectangle(x - (towerAttackRangeSize/2), y - (towerAttackRangeSize/2), width + (towerAttackRangeSize), height + (towerAttackRangeSize));
			this.groundID = groundID;
			this.charID = airID;
		}
		
		//이미지 생성
		public void draw(Graphics g) {
			//ground 이미지 그리기
			g.drawImage(Game.groundImageFile[groundID], x, y, width, height, null);
			
			//air에 무언가가 존재할 때 이미지 생성
			if(charID != IDnum.charAir) {
				g.drawImage(Game.charImageFile[charID], x, y, width, height, null);
			}
			
		}
		
		
		//여기서 명륜이 율전이 데미지 설정가능 할듯 
		//공격 설정
		public void physic() {
			//공격 몬스터 설정
			if(attackMob != -1 && towerAttackRange.intersects(Game.monsters[attackMob])) {
				attacking = true;
			} else {
				attacking = false;
			}
			
			//몬스터들이 범위 안에 들어오는지 확인
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
			
			//공격 과정
			if(attacking) {			
				//몬스터를 공격
				if(attackFrame >= attackTime) {
					if(charID == IDnum.charTowerMyeongRyun)
						Game.monsters[attackMob].loseMobHealth(1);
					else
						Game.monsters[attackMob].loseMobHealth(2);
					
					
					attackFrame = 0;
				} else {
					attackFrame += 1;
				}
				
				//몬스터를 제거했을 때
				if(Game.monsters[attackMob].isDead()) {
					attacking = false;
					attackMob = -1;
					
					
					Game.clear();
				}
			}
		}
		
		
		//몬스터 처치시 돈 획득
		public void getMoney(int mobID) {
			Game.coinNum += IDnum.mobReward[mobID];
		}
		
		//타워가 몬스터 공격
		public void attack(Graphics g) {
			//공격 표시(라인 형태로 표현)
			if(attacking) {  					
				g.setColor(new Color(100,255,100));
				g.drawLine(x + (width/2), y + (height/2), Game.monsters[attackMob].x +  (Game.monsters[0].monsterSize/2), Game.monsters[attackMob].y +  (Game.monsters[0].monsterSize/2));
			}	
		}
	
	}
	
	
}
