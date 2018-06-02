package Models;

public class Aresta {
    public Vertice ini;
    public Vertice fim;

    public Aresta() {
        this.ini= new Vertice();
        this.fim= new Vertice();
    }

    public Aresta(Vertice ini, Vertice fim) {
        this.ini = ini;
        this.fim = fim;
    }
}
