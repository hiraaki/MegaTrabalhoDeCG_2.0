package Models;

import javafx.scene.canvas.GraphicsContext;

import static java.lang.Math.abs;
import static java.lang.StrictMath.sqrt;

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
    public void draw(GraphicsContext gc, int lado){
        if(lado==1)
            gc.strokeLine(this.ini.x,this.ini.y,this.fim.x,this.fim.y);
        else if(lado==3){
            gc.strokeLine(this.ini.x,this.ini.z,this.fim.x,this.fim.z);
        }else if(lado==2){
            gc.strokeLine(this.ini.z,this.ini.y,this.fim.z,this.fim.y);
        }
    }
    public double DistanceFromLine(Vertice v){
        double ax, ay, bx, by, cx, cy;
        ax=this.ini.x;
        ay=this.ini.y;
        bx=this.fim.x;
        by=this.fim.y;
        cx=v.x;
        cy=v.y;
        double distanceSegment,distanceLine;
        double r_numerator = (cx-ax)*(bx-ax) + (cy-ay)*(by-ay);
        double r_denomenator = (bx-ax)*(bx-ax) + (by-ay)*(by-ay);
        double r = r_numerator / r_denomenator;

        double px = ax + r*(bx-ax);
        double py = ay + r*(by-ay);

        double s =  ((ay-cy)*(bx-ax)-(ax-cx)*(by-ay) ) / r_denomenator;

        distanceLine = abs(s)*sqrt(r_denomenator);

        double xx = px;
        double yy = py;

        if ( (r >= 0) && (r <= 1) )
        {
            distanceSegment = distanceLine;
        }
        else
        {

            double dist1 = (cx-ax)*(cx-ax) + (cy-ay)*(cy-ay);
            double dist2 = (cx-bx)*(cx-bx) + (cy-by)*(cy-by);
            if (dist1 < dist2)
            {
                xx = ax;
                yy = ay;
                distanceSegment = sqrt(dist1);
            }
            else
            {
                xx = bx;
                yy = by;
                distanceSegment = sqrt(dist2);
            }


        }

        return distanceSegment;
    }
}
