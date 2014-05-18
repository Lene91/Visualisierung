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
	
	double p_focus_x;
	double p_focus_y;
	

	public void setMouseCoords(int x, int y, View view) {
		p_focus_x = x;
		p_focus_y = y;
	}

	public Model transform(Model model, View view) {

		double p_boundary_x = view.getWidth();
		double p_boundary_y = view.getHeight();
		// Vertex oder Element?
		for (Vertex vertex:model.getVertices()){
			
			double p_norm_x = vertex.getX();
			double p_norm_y = vertex.getY();
			
			double p_fish_x = F1(p_norm_x, p_focus_x, p_boundary_x);
			double p_fish_y = F1(p_norm_y, p_focus_y, p_boundary_y);

			double q_norm_x;
			double q_norm_y;
			
			if (p_norm_x > p_focus_x)
				q_norm_x = p_norm_x + vertex.getWidth()/2;
			else
				q_norm_x = p_norm_x - vertex.getWidth()/2;
			
			if (p_norm_y > p_focus_y)
				q_norm_y = p_norm_y + vertex.getWidth()/2;
			else
				q_norm_y = p_norm_y - vertex.getWidth()/2;
			
			double q_fish_x = F1(q_norm_x, p_focus_x, p_boundary_x);
			double q_fish_y = F1(q_norm_y, p_focus_y, p_boundary_y);
			
			double s_geom = 2 * Math.min(Math.abs(q_fish_x - p_fish_x), Math.abs(q_fish_y - p_fish_y));
			double s_fish = s_geom;
			
			vertex.setX(p_fish_x);
			vertex.setY(p_fish_y);
			
			double h = vertex.getHeight();
			double w = vertex.getWidth();
			vertex.setHeight(h/(w+h) * s_fish);
			vertex.setWidth(w/(w+h) * s_fish);
			
		}	
		
		
		// TODO Auto-generated method stub
		return model;
	}
	
	public double F1(double p_norm, double p_focus, double p_boundary){
		double d_norm = p_norm - p_focus;
		double d_max;
		double p_fish;
		if (p_norm > p_focus){
			d_max = p_boundary  - p_focus;
			double d_norm_div_max = d_norm/d_max;
			p_fish = p_focus + G(d_norm_div_max)*d_max;
		}
		else {
			//d_norm = p_focus - p_norm;
			d_max =-p_focus;
			double d_norm_div_max = d_norm/d_max;
			if (d_norm_div_max > 1)
				d_norm_div_max = 1;
			p_fish = p_focus + G(d_norm_div_max)*d_max;
		}
		return p_fish;
	}
	
	// x = d_norm/d_max 
	public double G(double x){
		double d = 2;
		double r = ((d+1)*x)/(d*x+1);
		return r;
	}
	
}
