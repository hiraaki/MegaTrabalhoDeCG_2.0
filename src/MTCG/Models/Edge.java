package Models;

import Controller.EdgeController;
import javafx.scene.canvas.GraphicsContext;
import java.io.Serializable;

import static java.lang.Math.abs;
/**
 * Class who represent a Edge
 * @author Gabriela, Hamã, Mauricio
 */
public class Edge implements Serializable {
    public Vertex start;
    public Vertex end;
    public Polygon father;
    public Edge() {
        this.father = new Polygon();
        this.start= new Vertex();
        this.end = new Vertex();
    }

    /**
     * Create a Edge
     * @param start The vertex in the beginning of the Edge
     * @param end The vertex in the end of the Edge
     * @param father  Is the polygon who the Edge belongs
     */
    public Edge(Vertex start, Vertex end, Polygon father) {
        this.start = start;
        this.end = end;
        this.father = father;
    }

    /**
     * Draw the vertex
     * @param gc Grafics Context where the edge will be drawn
     * @param surface The orientation of the plan 1(x,y), 2(x,z), 3(z,y)
     */
    public void drawn(GraphicsContext gc, int surface){
        if(surface ==1)
            gc.strokeLine(this.start.x,this.start.y,this.end.x,this.end.y);
        else if(surface ==3){
            gc.strokeLine(this.start.x,this.start.z,this.end.x,this.end.z);
        }else if(surface ==2){
            gc.strokeLine(this.start.z,this.start.y,this.end.z,this.end.y);
        }
    }

    /**
     *
     * @param v The vertex to be compared with the edge
     * @param surface The plan orientation where will be tested
     * @return The answer of  is edge selected or is not?
     */
    public boolean selected(Vertex v, int surface){
        EdgeController ec= new EdgeController();
        if(ec.distanceFromLine(this,v, surface)<5){
//            System.out.println("x:"+v.x+" y:"+v.y+" z:"+v.z);
//            System.out.println("x:"+this.ini.x+" y:"+this.ini.y+" z:"+this.ini.z);
//            System.out.println("x:"+this.end.x+" y:"+this.end.y+" z:"+this.end.z);
//            System.out.println(this.DistanceFromLine(v,lado));
//            System.out.println();
            return true;
        }else {
            return false;
        }
    }
}
