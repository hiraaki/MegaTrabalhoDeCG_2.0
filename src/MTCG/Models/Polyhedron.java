package Models;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.toRadians;
/**
 * Class who represent a Polyhedron
 * @author Gabriela, Hamã, Mauricio
 */
public class Polyhedron implements Serializable {
    public ArrayList<Polygon> faces;
    public ArrayList<Vertex> vertices;
    public Vertex Central;

    public Polyhedron() {
        this.vertices = new ArrayList<>();
        this.faces= new ArrayList<>();
        this.Central = new Vertex();
    }

    /**
     * Create a polyhedron with a a list of polygon
     * @param faces List of Polygons who connected form a polygon
     */
    public Polyhedron(ArrayList<Polygon> faces) {
        this.faces = faces;
    }

    /**
     * Draw the polyhedron
     * @param gc Grafics Context where the polyhedron will be drawn
     * @param lado Is the orientation of the plan 1(x,y), 2(x,z), 3(z,y)
     */
    public void draw(GraphicsContext gc, int lado){
        for(Polygon p:this.faces){
            p.draw(gc,lado);
        }
    }
}
