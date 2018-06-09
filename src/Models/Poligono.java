package Models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Poligono {
    public ArrayList<Vertice> vertices;
    public ArrayList<Aresta> arestas;
    public Vertice Central;
    public Poliedro pai;
    public Poligono() {
        this.vertices=new ArrayList<>();
        this.arestas=new ArrayList<>();
        this.Central = new Vertice();
        this.pai = new Poliedro();
    }

    public Poligono(ArrayList<Vertice> vertices, ArrayList<Aresta> arestas) {
        this.vertices = vertices;
        this.arestas = arestas;
    }

    public Poligono(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
        this.arestas = new ArrayList<>();
        this.setArestas();
        this.calcCentroid();
    }

    public Poligono(Vertice ini, int lados, Color cor, int plano){ //planos 1 - XY / 2 - XZ / 3 - YZ
        this.Central= new Vertice();
        this.Central= ini;
        this.arestas = new ArrayList();
        this.vertices = new ArrayList();
        //this.face = new Face(Color.BLACK,cor);
        Vertice V=new Vertice();
        V=ini;
        double cos;
        double sen;
        double xtemp;
        double ytemp;
        double grau = (360/lados);
        double R=60;
        //System.out.println(this.Central.x+" "+this.Central.y);
        for(int i=0;i<lados;i++){
//            xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.x);
//            ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.y);

            if(plano == 1){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.x);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.y);
                vertices.add(new Vertice(xtemp,ytemp, 0));
               // System.out.println("VERTICE X="+xtemp+" Y="+ytemp);
            }else if(plano == 2){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.x);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.z);
                vertices.add(new Vertice(xtemp,0, ytemp));
              //  System.out.println("VERTICE X="+xtemp+" Z="+ytemp);
            } else if(plano ==3){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.z);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.y);
                vertices.add(new Vertice(0,ytemp, xtemp));
             //   System.out.println("VERTICE Z="+xtemp+" Y="+ytemp);
            }

            //System.out.println(this.Vertices.get(i).X+" "+this.Vertices.get(i).Y);
            //System.out.println(R);
        }
        /*for (int i =0 ; i<this.Vertices.size();i++){
            System.out.println(this.Vertices.get(i).X+" "+this.Vertices.get(i).Y);
        }*/
        this.setArestas();
        calcCentroid();
        //System.out.println("Centroid :"+Central.x+","+Central.y);
        //this.printVertices();

    }

    public void setArestas(){
        for (int i = 0; i < this.vertices.size(); i++) {
            if(i!=this.vertices.size()-1){
                this.arestas.add(new Aresta(this.vertices.get(i),this.vertices.get(i+1),this));
            }else if(i==this.vertices.size()-1){
                this.arestas.add(new Aresta(this.vertices.get(i),this.vertices.get(0),this));
            }
        }
    }

    public void calcCentroid(){
        double maiorX=Double.MIN_VALUE, maiorY=Double.MIN_VALUE, maiorZ=Double.MIN_VALUE;
        double menorX=Double.MAX_VALUE, menorY=Double.MAX_VALUE, menorZ=Double.MAX_VALUE;

        for(Vertice v:this.vertices){
            if(v.x>maiorX) maiorX=v.x;

            if(v.x<menorX) menorX=v.x;

            if(v.y>maiorY) maiorY=v.y;

            if(v.y<menorY) menorY=v.y;

            if(v.z>maiorZ) maiorZ=v.z;

            if(v.z<menorZ) menorZ=v.z;
        }
        this.Central=new Vertice(menorX+((maiorX-menorX)/2),menorY+((maiorY-menorY)/2),menorZ+((maiorZ-menorZ)/2));
    }
    public void draw(GraphicsContext gc,int lado){
        for (Aresta a:this.arestas){
            a.draw(gc,lado);
        }
    }

    public void copyIn(ArrayList<Vertice> vertices){
        for(Vertice v:vertices){
            this.vertices.add(new Vertice(v.x,v.y,v.z));
        }
        this.setArestas();
    }
    public void setVertices(){
        for(Aresta a: this.arestas){
            this.vertices.add(a.ini);
            this.vertices.add(a.fim);
        }
    }

    public boolean isselected(Vertice v){

        boolean found=false;
        for (Aresta a : this.arestas){
            //System.out.println(a.DistanceFromLine(v));
            if(a.DistanceFromLine(v)<5){
                found=true;
                break;
            }
        }
        return found;
    }
    public void translada(Vertice V){
        double x =V.x-this.Central.x;
        double y =V.y-this.Central.y;
        for(Vertice v: this.vertices){
            v.x+=x;
            v.y+=y;
        }
        calcCentroid();
    }

}
