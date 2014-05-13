package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Element;
import infovis.diagram.elements.Vertex;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Iterator;

/*
 * 
 */

public class Fisheye implements Layout{

	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
	}

	public Model transform(Model model, View view) {
		
		double p_focus_x = 250;
		double p_focus_y = 250;
		
		double p_boundary_x = Vertex.STD_WIDTH;
		double p_boundary_y = Vertex.STD_HEIGHT;
		// Vertex oder Element?
		// for (Vertex vertex: model.getVertices()){
		for (Element element: model.getElements()){
			
			double p_norm_x = element.getX();
			double p_norm_y = element.getY();
			
			double d_norm_x = p_norm_x - p_focus_x;
			double d_norm_y = p_norm_y - p_focus_y;
			
			double d_max_x = - p_focus_x;
			if (p_norm_x > p_focus_x)
				d_max_x = p_boundary_x - p_focus_x;
			
			double d_max_y = - p_focus_y;
			if (p_norm_y > p_focus_y)
				d_max_y = p_boundary_y - p_focus_y;
			
			double p_fish_x = p_focus_x + G(d_norm_x/d_max_x)*d_max_x;
			double p_fish_y = p_focus_y + G(d_norm_y/d_max_y)*d_max_y;

		}	
		
		
		
		// TODO Auto-generated method stub
		return null;
	}
	
	// x = d_norm/d_max 
	public double G(double x){
		double d = 5;
		double nom = (d + 1)* x;
		double denom = d*x + 1;
		return nom/denom;
	}
	
}
