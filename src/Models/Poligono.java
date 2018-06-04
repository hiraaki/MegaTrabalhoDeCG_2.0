package Models;

import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Poligono {
    public ArrayList<Vertice> vertices;
    public ArrayList<Aresta> arestas;
    public Vertice Central;


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

    public Poligono(Vertice ini, Vertice fim, int lados, Color cor){
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
        double R=abs(abs(fim.x)-abs(ini.x));
        System.out.println(this.Central.x+" "+this.Central.y);
        for(int i=0;i<lados;i++){
            xtemp = (R * Math.cos((2 * Math.PI * (i)) / lados + grau) + V.x);
            ytemp = (R * Math.sin((2 * Math.PI * (i)) / lados + grau) + V.y);
            vertices.add(new Vertice(xtemp,ytemp, 0));
            //System.out.println(this.Vertices.get(i).X+" "+this.Vertices.get(i).Y);
            //System.out.println(R);
        }
        /*for (int i =0 ; i<this.Vertices.size();i++){
            System.out.println(this.Vertices.get(i).X+" "+this.Vertices.get(i).Y);
        }*/
        this.setArestas();
        calcCentroid();
        System.out.println("Centroid :"+Central.x+","+Central.y);
        //this.printVertices();

    }

    public void setArestas(){
        for (int i = 0; i < this.vertices.size(); i++) {
            if(i!=this.vertices.size()-1){
                this.arestas.add(new Aresta(this.vertices.get(i),this.vertices.get(i+1)));
            }else if(i==this.vertices.size()-1){
                this.arestas.add(new Aresta(this.vertices.get(i),this.vertices.get(0)));
            }
        }
    }

    public void calcCentroid(){
        double somaX=0;
        double somaY=0;
        double somaZ=0;

        for(Vertice v:this.vertices){
            somaX+=v.x;
            somaY+=v.y;
            somaZ+=v.z;
        }
        this.Central=new Vertice(somaX/2,somaY/2,somaZ/2);
    }
}
