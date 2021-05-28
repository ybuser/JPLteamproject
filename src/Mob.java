import java.awt.*;

public class Mob extends Rectangle{
	public int xC, yC;
	public int health;
	
	public int healthSpace = 3;
	public int healthHeight = 6;
	
	public int mobSize = 52;
	public int mobWalk = 0;
	
	public int upward= 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public int mobID = IDnum.mobAir;
	
	public boolean isLiving = false;
	
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasRight = false;
	public boolean hasLeft = false;
	
	public Mob() {
		
	}
	
	public void spawnMob(int mobID) {
		for(int y=0; y < Game.map.tileHeigntNum; y++) {
			if(Game.map.tile[y][0].groundID == IDnum.groundRoad) {
				setBounds(Game.map.tile[y][0].x, Game.map.tile[y][0].y, mobSize, mobSize);
				xC = 0; 
				yC = y;
			}
		}
		
		this.mobID = mobID;
		this.health = mobSize;
		
		isLiving = true;
		
	}
	
	public void deleteMob() {
		isLiving = false;
		direction = right;
		mobWalk = 0;
		
		Game.map.tile[0][0].getMoney(mobID);
		
	}
	
	public void looseHealth() {
		Game.healthNum -=1;
	}
	
	public void levelUp() {
		walkSpeed -= 7;
	}
	
	
	public int walkFrame = 0, walkSpeed = 20;
	public void physic() {
		if(walkFrame >= walkSpeed) {
			if(direction == right) {
				x += 1;
			}else if(direction == upward) {
				y -= 1;
			}else if(direction == downward) {
				y += 1;
			}else if(direction == left) {
				x -= 1;
			}
			
			mobWalk += 1;
			
			if(mobWalk == Game.map.tileSize) {
				if(direction == right) {
					xC += 1;
					hasRight = true;
				}else if(direction == upward) {
					yC -= 1;
					hasUpward = true;
				}else if(direction == downward) {
					yC += 1;
					hasDownward = true;
				}else if(direction == left) {
					xC -= 1;
					hasLeft = true;
				}
				
				if(!hasUpward) {
					try {
						if(Game.map.tile[yC+1][xC].groundID == IDnum.groundRoad) {
							direction = downward;
						}
					}catch (Exception e) {}
				}
				
				if(!hasDownward) {
					try {
						if(Game.map.tile[yC-1][xC].groundID == IDnum.groundRoad) {
							direction = upward;
						}
					}catch (Exception e) {}
				}
				
				if(!hasLeft) {
					try {
						if(Game.map.tile[yC][xC+1].groundID == IDnum.groundRoad) {
							direction = right;
						}
					}catch (Exception e) {}
				}
				
				if(!hasRight) {
					try {
						if(Game.map.tile[yC][xC-1].groundID == IDnum.groundRoad) {
							direction = left;
						}
					}catch (Exception e) {}
				}
				
				if(Game.map.tile[yC][xC].airID == IDnum.airMapLast) {
					deleteMob();
					looseHealth();
				}
				
				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				
				mobWalk = 0;
			}
			
			walkFrame = 0;
		}else {
			walkFrame += 1;
		}
		
		
		
	}
	
	public void loseMobHealth(int h) {
		health -= h;
			
		checkDeath();
	}
	
	public void checkDeath() {
		if(health == 0) {
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