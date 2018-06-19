package Models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Poliedro {
    public ArrayList<Poligono> faces;
    public ArrayList<Vertice> vertices;

    public Poliedro() {
        faces= new ArrayList<>();
    }

    public Poliedro(ArrayList<Poligono> faces) {
        this.faces = faces;
    }

    public Poliedro(Poligono arevolucionar,int particoes,int lado) {
        double angulo = (2*Math.PI)/particoes;
        double anguloAtual = angulo;
        double anguloNovo = 0;
        this.faces = new ArrayList<>();
        Poligono atual = new Poligono();
        Poligono novo;
        atual.copyIn(arevolucionar.vertices);
        for (int n=0;n<particoes;n++) {

            anguloNovo+=angulo;
            novo = new Poligono();
            novo.copyIn(arevolucionar.vertices);
            novo.rotaciona(anguloNovo,lado);
            //System.out.println(Math.toDegrees(anguloNovo));

            for (int i = 0; i < arevolucionar.arestas.size(); i++) {
                Poligono pface = new Poligono();
                pface.arestas.add(atual.arestas.get(i));
                pface.arestas.add(new Aresta(atual.arestas.get(i).ini, novo.arestas.get(i).ini, pface));
                pface.arestas.add(novo.arestas.get(i));
                pface.arestas.add(new Aresta(atual.arestas.get(i).fim, novo.arestas.get(i).fim, pface));
                pface.vertices.add(atual.arestas.get(i).ini);
                pface.vertices.add(atual.arestas.get(i).fim);
                pface.vertices.add(novo.arestas.get(i).fim);
                pface.vertices.add(novo.arestas.get(i).ini);
                Poligono face = new Poligono();
                face.copyIn(pface.vertices);
                faces.add(face);
            }
            anguloAtual=anguloNovo;
            atual=new Poligono();
            atual.copyIn(arevolucionar.vertices);
            atual.rotaciona(anguloAtual,lado);
        }


    }

    public void draw(GraphicsContext gc, int lado){
        for(Poligono p:this.faces){
            p.draw(gc,lado);
        }
    }

    public void translada(Vertice v){
        for(Poligono p: this.faces){
            p.translada(v);
        }
    }
    public void rotaciona(double radians,int lado){
        for(Poligono p: this.faces){
            p.rotaciona(radians,lado);
        }
    }
}
