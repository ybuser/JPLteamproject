import java.io.*;
import java.util.*;

public class Mapinfo {
	public void loadMap (File map) {
		try {
			Scanner sc = new Scanner(map);
			
			
			//길 저장..
			while(sc.hasNext()) {
				Game.killsToWin = sc.nextInt();
				
				//groundID 읽기
				for(int y = 0; y < Game.map.tileHeigntNum; y++) {
					for(int x = 0; x < Game.map.tileWidthNum; x++) {
						Game.map.tile[y][x].groundID = sc.nextInt();
					}
				}
				
				//airID 읽기
				for(int y = 0; y < Game.map.tileHeigntNum; y++) {
					for(int x = 0; x < Game.map.tileWidthNum; x++) {
						Game.map.tile[y][x].airID = sc.nextInt();
					}
				}
			}
			
			sc.close();
		} catch (Exception e) {}
	} 
}
