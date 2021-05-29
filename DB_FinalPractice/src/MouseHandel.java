import java.awt.event.*;
import java.awt.*;


//마우스 조정
public class MouseHandel implements MouseMotionListener, MouseListener{


	public void mouseClicked(MouseEvent e) {}


	public void mousePressed(MouseEvent e) {
		Game.shop.click(e.getButton());
	}


	public void mouseReleased(MouseEvent e) {}


	public void mouseEntered(MouseEvent e) {}

	
	public void mouseExited(MouseEvent e) {}


	public void mouseDragged(MouseEvent e) {} 


	public void mouseMoved(MouseEvent e) {
		 Game.mouse = new Point((e.getX()) - ((Frame.size.width - Game.myWidth)/2), e.getY() - ((Frame.size.height - (Game.myHeight)) - (Frame.size.width - Game.myWidth)/2));
	}

}
