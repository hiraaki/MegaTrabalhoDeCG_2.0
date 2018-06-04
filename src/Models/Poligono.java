package Models;

import java.util.ArrayList;

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
        this.setarestas();
        this.calcCentroid();
    }
    public void setarestas(){
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
