package Models;

import java.io.Serializable;

import static java.lang.StrictMath.sqrt;

public class Vertice implements Serializable {
    public double x;
    public double y;
    public double z;

    public Vertice(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertice() {
    }
    public double distancia(Vertice V){
        double resp = sqrt(Math.pow(V.x-this.x,2)+Math.pow(V.y-this.y,2));
        return resp;
    }

}
