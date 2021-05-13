import java.awt.*;
import javax.swing.*;

public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread(this);
	
	public Screen() {
		//setBackground(Color.PINK);
		thread.start();
	}
	
	public void paintComponent(Graphics g) {
		
	}
	
	public void run() {
		while(true) {
			System.out.println("hi");
		}
	}
}
