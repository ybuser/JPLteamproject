import java.awt.*;


public class Room {
	public int worldWidth = 12; //��� ���� ����
	public int worldHeight = 8; //��� ���� ����
	public int blockSize = 52; //��� ���� ���� ����(���簢��)
	
	public Block[][] block;
	
	public Room() {
		define();
		
	}
	
	public void define() {
		block = new Block[worldHeight][worldWidth];
		
		//��ǥ ����!!
		for(int y=0;y<block.length;y++) {
			for(int x=0;x<block[0].length;x++) {
				
				//��ϵ��� ��� �г� ����� ����!!
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
