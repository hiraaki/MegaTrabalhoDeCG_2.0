package Controller;

import Models.Edge;
import Models.Polygon;
import Models.Vertex;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PolygonController {

    public Vertex calcCentroid(ArrayList<Vertex> vertices){
        double maiorX=Double.MIN_VALUE, maiorY=Double.MIN_VALUE, maiorZ=Double.MIN_VALUE;
        double menorX=Double.MAX_VALUE, menorY=Double.MAX_VALUE, menorZ=Double.MAX_VALUE;

        for(Vertex v:vertices){
            if(v.x>maiorX) maiorX=v.x;

            if(v.x<menorX) menorX=v.x;

            if(v.y>maiorY) maiorY=v.y;

            if(v.y<menorY) menorY=v.y;

            if(v.z>maiorZ) maiorZ=v.z;

            if(v.z<menorZ) menorZ=v.z;
        }
        return (new Vertex(menorX+((maiorX-menorX)/2),menorY+((maiorY-menorY)/2),menorZ+((maiorZ-menorZ)/2)));
    }

    public void setEdges(Polygon polygon){
        for (int i = 0; i < polygon.vertices.size(); i++) {
            if(i!=polygon.vertices.size()-1){
                polygon.edges.add(new Edge(polygon.vertices.get(i),polygon.vertices.get(i+1),polygon));
            }else if(i==polygon.vertices.size()-1){
                polygon.edges.add(new Edge(polygon.vertices.get(i),polygon.vertices.get(0),polygon));
            }
        }
    }

    public Polygon createRegularPolygon(Vertex ini, int axis, Color cor, int plano){ //planos 1 - XY / 2 - XZ / 3 - YZ
        Polygon polygon = new Polygon();
        polygon.Central= ini;
        Vertex V=ini;
        double xtemp;
        double ytemp;
        double grau = (360/axis);
        double R=60;
        for(int i=0;i<axis;i++){
            if(plano == 1){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / axis + grau) + V.x);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / axis + grau) + V.y);
                polygon.vertices.add(new Vertex(xtemp,ytemp, 0));
            }else if(plano == 2){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / axis + grau) + V.x);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / axis + grau) + V.z);
                polygon.vertices.add(new Vertex(xtemp,0, ytemp));
            } else if(plano ==3){
                xtemp = (R * Math.cos((2 * Math.PI * (i)) / axis + grau) + V.z);
                ytemp = (R * Math.sin((2 * Math.PI * (i)) / axis + grau) + V.y);
                polygon.vertices.add(new Vertex(0,ytemp, xtemp));
            }
        }
        this.setEdges(polygon);
        polygon.Central = calcCentroid(polygon.vertices);
        return polygon;
    }

    public Polygon copyIn(Polygon polygon){
//        ArrayList<Vertex> vertices
        Polygon polygon_ = new Polygon();
        polygon_.Central.x=polygon.Central.x;
        polygon_.Central.y=polygon.Central.y;
        polygon_.Central.z=polygon.Central.z;
        for(Vertex v:polygon.vertices){
            polygon_.vertices.add(new Vertex(v.x,v.y,v.z));
        }
        this.setEdges(polygon_);
        return polygon_;
    }

}
