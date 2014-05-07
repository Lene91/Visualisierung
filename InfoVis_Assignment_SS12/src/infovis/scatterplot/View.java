package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.HashMap;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	     int x = 200;
	     int y = 100;
	     double w = 75;
	     double h = 75;
	     private Map<Data,Point2D> markedData = new HashMap<Data,Point2D>();
	     

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {
			
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.clearRect(0, 0, getWidth(), getHeight());
			
			drawGrid(g2D);

			for (Data d : model.getList()) {

				if (markedData.containsKey(d)){
					g2D.setColor(Color.ORANGE);
				}
				else{
					g2D.setColor(Color.BLACK);
				}
				for (int i = 0; i < 7; ++i) {
					Range rangeI = model.getRanges().get(i);
					double newValueI = getMappedValue(d.getValues()[i], rangeI.getMin(), rangeI.getMax(), y, y+h);
					for (int j = 0; j < 7; ++j) {
						Range rangeJ = model.getRanges().get(j);
						double newValueJ = getMappedValue(d.getValues()[j], rangeJ.getMin(), rangeJ.getMax(), x, x+w);
						if (withinMarker(newValueJ,newValueI)){
							Point2D p = new Point2D.Double(newValueJ,newValueI);
							markedData.put(d,p);
						}
						else {
							if (markedData.containsKey(d)){
								Point2D p = markedData.get(d);
								if (!withinMarker(p.getX(), p.getY())){
									markedData.remove(d);
								}
							}
						}
						Rectangle2D rect = new Rectangle2D.Double(newValueJ-2,newValueI-2,4,4);
						g2D.draw(rect);
						x += w;
					}
					y += h;
					x = 200;
				}
				y = 100;

			}
			
			g2D.setColor(Color.RED);
			g2D.draw(markerRectangle);
			g2D.setColor(Color.BLACK);
			
			int i = 0;
			int j = 10;

	        for (String l : model.getLabels()) {
	        	g.drawString(l, 200+i*75, 50+j);
	        	g.drawString(l, 20, 150+i*75);
				i++;
				j = -j;
			}

			
		}
		public void setModel(Model model) {
			this.model = model;
		}
		
		private void drawGrid(Graphics2D g2D) {
			for (int i = 1; i < 50; ++i) {
				Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);
				g2D.draw(rect);
				x += w;
				if(i%7 == 0) {
					y += h;
					x = 200;
				}	
			}
			y = 100;
		}
		
		private double getMappedValue(double oldValue, double oldMin, double oldMax, double newMin, double newMax) {

			double oldRange = oldMax - oldMin;
			double newRange = newMax - newMin;
			double newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;
			return newValue;
		}
		
		public Map<Data,Point2D> getMarkedData(){
			return this.markedData;
		}

		
		private boolean withinMarker(double x, double y){
			return markerRectangle.contains(x,y);
		}

}
