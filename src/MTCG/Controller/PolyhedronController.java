package Controller;

import Models.Edge;
import Models.Polygon;
import Models.Polyhedron;
import Models.Vertex;

import static java.lang.Math.toRadians;

public class PolyhedronController {
    PolygonController polygonController;
    VertexBasedOperations vertexBasedOperations;

    public PolyhedronController() {
        this.polygonController = new PolygonController();
        vertexBasedOperations = new VertexBasedOperations();
    }

    public Polyhedron criateNewPolyhedronRegular(Polygon arevolucionar, int particoes, int lado, double Angulo) {
        Polyhedron polyhedron=new Polyhedron();
        double angulo = toRadians(Angulo)/particoes;
        double anguloAtual = angulo;
        double anguloNovo = 0;
        Polygon atual = new Polygon();
        Polygon novo;
        atual = polygonController.copyIn(arevolucionar);

        for (int n=0;n<particoes;n++) {
            anguloNovo+=angulo;
            novo = new Polygon();
            novo = polygonController.copyIn(arevolucionar);
            vertexBasedOperations.rotaciona(novo.vertices,novo.Central,anguloNovo,lado);
            //System.out.println(Math.toDegrees(anguloNovo));

            for (int i = 0; i < arevolucionar.edges.size(); i++) {
                Polygon pface = new Polygon();

                pface.edges.add(atual.edges.get(i));
                pface.edges.add(new Edge(atual.edges.get(i).start, novo.edges.get(i).start, pface));
                pface.edges.add(novo.edges.get(i));
                pface.edges.add(new Edge(atual.edges.get(i).end, novo.edges.get(i).end, pface));
                pface.vertices.add(atual.edges.get(i).start);
                pface.vertices.add(atual.edges.get(i).end);
                pface.vertices.add(novo.edges.get(i).end);
                pface.vertices.add(novo.edges.get(i).start);

                Polygon face = new Polygon();
                face = polygonController.copyIn(pface);
                face.Central=polygonController.calcCentroid(face.vertices);
                polyhedron.faces.add(face);
                polyhedron.vertices.addAll(face.vertices);
            }
            anguloAtual=anguloNovo;
            atual = new Polygon();
            atual = polygonController.copyIn(arevolucionar);
            vertexBasedOperations.rotaciona(atual.vertices,atual.Central,anguloAtual,lado);
        }
        polyhedron.Central=new Vertex();
        polyhedron.Central = polygonController.calcCentroid(polyhedron.vertices);
        return polyhedron;
    }

    public void calcCentroid(Polyhedron polyhedron){
        double maiorX=Double.MIN_VALUE, maiorY=Double.MIN_VALUE, maiorZ=Double.MIN_VALUE;
        double menorX=Double.MAX_VALUE, menorY=Double.MAX_VALUE, menorZ=Double.MAX_VALUE;
        for(Polygon p:polyhedron.faces){

            if(p.Central.x>maiorX) maiorX=p.Central.x;

            if(p.Central.x<menorX) menorX=p.Central.x;

            if(p.Central.y>maiorY) maiorY=p.Central.y;

            if(p.Central.y<menorY) menorY=p.Central.y;

            if(p.Central.z>maiorZ) maiorZ=p.Central.z;

            if(p.Central.z<menorZ) menorZ=p.Central.z;
        }
        polyhedron.Central=new Vertex(menorX+((maiorX-menorX)/2),menorY+((maiorY-menorY)/2),menorZ+((maiorZ-menorZ)/2));
    }
}
