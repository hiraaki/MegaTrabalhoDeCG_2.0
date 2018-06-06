package Models;

import java.util.ArrayList;

public class Poliedro {
    public ArrayList<Poligono> faces;

    public Poliedro(ArrayList<Poligono> faces) {
        this.faces = faces;
    }

    public Poliedro(Poligono arevolucionar,int particoes) {
        for (int n=0;n<particoes;n++)
        for (int i=0;i<=arevolucionar.arestas.size();i++){

            Poligono novo = new Poligono();
            Poligono pface = new Poligono();

            novo.copyIn(arevolucionar.vertices);

            pface.arestas.add(arevolucionar.arestas.get(i));
            pface.arestas.add(new Aresta(arevolucionar.arestas.get(i).ini,novo.arestas.get(i).ini));
            pface.arestas.add(novo.arestas.get(i));
            pface.arestas.add(new Aresta(arevolucionar.arestas.get(i).fim,novo.arestas.get(i).fim));
            pface.setVertices();
            faces.add(pface);
        }
    }
}
