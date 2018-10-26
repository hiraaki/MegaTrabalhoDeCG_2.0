package Models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Polygon implements Serializable {
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> arestas;
    public Vertex Central;
    public Polyhedron pai;

    public Polygon() {
        this.vertices=new ArrayList<>();
        this.arestas=new ArrayList<>();
        this.Central = new Vertex();
        this.pai = new Polyhedron();
    }

    public Polygon(ArrayList<Vertex> vertices, ArrayList<Edge> arestas) {
        this.vertices = vertices;
        this.arestas = arestas;
        for(Edge a:this.arestas){
            a.pai=this;
        }
        this.Central=new Vertex();
        this.calcCentroid();
    }



    public Polygon(Vertex ini, int lados, Color cor, int plano){ //planos 1 - XY / 2 - XZ / 3 - YZ
        this.Central= new Vertex();
        this.Central= ini;
        this.arestas = new ArrayList();
        this.vertices = new ArrayList();
        //this.face = new Face(Color.BLACK,cor);
        Vertex V=new Vertex();
        V=ini;
        double cos;
        double sen;
        double xtemp;
        double ytemp;
        double grau = (360/lados);
        double R=60;
        for(int i=0;i<lados;i++){
            if(plano == 1){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.x);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.y);
                vertices.add(new Vertex(xtemp,ytemp, 0));
            }else if(plano == 2){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.x);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.z);
                vertices.add(new Vertex(xtemp,0, ytemp));
            } else if(plano ==3){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.z);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.y);
                vertices.add(new Vertex(0,ytemp, xtemp));
            }
        }
        this.setArestas();
        calcCentroid();
    }

    public void setArestas(){
        for (int i = 0; i < this.vertices.size(); i++) {
            if(i!=this.vertices.size()-1){
                this.arestas.add(new Edge(this.vertices.get(i),this.vertices.get(i+1),this));
            }else if(i==this.vertices.size()-1){
                this.arestas.add(new Edge(this.vertices.get(i),this.vertices.get(0),this));
            }
        }
    }

    public void calcCentroid(){
        double maiorX=Double.MIN_VALUE, maiorY=Double.MIN_VALUE, maiorZ=Double.MIN_VALUE;
        double menorX=Double.MAX_VALUE, menorY=Double.MAX_VALUE, menorZ=Double.MAX_VALUE;

        for(Vertex v:this.vertices){
            if(v.x>maiorX) maiorX=v.x;

            if(v.x<menorX) menorX=v.x;

            if(v.y>maiorY) maiorY=v.y;

            if(v.y<menorY) menorY=v.y;

            if(v.z>maiorZ) maiorZ=v.z;

            if(v.z<menorZ) menorZ=v.z;
        }
        this.Central=new Vertex(menorX+((maiorX-menorX)/2),menorY+((maiorY-menorY)/2),menorZ+((maiorZ-menorZ)/2));
    }
    public void draw(GraphicsContext gc,int lado){
        for (Edge a:this.arestas){
            a.draw(gc,lado);
        }
    }

    public void copyIn(ArrayList<Vertex> vertices){
        this.Central=new Vertex();
        for(Vertex v:vertices){
            this.vertices.add(new Vertex(v.x,v.y,v.z));
        }
        this.setArestas();
        this.calcCentroid();
    }

    public boolean isselected(Vertex v, int lados){
        boolean found=false;
        for (Edge a : this.arestas){
            //System.out.println(a.DistanceFromLine(v));
            if(a.selected(v,lados)){
                found=true;
                break;
            }
        }
        return found;
    }
    public void translada(Vertex V){
//        double x = V.x;//-this.Central.x;
//        double y =V.y;//-this.Central.y;
//        double z =V.z;//-this.Central.z;
        for(Vertex v: this.vertices){
            v.x+=V.x;
            v.y+=V.y;
            v.z+=V.z;
        }
        this.Central.x+=V.x;
        this.Central.y+=V.y;
        this.Central.z+=V.z;

    }



    public void rotaciona(double radians,int lado){
        if(lado==1) {
            double seno = Math.sin(radians);
            double cose = Math.cos(radians);
            double ante=0;
            for(Vertex v: vertices){
                ante=(v.x*cose)-(v.y*seno);
                v.y=(v.x*seno)+(v.y*cose);
                v.x=ante;
            }
            ante=(this.Central.x*cose)-(this.Central.y*seno);
            this.Central.y=(this.Central.x*seno)+(this.Central.y*cose);
            this.Central.x=ante;


        }else if(lado==2){
            double seno = Math.sin(radians);
            double cose = Math.cos(radians);
            double ante=0;
            for(Vertex v: this.vertices){
                ante=(v.z*cose)-(v.y*seno);
                v.y=(v.z*seno)+(v.y*cose);
                v.z=ante;
            }
            ante=(this.Central.z*cose)-(this.Central.y*seno);
            this.Central.y=(this.Central.z*seno)+(this.Central.y*cose);
            this.Central.z=ante;


        }else if(lado==3){
            double seno = Math.sin(radians);
            double cose = Math.cos(radians);
            double ante=0;
            for(Vertex v: vertices){
                ante=(v.x*cose)+(v.z*seno);
                v.z=(v.z*cose)-(v.x*seno);
                v.x=ante;
            }
            ante=(this.Central.x*cose)+(this.Central.z*seno);
            this.Central.z=(this.Central.z*cose)-(this.Central.x*seno);
            this.Central.x=ante;

        }
    }
    public void scala(double novoTamanho,int eixo){
        if(eixo==1) {
            for (Vertex v : this.vertices) {
                v.x = v.x * novoTamanho;
            }
            this.Central.x = this.Central.x * novoTamanho;
        }else if(eixo==2){
            for(Vertex v : this.vertices){
                v.y=v.y*novoTamanho;
            }
            this.Central.y=this.Central.y*novoTamanho;
        }else if (eixo==3){
            for(Vertex v : this.vertices){
                v.z=v.z*novoTamanho;
            }
            this.Central.z=this.Central.z*novoTamanho;
        }
    }


}
