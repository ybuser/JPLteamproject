import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
	public static String title = "SKKU Tower Defense";
	public static Dimension size = new Dimension(700,550);
	
	public Frame() {
		setTitle(title);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(null); //화면 가운데 고정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프로세스 종료
		
		init();
	}
	
	public void init() {
		setLayout(new GridLayout(1,1,0,0));
		
		Screen screen = new Screen(this);
		add(screen);
		
		setVisible(true);
	}
	
	public static void main(String args[]) {
		Frame frame = new Frame();
		
	}
}
