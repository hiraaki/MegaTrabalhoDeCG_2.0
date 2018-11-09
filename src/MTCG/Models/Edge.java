package Models;

import Controller.EdgeController;
import javafx.scene.canvas.GraphicsContext;
import java.io.Serializable;

import static java.lang.Math.abs;

public class Edge implements Serializable {
    public Vertex start;
    public Vertex end;
    public Polygon father;
    public Edge() {
        this.father = new Polygon();
        this.start= new Vertex();
        this.end = new Vertex();
    }

    public Edge(Vertex ini, Vertex fim, Polygon father) {
        this.start = ini;
        this.end = fim;
        this.father = father;
    }
    public void draw(GraphicsContext gc, int surface){
        if(surface ==1)
            gc.strokeLine(this.start.x,this.start.y,this.end.x,this.end.y);
        else if(surface ==3){
            gc.strokeLine(this.start.x,this.start.z,this.end.x,this.end.z);
        }else if(surface ==2){
            gc.strokeLine(this.start.z,this.start.y,this.end.z,this.end.y);
        }
    }

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
