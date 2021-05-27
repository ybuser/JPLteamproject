import java.awt.*;


public class Room {
	public int worldWidth = 12; //블록 가로 개수
	public int worldHeight = 8; //블록 새로 개수
	public int blockSize = 52; //블록 가로 세로 길이(정사각형)
	
	public Block[][] block;
	
	public Room() {
		define();
		
	}
	
	public void define() {
		block = new Block[worldHeight][worldWidth];
		
		//좌표 조심!!
		for(int y=0;y<block.length;y++) {
			for(int x=0;x<block[0].length;x++) {
				
				//블록들을 가운데 패널 가운데로 정렬!!
				block[y][x] = new Block((Screen.myWidth/2) - ((worldWidth*blockSize)/2) + x*blockSize, y*blockSize, blockSize, blockSize, Value.groundGrass, Value.airAir);
			}
		}
	}
	
	public void physic() {
		for(int y=0;y<block.length;y++)	{
			for(int x=0;x<block[0].length;x++) {
				block[y][x].physic();
			}
		}
	}
	
	public void draw(Graphics g) {
		//go over all blocks and draw
		for(int y=0;y<block.length;y++) {
			for(int x=0;x<block[0].length;x++) {
				block[y][x].draw(g);
			}
		}
		
		for(int y=0;y<block.length; y++) {
			for(int x=0; x<block[0].length; x++) {
				block[y][x].fight(g);
			}
		}
	}
}
