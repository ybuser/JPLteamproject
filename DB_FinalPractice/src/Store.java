import java.awt.*;

public class Store {
	public static int shopWidth = 8;
	public static int buttonSize = 32;
	
	public Rectangle[] button = new Rectangle[shopWidth];
	
	public Store() {
		define();
	}
	
	public void define() {
		for(int i = 0; i < button.length; i++) {
			button[i] = new Rectangle((Screen.myWidth/2)- ((shopWidth*buttonSize)/2), 10, buttonSize, buttonSize);
		}
	}
	
	public void draw(Graphics g) { 
		//1:25:17
		//g.fillRect(button[i].x, buttonSize, shopWidth, buttonSize);
	}
}