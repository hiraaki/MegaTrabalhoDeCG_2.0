package Controller;

import Models.Vertex;

import java.util.ArrayList;

public class VertexBasedOperations {

    public void rotaciona(ArrayList<Vertex> vertices, Vertex Central,double radians, int lado){
        if(lado==1) {
            double seno = Math.sin(radians);
            double cose = Math.cos(radians);
            double ante;
            for(Vertex v: vertices){
                ante=(v.x*cose)-(v.y*seno);
                v.y=(v.x*seno)+(v.y*cose);
                v.x=ante;
            }
            ante=(Central.x*cose)-(Central.y*seno);
            Central.y=(Central.x*seno)+(Central.y*cose);
            Central.x=ante;


        }else if(lado==2){
            double seno = Math.sin(radians);
            double cose = Math.cos(radians);
            double ante;
            for(Vertex v: vertices){
                ante=(v.z*cose)-(v.y*seno);
                v.y=(v.z*seno)+(v.y*cose);
                v.z=ante;
            }
            ante=(Central.z*cose)-(Central.y*seno);
            Central.y=(Central.z*seno)+(Central.y*cose);
            Central.z=ante;


        }else if(lado==3){
            double seno = Math.sin(radians);
            double cose = Math.cos(radians);
            double ante;
            for(Vertex v: vertices){
                ante=(v.x*cose)+(v.z*seno);
                v.z=(v.z*cose)-(v.x*seno);
                v.x=ante;
            }
            ante=(Central.x*cose)+(Central.z*seno);
            Central.z=(Central.z*cose)-(Central.x*seno);
            Central.x=ante;

        }
    }

    public void translada(ArrayList<Vertex> vertices, Vertex Central,Vertex V){
//        double x = V.x;//-this.Central.x;
//        double y =V.y;//-this.Central.y;
//        double z =V.z;//-this.Central.z;
        for(Vertex v: vertices){
            v.x+=V.x;
            v.y+=V.y;
            v.z+=V.z;
        }
        Central.x+=V.x;
        Central.y+=V.y;
        Central.z+=V.z;

    }

    public void scala(ArrayList<Vertex> vertices,Vertex Central,double novoTamanho,int eixo){
        if(eixo==1) {
            for (Vertex v : vertices) {
                v.x = v.x * novoTamanho;
            }
            Central.x = Central.x * novoTamanho;
        }else if(eixo==2){
            for(Vertex v : vertices){
                v.y=v.y*novoTamanho;
            }
            Central.y=Central.y*novoTamanho;
        }else if (eixo==3){
            for(Vertex v : vertices){
                v.z=v.z*novoTamanho;
            }
            Central.z=Central.z*novoTamanho;
        }
    }
}
