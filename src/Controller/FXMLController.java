/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Models.*;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;

import static javafx.scene.text.FontWeight.findByWeight;

/**
 * FXML Controller class
 *
 * @author gabriela
 */
public class FXMLController implements Initializable {

    @FXML
    private ResizableCanvas canvas1;
    
    @FXML
    private ResizableCanvas canvas2;
    
    @FXML
    private ResizableCanvas canvas3;
    
    @FXML
    private ResizableCanvas canvas4;
    
//    @FXML
//    private Button botaoSeleciona;
//
//    @FXML
//    private Button botaoExclui;
//
//    @FXML
//    private Button botaoPonto;
//
//    @FXML
//    private Button botaoCirculo;
//
//    @FXML
//    private Button botaoReta;
//
//    @FXML
//    private Button botaoPoligonoR;

    private int cliques;

    private ArrayList<Vertice> verIrregular;

    private ArrayList<Vertice> verIrregular2;

    private ArrayList<Aresta> arrIrregular;

    private ArrayList<Aresta> arrIrregular2;

    private ArrayList<Poligono> poligonos;

    private Vertice clique;


    //mantenha setando a bosta até ele clicar denovo na porra do botão ou em qualquer outro
    private ArrayList<Vertice> linhas;


    private ArrayList<Poliedro> poliedros;

    GraphicsContext gc1;
    GraphicsContext gc2;
    GraphicsContext gc3;
    GraphicsContext gc4;

    int lados, polylineAtiva, canvas;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.arrIrregular= new ArrayList<>();
        this.arrIrregular2= new ArrayList<>();
        this.verIrregular= new ArrayList<>();
        this.verIrregular2 = new ArrayList<>();
        this.poligonos = new ArrayList<>();
        this.clique=new Vertice();
        this.linhas=new ArrayList<>();
        this.poliedros=new ArrayList<>();
        canvas1.widthProperty().addListener(evt -> drawall());
        canvas1.heightProperty().addListener(evt -> drawall());
        canvas2.widthProperty().addListener(evt -> drawall());
        canvas2.heightProperty().addListener(evt -> drawall());
        canvas3.widthProperty().addListener(evt -> drawall());
        canvas3.heightProperty().addListener(evt -> drawall());
        canvas4.widthProperty().addListener(evt -> drawall());
        canvas4.heightProperty().addListener(evt -> drawall());
        gc1= canvas1.getGraphicsContext2D();
        gc2= canvas2.getGraphicsContext2D();
        gc3= canvas3.getGraphicsContext2D();
        gc4= canvas4.getGraphicsContext2D();
        this.cliques=0;
        desenhaRegua();
    }

    //SAI X APENAS PARA DEBUG
    public void sX(){
        double width = canvas1.getWidth();
        double height = canvas1.getHeight();

        GraphicsContext gc = gc1;
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.RED);
        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, height, width, 0);

    }

    //FAZ O POLIGONO IRREGULAR
    public void ButtonPonto(){

        canvas1.setOnMouseClicked(this::Irregular);
        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }

    public void Irregular(MouseEvent e){


        if(verIrregular.size()>0){

            if(verIrregular.get(0).distancia(new Vertice(e.getX(),e.getY(),0))<5) {


                this.arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0)));
                System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                System.out.println("saiu!!!!");

                this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc1, 1);

                this.poligonos.add(new Poligono(this.verIrregular,this.arrIrregular));

                this.verIrregular=new ArrayList<>();
                this.arrIrregular=new ArrayList<>();

            }else {

                System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                verIrregular.add(new Vertice(e.getX(), e.getY(), 0));
                arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 2),
                        this.verIrregular.get(this.verIrregular.size() - 1)));

                this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc1, 1);
            }
        }else {

            verIrregular.add(new Vertice(e.getX(), e.getY(), 0));


        }

    }

    public void botaoPoligonoR(){
        canvas1.setOnMouseClicked(this::RegularXY);
        canvas2.setOnMouseClicked(this::RegularYZ);
        canvas3.setOnMouseClicked(this::RegularZX);
        String resposta = JOptionPane.showInputDialog(null, "Quantos lados possui o polígono?");

        lados = Integer.parseInt(resposta);
        System.out.println(lados);

        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }

    public void RegularXY(MouseEvent e){
        System.out.println("MOUSE X="+e.getX()+"Y="+e.getY());
        this.poligonos.add(new Poligono(new Vertice(e.getX(), e.getY(), 0), lados, null,1));
        drawall();
    }

    public void RegularYZ(MouseEvent e){
        System.out.println("MOUSE X="+e.getX()+"Y="+e.getY());
        this.poligonos.add(new Poligono(new Vertice(0, e.getY(), e.getX()), lados, null,3));
        drawall();
    }

    public void RegularZX(MouseEvent e){
        System.out.println("MOUSE X="+e.getX()+"Y="+e.getY());
        this.poligonos.add(new Poligono(new Vertice(e.getX(), 0, e.getY()), lados, null,2));
        drawall();
    }

    public void botaoPolyline(){
        canvas1.setOnMouseClicked(this::Polyline);
        if(arrIrregular2.size()>0||polylineAtiva>0){
            fechaPolilyne();
            polylineAtiva=0;
        }
    }

    public void Polyline(MouseEvent e){

        if(verIrregular2.size()>0){

//            if(verIrregular2.get(0).distancia(new Vertice(e.getX(),e.getY(),0))<5) {
//
//
//                this.arrIrregular2.add(new Aresta(this.verIrregular2.get(this.verIrregular2.size() - 1), this.verIrregular2.get(0)));
//                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
//                System.out.println("saiu!!!!");
//
//                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc1, 1);
//
//                this.poligonos.add(new Poligono(this.verIrregular2,this.arrIrregular2));
//
//                this.verIrregular2=new ArrayList<>();
//                this.arrIrregular2=new ArrayList<>();
//
//            }else {

                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                verIrregular2.add(new Vertice(e.getX(), e.getY(), 0));
                arrIrregular2.add(new Aresta(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1)));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc1, 1);
            //}
        }else {
            verIrregular2.add(new Vertice(e.getX(), e.getY(), 0));
            polylineAtiva=1;
        }


    }

    public void drawall(){
//        for(Aresta a: this.arrIrregular){
//            a.draw(gc1,1);
//        }
        gc1.clearRect(0,0,canvas1.getWidth(),canvas1.getHeight());
        gc2.clearRect(0,0,canvas2.getWidth(),canvas2.getHeight());
        gc3.clearRect(0,0,canvas3.getWidth(),canvas3.getHeight());
        gc4.clearRect(0,0,canvas4.getWidth(),canvas4.getHeight());

        for(Poligono p:this.poligonos){
            p.drawXY(gc1,1);
            p.drawXY(gc2,2);
            p.drawXY(gc3,3);

        }

        desenhaRegua();
    }

    public void fechaPolilyne(){
        this.poligonos.add(new Poligono(this.verIrregular2,this.arrIrregular2));
        this.verIrregular2=new ArrayList<>();
        this.arrIrregular2=new ArrayList<>();
    }

    public void desenhaRegua(){
        Font f = new Font("Verdana",9);
        gc1.setFont(f);
        gc2.setFont(f);
        gc3.setFont(f);
        gc4.setFont(f);
        for (int i=0;i<=canvas1.getHeight();i+=10){
            gc1.strokeLine(0,i,6,i);
            if(i%50==0){
                gc1.strokeText(String.valueOf(i),7,i);
            }

        }

        for (int i=0;i<=canvas1.getWidth();i+=10){
            gc1.strokeLine(i,0,i,6);
            if(i%50==0){
                gc1.strokeText("\n"+String.valueOf(i), i,7);
            }

        }

        for (int i=0;i<=canvas2.getHeight();i+=10){
            gc2.strokeLine(0,i,6,i);
            if(i%50==0){
                gc2.strokeText(String.valueOf(i),7,i);
            }

        }

        for (int i=0;i<=canvas2.getWidth();i+=10){
            gc2.strokeLine(i,0,i,6);
            if(i%50==0){
                gc2.strokeText("\n"+String.valueOf(i), i,7);
            }

        }

        for (int i=0;i<=canvas3.getHeight();i+=10){
            gc3.strokeLine(0,i,6,i);
            if(i%50==0){
                gc3.strokeText(String.valueOf(i),7,i);
            }

        }

        for (int i=0;i<=canvas3.getWidth();i+=10){
            gc3.strokeLine(i,0,i,6);
            if(i%50==0){
                gc3.strokeText("\n"+String.valueOf(i), i,7);
            }

        }


    }
}
