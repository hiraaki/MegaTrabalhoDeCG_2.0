package Models;

import Controller.PolygonController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;

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


    public void draw(GraphicsContext gc,int lado){
        for (Edge a:this.edges){
            a.draw(gc,lado);
        }
    }

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
