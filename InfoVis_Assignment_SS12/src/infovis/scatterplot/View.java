package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0); 
	     private Rectangle2D rect1, rect2, rect3, rect4, rect5;

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.clearRect(0, 0, getWidth(), getHeight());
			
			drawGrid(g2D);
			

	        for (String l : model.getLabels()) {
				Debug.print(l);
				Debug.print(",  ");
				Debug.println("");
			}
			for (Range range : model.getRanges()) {
				Debug.print(range.toString());
				Debug.print(",  ");
				Debug.println("");
			}
			for (Data d : model.getList()) {
				Debug.print(d.toString());
				Debug.println("");
			}
	        
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
		
		private void drawGrid(Graphics2D g2D) {
			int x = 100;
			int y = 100;
			int w = 75;
			int h = 75;
			for (int i = 1; i < 50; ++i) {
				Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);
				g2D.draw(rect);
				x += w;
				if(i%7 == 0) {
					y += h;
					x = 100;
				}
			}
		}
}
