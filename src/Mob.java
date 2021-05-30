import java.awt.*;


public class Mob extends Rectangle{
	public int index_X, index_Y;
	public int health;
	
	public int mobID = IDnum.mobAir;
	
	public int healthSpace = 3;
	public int healthHeight = 6;
	
	public int monsterSize = 52;
	public int directionFlag = 0;
	
	public int upward= 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	
	public boolean isLiving = false;
	
	
	public Mob() {
		setDirection();
	}
	
	public void spawnMob(int mobID) {
		for(int y=0; y < Game.map.tileHeigntNum; y++) {
			if(Game.map.tile[y][0].groundID == IDnum.groundRoad) {
				setBounds(Game.map.tile[y][0].x, Game.map.tile[y][0].y, monsterSize, monsterSize);
				index_X = 0; 
				index_Y = y;
			}
		}
		
		this.mobID = mobID;
		this.health = monsterSize;
		
		isLiving = true;
		
	}
	
	public void deleteMob() {
		isLiving = false;
		direction = right;
		directionFlag = 0;
		
		Game.deadMob += 1;
		
		Game.map.tile[0][0].getMoney(mobID);
		
	}
	
	public void looseHealth() {
		Game.healthNum -=1;
	}
	
	public void levelUp() {
		walkSpeed -= 14;
	}
	
	
	public int[][] tempRoadMap;
	
	public void setDirection() {
		tempRoadMap = new int[Game.map.tileHeigntNum][Game.map.tileWidthNum];
		for(int y = 0; y < Game.map.tileHeigntNum; y++) {
			for(int x = 0; x < Game.map.tileWidthNum; x++) {
				tempRoadMap[y][x] = Game.map.tile[y][x].groundID;
			}
		}	
	}
	
	//방향을 따로 저장해서 해보자!!
	public int walkFrame = 0, walkSpeed = 30;
	
	public void physic() {
		if (walkFrame >= walkSpeed) {
			if(direction == right) {
				x += 1;
			}
			else if(direction == upward) {
				y -= 1;
			}
			else if(direction == downward) {
				y += 1;	
			}
			else if(direction == left) {
				x -= 1;			
			}	
		
		
			directionFlag += 1;
		
			if(directionFlag >= Game.map.tileSize) {
				if(direction == right) {
					tempRoadMap[index_Y][index_X] = 0;
					index_X += 1;
				}
				else if(direction == upward) {
					tempRoadMap[index_Y][index_X] = 0;
					index_Y -= 1;
				}
				else if(direction == downward) {
					tempRoadMap[index_Y][index_X] = 0;
					index_Y += 1;
				}
				else if(direction == left) {
					tempRoadMap[index_Y][index_X] = 0;
					index_X -= 1;
				}
				
				try {
					while(true){
						if(tempRoadMap[index_Y+1][index_X] != 0) {
							direction = downward;
							break;
						}
						else if(tempRoadMap[index_Y][index_X-1] != 0) {
							direction = left;
							break;
						}
						else if(tempRoadMap[index_Y-1][index_X] != 0) {
							direction = upward;
							break;
						}
						else if(tempRoadMap[index_Y][index_X+1] != 0) {
							direction = right;
							break;
						}
	
					}
				}catch(Exception e) {}

				
				
				if(Game.map.tile[index_Y][index_X].charID == IDnum.charMapLast) {
					deleteMob();
					Game.coinNum -= 2;
					looseHealth();
					setDirection();
				}
				
				directionFlag = 0;
			}
			walkFrame = 0;
		}
		else {
			walkFrame += 1;
		}
		
		
	}
	
	
	public void loseMobHealth(int h) {
		health -= h;
			
		checkDeath();
	}
	
	public void checkDeath() {
		if(health <= 0) {
			deleteMob();
		}
	}
	
	public boolean isDead() {
		return !isLiving;
	}
	
	public void draw (Graphics g) {
		g.drawImage(Game.mobImageFile[mobID], x, y, width, height, null);
		

		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);
		
		g.setColor(new Color(50, 180, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);
		
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - (healthSpace + healthHeight), health - 1, healthHeight - 1);
		
	}

}