package Models;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.toRadians;

public class Polyhedron implements Serializable {
    public ArrayList<Polygon> faces;
    public ArrayList<Vertex> vertices;
    public Vertex Central;

    public Polyhedron() {
        this.vertices = new ArrayList<>();
        this.faces= new ArrayList<>();
        this.Central = new Vertex();
    }

    public Polyhedron(ArrayList<Polygon> faces) {
        this.faces = faces;
    }


    public void draw(GraphicsContext gc, int lado){
        for(Polygon p:this.faces){
            p.draw(gc,lado);
        }
    }

//    public void translada(Vertex v){
//        for(Polygon p: this.faces){
//            p.translada(v);
//        }
//        this.Central.x+=v.x;
//        this.Central.y+=v.y;
//        this.Central.z+=v.z;
//    }
//    public void rotaciona(double radians,int lado){
//        for(Polygon p: this.faces){
//            p.rotaciona(radians,lado);
//        }
//        double seno = Math.sin(radians);
//        double cose = Math.cos(radians);
//        double ante=0;
//        if(lado==1){
//            ante=(this.Central.x*cose)-(this.Central.y*seno);
//            this.Central.y=(this.Central.x*seno)+(this.Central.y*cose);
//            this.Central.x=ante;
//        }else if(lado==2){
//            ante=(this.Central.z*cose)-(this.Central.y*seno);
//            this.Central.y=(this.Central.z*seno)+(this.Central.y*cose);
//            this.Central.z=ante;
//        }else if(lado==3){
//            ante=(this.Central.x*cose)+(this.Central.z*seno);
//            this.Central.z=(this.Central.z*cose)-(this.Central.x*seno);
//            this.Central.x=ante;
//        }
//    }
//
//    public void scala(double variacao,int eixo){
//        for (Polygon p:this.faces){
//            p.scala(variacao,eixo);
//        }
//        if(eixo==1){
//            this.Central.x = this.Central.x * variacao;
//        }else if(eixo==2){
//            this.Central.y = this.Central.y * variacao;
//        }else if(eixo==3){
//            this.Central.y = this.Central.y * variacao;
//        }
//    }
}
