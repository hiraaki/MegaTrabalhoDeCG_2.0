package Models;

import java.io.Serializable;

import static java.lang.StrictMath.sqrt;
/**
 * Class who represent a Vertex
 * @author Gabriela, Hamã, Mauricio
 */
public class Vertex implements Serializable {
    public double x;
    public double y;
    public double z;

    public Vertex(){}
    /**
     * Creates a Vertex
     * @param x The X of the Vertex
     * @param y The Y of the Vertex
     * @param z The Z of the Vertex
     */
    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Distance between two Vertexs
     * @param V Vertex to be compered
     * @return Distance
     */
    public double distancia(Vertex V){
        double resp = sqrt(Math.pow(V.x-this.x,2)+Math.pow(V.y-this.y,2));
        return resp;
    }

}
