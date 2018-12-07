package Controller;

import Models.Edge;
import Models.Polygon;
import Models.Polyhedron;
import Models.Vertex;

import static java.lang.Math.toRadians;
/**
 * Class responsible to implements the methods for polyhedrons.
 * @author Gabriela, Hamã, Mauricio.
 */
public class PolyhedronController {
    PolygonController polygonController;
    VertexBasedOperations vertexBasedOperations;

    public PolyhedronController() {
        this.polygonController = new PolygonController();
        vertexBasedOperations = new VertexBasedOperations();
    }

    /**
     * Creates a default polyhedron based on a polygon(Geratriz) or line, the size is defined by the distance of the object from the axis to revolute.
     * @param arevolucionar Base polygon or line.
     * @param particoes Number of sections in the polyhedron.
     * @param lado Plan of reference create the polygon.
     * @param Angulo angle limit to revolute
     * @return Polyhedron created
     */
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

    /**
     * Calculates the centroid of the Polyhedron based on the centroid of the polygons
     * @param polyhedron The polyhedron to calculate the centroid
     */
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
