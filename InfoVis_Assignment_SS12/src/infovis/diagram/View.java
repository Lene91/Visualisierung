package infovis.diagram;

import infovis.diagram.elements.Element;
import infovis.diagram.elements.Vertex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double translateX= 0;
	private double translateY=0;
	private double overviewScale = 1;
	private Rectangle2D marker = new Rectangle2D.Double();
	private Rectangle2D overviewRect = new Rectangle2D.Double(10,10, 200, 180);   

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		g2D.scale(scale,scale);
		g2D.translate(-translateX, -translateY); 
		paintDiagram(g2D);
		g2D.translate(translateX, translateY);
		g2D.scale(1.0/scale, 1.0/scale);
		
		g2D.setColor(Color.WHITE);
		g2D.fill(overviewRect);
		g2D.clip(overviewRect);
		
		Rectangle2D r = new Rectangle2D.Double();
		for (Element element: model.getElements()){
			r.add(element.getX(),element.getY());
			r.add(element.getX()+Vertex.STD_WIDTH,element.getY()+Vertex.STD_HEIGHT);
		}
		
		double scaleX = overviewRect.getWidth() / r.getWidth();
		double scaleY = overviewRect.getHeight() / r.getHeight();
		overviewScale = Math.min(scaleX, scaleY);
		
		g2D.translate(overviewRect.getX(), overviewRect.getY());
		g2D.scale(overviewScale, overviewScale);
		paintDiagram(g2D);
		g2D.scale(1.0/overviewScale, 1.0/overviewScale);
		g2D.translate(-overviewRect.getX(), -overviewRect.getY());
		
		marker.setRect(overviewRect.getX() + translateX * overviewScale, overviewRect.getY() + translateY * overviewScale, getWidth() * overviewScale / scale, getHeight() * overviewScale / scale);
		g2D.setColor(new Color(255,255,0,100));
		g2D.fill(marker);

	}
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D);
		}	
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}	
	// vorher input:int,int
	public void updateMarker(double x, double y){
		translateX = ( x-overviewRect.getX() ) / overviewScale;
		translateY = ( y-overviewRect.getY() ) / overviewScale;
		
		marker.setRect(x, y, getWidth() * overviewScale / scale, getHeight() * overviewScale / scale);
		
	}
	public void updateOverview(double x, double y){
		overviewRect.setRect(x,y,200,180);
	}
	
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
	
	public Rectangle2D getOverviewRect(){
		return overviewRect;
	}
	public boolean overviewContains(int x, int y){
		return overviewRect.contains(x,y);
	}
	
}
 