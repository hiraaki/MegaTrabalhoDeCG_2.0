package Models;

import Controller.PolygonController;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;
/**
 * Class who represent a polygon
 * @author Gabriela, Hamã, Mauricio
 */
public class Polygon implements Serializable {
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public Vertex Central;
    public Polyhedron father;


    public Polygon() {
        this.vertices=new ArrayList<>();
        this.edges =new ArrayList<>();
        this.Central = new Vertex();
        this.father = new Polyhedron();
    }

    /**
     * Create a new Polygon
     * @param vertices Sequence of Vertexs, who linked forms a polygon
     * @param edges Sequence of edges of a polygon
     */
    public Polygon(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        for(Edge a:this.edges){
            a.father =this;
        }
        this.Central=new Vertex();
        PolygonController pc = new PolygonController();
        this.Central = pc.calcCentroid(this.vertices);
    }

    /**
     * Draw the polygon
     * @param gc Grafics Context where the edge will be drawn
     * @param lado Is the orientation of the plan 1(x,y), 2(x,z), 3(z,y)
     */
    public void draw(GraphicsContext gc,int lado){
        for (Edge a:this.edges){
            a.drawn(gc,lado);
        }
    }

    /**
     * Test if a vertex is near enough to be selected
     * @param v The vertex to be compared with the edges of the polygon
     * @param lados The plan orientation where will be tested
     * @return The answer of  is polygon selected or is not?
     */
    public boolean isselected(Vertex v, int lados){
        boolean found=false;
        for (Edge a : this.edges){
            //System.out.println(a.DistanceFromLine(v));
            if(a.selected(v,lados)){
                found=true;
                break;
            }
        }
        return found;
    }
}
