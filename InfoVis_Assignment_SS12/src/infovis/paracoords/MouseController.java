package infovis.paracoords;

import infovis.scatterplot.Model;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	int moving_line_index = -1;
	boolean move_axis = false;
	boolean mark_stuff = false;
	private double startX = 0;
	private double startY = 0;
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
		int l = view.isNearAxis(startX, startY);
		if(l >= 0) {
			moving_line_index=l;
			move_axis = true;
		}
		else
			mark_stuff = true;
	}

	public void mouseReleased(MouseEvent e) {
		move_axis = false;
		moving_line_index = -1;
		mark_stuff = false; 

	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (mark_stuff == true)
			view.getMarkerRectangle().setRect(startX, startY, x-startX, y-startY);
		else if (move_axis == true) {
			
			Line2D current = view.getCurrentLine(moving_line_index);
			Line2D leftNeighbour = view.getLeftNeighbour(moving_line_index);
			Line2D rightNeighbour = view.getRightNeighbour(moving_line_index);
			
			if (leftNeighbour != null && current.getX1() <= leftNeighbour.getX1() && Math.abs(current.getX1() - leftNeighbour.getX1())> 5) {
				view.changePositions(moving_line_index, "left");
				
			}
			if (rightNeighbour != null && current.getX1() >= rightNeighbour.getX1()&& Math.abs(current.getX1() - rightNeighbour.getX1())> 5) {
				view.changePositions(moving_line_index, "right");
			}
			view.setOffsetAtIndex(x,moving_line_index);
		}
		view.repaint();

	}

	public void mouseMoved(MouseEvent e) {

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
