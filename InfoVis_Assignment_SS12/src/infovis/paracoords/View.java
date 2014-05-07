package infovis.paracoords;

import infovis.scatterplot.Data;
import infovis.scatterplot.Model;
import infovis.scatterplot.Range;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
    private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	private int start = 110;
	private int end = 510;
	private int xStep = 100;
    private Map<Data,Point2D> markedData = new HashMap<Data,Point2D>();
	
	public Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		drawSystem(g2D);
		
		drawData(g2D);
		
		g2D.setColor(Color.RED);
		g2D.draw(markerRectangle);
	}

	private void drawSystem(Graphics2D g2D) {
		int x = xStep;
		int j = 10;
		for(int i = 0; i < model.getLabels().size(); ++i)
		{
			String label = model.getLabels().get(i);
			g2D.drawString(label,x,50+j);
			Range r = model.getRanges().get(i);
			g2D.drawString(String.valueOf(r.getMax()), x, start-10);
			Line2D line = new Line2D.Double(x,start,x,end);
			g2D.draw(line);
			g2D.drawString(String.valueOf(r.getMin()), x, end+15);
			x += xStep;
			j = -j;
		}
	}
	
	private void drawData(Graphics2D g2D)
	{
		for (Data d : model.getList()) {
			
			if (markedData.containsKey(d)){
				g2D.setColor(Color.ORANGE);
			}
			else{
				g2D.setColor(Color.BLACK);
			}
			
			int xPos = xStep;
			Path2D line = new Path2D.Double();
			for (int i = 0; i < model.getLabels().size(); ++i) {
				Range range = model.getRanges().get(i);
				double newValue = getMappedValue(d.getValues()[i], range.getMin(), range.getMax(), start, end);
				if(i == 0)
					line.moveTo(xPos, newValue);
				else
					line.lineTo(xPos, newValue);
				if (withinMarker(xPos,newValue)){
					Point2D p = new Point2D.Double(xPos,newValue);
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
				xPos += xStep;
			}
			g2D.draw(line);
		
		}
		
	
	}
	
	private double getMappedValue(double oldValue, double oldMin, double oldMax, double newMin, double newMax) {

		double oldRange = oldMax - oldMin;
		double newRange = newMax - newMin;
		double newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;
		return newValue;
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public Map<Data,Point2D> getMarkedData(){
		return this.markedData;
	}

	private boolean withinMarker(double x, double y){
		return markerRectangle.contains(x,y);
	}
	
	public boolean isNearAxis(double x, double y) {
		double tmp = x % xStep; 
		if (xStep - tmp < 10 && x > 90 && y > start && y < end)
			return true;
		else return false;		
	}
	
}
