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
        faces= new ArrayList<>();
    }

    public Polyhedron(ArrayList<Polygon> faces) {
        this.faces = faces;
    }

    public Polyhedron(Polygon arevolucionar, int particoes, int lado, double Angulo) {
        double angulo = toRadians(Angulo)/particoes;
        double anguloAtual = angulo;
        double anguloNovo = 0;
        this.faces = new ArrayList<>();
        Polygon atual = new Polygon();
        Polygon novo;
        atual.copyIn(arevolucionar.vertices);
        for (int n=0;n<particoes;n++) {

            anguloNovo+=angulo;
            novo = new Polygon();
            novo.copyIn(arevolucionar.vertices);
            novo.rotaciona(anguloNovo,lado);
            //System.out.println(Math.toDegrees(anguloNovo));

            for (int i = 0; i < arevolucionar.arestas.size(); i++) {
                Polygon pface = new Polygon();
                pface.arestas.add(atual.arestas.get(i));
                pface.arestas.add(new Edge(atual.arestas.get(i).ini, novo.arestas.get(i).ini, pface));
                pface.arestas.add(novo.arestas.get(i));
                pface.arestas.add(new Edge(atual.arestas.get(i).fim, novo.arestas.get(i).fim, pface));
                pface.vertices.add(atual.arestas.get(i).ini);
                pface.vertices.add(atual.arestas.get(i).fim);
                pface.vertices.add(novo.arestas.get(i).fim);
                pface.vertices.add(novo.arestas.get(i).ini);
                Polygon face = new Polygon();
                face.copyIn(pface.vertices);
                faces.add(face);
            }
            anguloAtual=anguloNovo;
            atual=new Polygon();
            atual.copyIn(arevolucionar.vertices);
            atual.rotaciona(anguloAtual,lado);
        }
        this.Central=new Vertex();
        this.calcCentroid();
    }

    public void draw(GraphicsContext gc, int lado){
        for(Polygon p:this.faces){
            p.draw(gc,lado);
        }
    }

    public void translada(Vertex v){
        for(Polygon p: this.faces){
            p.translada(v);
        }
        this.Central.x+=v.x;
        this.Central.y+=v.y;
        this.Central.z+=v.z;
    }
    public void rotaciona(double radians,int lado){
        for(Polygon p: this.faces){
            p.rotaciona(radians,lado);
        }
        double seno = Math.sin(radians);
        double cose = Math.cos(radians);
        double ante=0;
        if(lado==1){
            ante=(this.Central.x*cose)-(this.Central.y*seno);
            this.Central.y=(this.Central.x*seno)+(this.Central.y*cose);
            this.Central.x=ante;
        }else if(lado==2){
            ante=(this.Central.z*cose)-(this.Central.y*seno);
            this.Central.y=(this.Central.z*seno)+(this.Central.y*cose);
            this.Central.z=ante;
        }else if(lado==3){
            ante=(this.Central.x*cose)+(this.Central.z*seno);
            this.Central.z=(this.Central.z*cose)-(this.Central.x*seno);
            this.Central.x=ante;
        }
    }
    public void calcCentroid(){
        double maiorX=Double.MIN_VALUE, maiorY=Double.MIN_VALUE, maiorZ=Double.MIN_VALUE;
        double menorX=Double.MAX_VALUE, menorY=Double.MAX_VALUE, menorZ=Double.MAX_VALUE;
        for(Polygon p:this.faces){

            if(p.Central.x>maiorX) maiorX=p.Central.x;

            if(p.Central.x<menorX) menorX=p.Central.x;

            if(p.Central.y>maiorY) maiorY=p.Central.y;

            if(p.Central.y<menorY) menorY=p.Central.y;

            if(p.Central.z>maiorZ) maiorZ=p.Central.z;

            if(p.Central.z<menorZ) menorZ=p.Central.z;
        }
        this.Central=new Vertex(menorX+((maiorX-menorX)/2),menorY+((maiorY-menorY)/2),menorZ+((maiorZ-menorZ)/2));
    }
    public void scala(double variacao,int eixo){
        for (Polygon p:this.faces){
            p.scala(variacao,eixo);
        }
        if(eixo==1){
            this.Central.x = this.Central.x * variacao;
        }else if(eixo==2){
            this.Central.y = this.Central.y * variacao;
        }else if(eixo==3){
            this.Central.y = this.Central.y * variacao;
        }
    }
}
