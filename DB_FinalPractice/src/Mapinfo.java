import java.io.*;
import java.util.*;

public class Mapinfo {
	public void loadMap (File map) {
		try {
			Scanner sc = new Scanner(map);
			
			
			//�� ����..
			while(sc.hasNext()) {
				Game.goalNum = sc.nextInt();
				
				//groundID �б�
				for(int y = 0; y < Game.map.tileHeigntNum; y++) {
					for(int x = 0; x < Game.map.tileWidthNum; x++) {
						Game.map.tile[y][x].groundID = sc.nextInt();
					}
				}
				
				//airID �б�
				for(int y = 0; y < Game.map.tileHeigntNum; y++) {
					for(int x = 0; x < Game.map.tileWidthNum; x++) {
						Game.map.tile[y][x].charID = sc.nextInt();
					}
				}
			}
			
			sc.close();
		} catch (Exception e) {}
	} 
}
