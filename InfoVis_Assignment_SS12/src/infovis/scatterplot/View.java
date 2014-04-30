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
	     int x;
	     int y;
	     double w;
	     double h;
	     

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.clearRect(0, 0, getWidth(), getHeight());
			
			drawGrid(g2D);

			x = 200;
			y = 100;
			w = 75;
			h = 75;
			for (Data d : model.getList()) {
				for (int i = 0; i < 7; ++i) {
					Range range = model.getRanges().get(i);
					double newValue = getMappedValue(d.getValues()[i], range.getMax(), range.getMin(), x, x+w);
					Rectangle2D rect = new Rectangle2D.Double(newValue-5,y+25,10,10);
					g2D.draw(rect);
					x += w;
					if(i%7 == 0) {
						y += h;
						x = 200;
					}
					System.out.println("value "+ newValue);
				}
			}

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
			x = 200;
			y = 100;
			w = 75;
			h = 75;
			for (int i = 1; i < 50; ++i) {
				Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);
				g2D.draw(rect);
				x += w;
				if(i%7 == 0) {
					y += h;
					x = 200;
				}
			}
		}
		
		private double getMappedValue(double oldValue, double oldMax, double oldMin, double newMax, double newMin) {
			double oldRange = oldMax - oldMin;
			double newRange = newMax - newMin;
			double newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;
			return newValue;
		}
}
