/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import javax.swing.*;

import static java.lang.Math.*;


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

    private ArrayList<Poligono> polyline;

    private Poligono selected;

    private Poliedro poliselected;

    private Vertice clique;

    private ArrayList<Vertice> linhas;


    private ArrayList<Poliedro> poliedros;

    GraphicsContext gc1;
    GraphicsContext gc2;
    GraphicsContext gc3;
    GraphicsContext gc4;

    int lados, polylineAtiva, canvas;

    ObservableList<String> eixo = FXCollections.observableArrayList("X","Y","Z");

    @FXML
    private ChoiceBox<String> eixos;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.arrIrregular= new ArrayList<>();
        this.arrIrregular2= new ArrayList<>();
        this.verIrregular= new ArrayList<>();
        this.verIrregular2 = new ArrayList<>();
        this.poligonos = new ArrayList<>();


        this.polyline = new ArrayList<>();
        this.clique=new Vertice();

        this.clique=null;
        this.eixos= new ChoiceBox<>();

        this.linhas=new ArrayList<>();
        this.poliedros=new ArrayList<>();
        this.poliselected =null;
        this.selected =null;
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
        this.eixos.setItems(eixo);
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
        canvas2.setOnMouseClicked(this::Irregular);
        canvas3.setOnMouseClicked(this::Irregular);
        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }

    private void Irregular(MouseEvent e){


        if(e.getSource()==canvas1){
            if(verIrregular.size()>0){

                if(verIrregular.get(0).distancia(new Vertice(e.getX(),e.getY(),0))<5) {


                    this.arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0),null));
                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    //System.out.println("saiu!!!!");

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc1, 1);

                    this.poligonos.add(new Poligono(this.verIrregular,this.arrIrregular));

                    this.verIrregular=new ArrayList<>();
                    this.arrIrregular=new ArrayList<>();

                }else {

                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    verIrregular.add(new Vertice(e.getX(), e.getY(), 0));
                    arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 2),
                            this.verIrregular.get(this.verIrregular.size() - 1),
                            null));

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc1, 1);
                }
            }else {

                verIrregular.add(new Vertice(e.getX(), e.getY(), 0));

            }
        }else if(e.getSource()==canvas2){
            if(verIrregular.size()>0){

                if(verIrregular.get(0).distancia(new Vertice(0,e.getY(),e.getX()))<5) {


                    this.arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0),null));
                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    //System.out.println("saiu!!!!");

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc2, 2);

                    this.poligonos.add(new Poligono(this.verIrregular,this.arrIrregular));

                    this.verIrregular=new ArrayList<>();
                    this.arrIrregular=new ArrayList<>();

                }else {

                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    verIrregular.add(new Vertice(0, e.getY(), e.getX()));
                    arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 2),
                            this.verIrregular.get(this.verIrregular.size() - 1),
                            null));

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc2, 2);
                }
            }else {

                verIrregular.add(new Vertice(e.getX(), e.getY(), 0));

            }
        }else if(e.getSource()==canvas3){
            if(verIrregular.size()>0){

                if(verIrregular.get(0).distancia(new Vertice(e.getX(),0,e.getY()))<5) {


                    this.arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0),null));
                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    //System.out.println("saiu!!!!");

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc3, 3);

                    this.poligonos.add(new Poligono(this.verIrregular,this.arrIrregular));

                    this.verIrregular=new ArrayList<>();
                    this.arrIrregular=new ArrayList<>();

                }else {

                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    verIrregular.add(new Vertice(e.getX(), 0, e.getY()));
                    arrIrregular.add(new Aresta(this.verIrregular.get(this.verIrregular.size() - 2),
                            this.verIrregular.get(this.verIrregular.size() - 1),
                            null));

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc3, 3);
                }
            }else {

                verIrregular.add(new Vertice(e.getX(), 0, e.getY()));

            }
        }


    }

    public void botaoPoligonoR(){
        canvas1.setOnMouseClicked(this::Regular);
        canvas2.setOnMouseClicked(this::Regular);
        canvas3.setOnMouseClicked(this::Regular);
        String resposta = JOptionPane.showInputDialog(null, "Quantos lados possui o polígono?");
        lados = Integer.parseInt(resposta);
        System.out.println(lados);

        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }

    private void Regular(MouseEvent e){

        if(e.getSource()==canvas1){
            this.poligonos.add(new Poligono(new Vertice(e.getX(), e.getY(), 0), lados, null,1));
            this.poligonos.get(this.poligonos.size()-1).draw(gc1,1);
        }else if(e.getSource()==canvas2){
            this.poligonos.add(new Poligono(new Vertice(0, e.getY(), e.getX()), lados, null,3));
            this.poligonos.get(this.poligonos.size()-1).draw(gc2,2);
        }else if(e.getSource()==canvas3){
            this.poligonos.add(new Poligono(new Vertice(e.getX(), 0, e.getY()), lados, null,2));
            this.poligonos.get(this.poligonos.size()-1).draw(gc3,3);
        }
    }


    public void botaoPolyline(){
        canvas1.setOnMouseClicked(this::Polyline);
        canvas2.setOnMouseClicked(this::Polyline);
        canvas3.setOnMouseClicked(this::Polyline);
        if(arrIrregular2.size()>0||polylineAtiva>0){
            fechaPolilyne();
            polylineAtiva=0;
        }
    }

    private void Polyline(MouseEvent e){

        if(e.getSource()==canvas1){
            if(verIrregular2.size()>0){
                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                verIrregular2.add(new Vertice(e.getX(), e.getY(), 0));
                arrIrregular2.add(new Aresta(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1),
                        null));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc1, 1);
            }else {
                verIrregular2.add(new Vertice(e.getX(), e.getY(), 0));
                polylineAtiva=1;
            }
        }else if(e.getSource()==canvas2){
            if(verIrregular2.size()>0){
                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertice(0,e.getY(),e.getX())));
                verIrregular2.add(new Vertice(0, e.getY(), (e.getX())));
                arrIrregular2.add(new Aresta(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1),
                        null));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc2, 2);
            }else {
                verIrregular2.add(new Vertice(0, e.getY(), e.getX()));
                polylineAtiva=1;
            }

        }else if(e.getSource()==canvas3){
            if(verIrregular2.size()>0){
                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertice(e.getX(), 0,e.getY())));
                verIrregular2.add(new Vertice(e.getX(), 0, e.getY()));
                arrIrregular2.add(new Aresta(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1),
                        null));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc3, 3);
            }else {
                verIrregular2.add(new Vertice(e.getX(), 0, e.getY()));
                polylineAtiva=1;
            }
        }




    }

    public void botaoCirculo(){
        canvas1.setOnMouseClicked(this::Circulo1);
        canvas2.setOnMouseClicked(this::Circulo2);
        canvas3.setOnMouseClicked(this::Circulo3);

        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }

    private void Circulo1(MouseEvent e){
        this.poligonos.add(new Poligono(new Vertice(e.getX(), e.getY(), 0), 500, null,1));
        this.poligonos.get(this.poligonos.size()-1).draw(gc1,1);
    }

    private void Circulo2(MouseEvent e){
        this.poligonos.add(new Poligono(new Vertice(0, e.getY(), e.getX()), 500, null,3));
        this.poligonos.get(this.poligonos.size()-1).draw(gc2,2);
    }

    private void Circulo3(MouseEvent e){
        this.poligonos.add(new Poligono(new Vertice(e.getX(), 0, e.getY()), 500, null,2));
        this.poligonos.get(this.poligonos.size()-1).draw(gc3,3);
    }

    private void clear() {
        gc1.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
        gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        gc3.clearRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
        gc4.clearRect(0, 0, canvas4.getWidth(), canvas4.getHeight());
    }

    private void drawall(){
        clear();
        for(Poligono p:this.poligonos){
            if(p==this.selected){
                gc1.setStroke(Color.RED);
                gc2.setStroke(Color.RED);
                gc3.setStroke(Color.RED);
                gc1.setLineWidth(3.0);
                gc2.setLineWidth(3.0);
                gc3.setLineWidth(3.0);
                //System.out.println("selected");
                p.draw(gc1,1);
                p.draw(gc2,2);
                p.draw(gc3,3);
                gc1.setLineWidth(1.0);
                gc2.setLineWidth(1.0);
                gc3.setLineWidth(1.0);
                gc1.setStroke(Color.BLACK);
                gc2.setStroke(Color.BLACK);
                gc3.setStroke(Color.BLACK);
            }else {
                p.draw(gc1,1);
                p.draw(gc2,2);
                p.draw(gc3,3);
            }

        }
        for(Poligono p:this.polyline){
            if(p==this.selected){
                gc1.setStroke(Color.RED);
                gc2.setStroke(Color.RED);
                gc3.setStroke(Color.RED);
                gc1.setLineWidth(3.0);
                gc2.setLineWidth(3.0);
                gc3.setLineWidth(3.0);
                //System.out.println("selected");
                p.draw(gc1,1);
                p.draw(gc2,2);
                p.draw(gc3,3);
                gc1.setLineWidth(1.0);
                gc2.setLineWidth(1.0);
                gc3.setLineWidth(1.0);
                gc1.setStroke(Color.BLACK);
                gc2.setStroke(Color.BLACK);
                gc3.setStroke(Color.BLACK);
            }else {
                p.draw(gc1,1);
                p.draw(gc2,2);
                p.draw(gc3,3);
            }

        }
        for(Poliedro p:this.poliedros)
            if (p == this.poliselected) {
                gc1.setStroke(Color.RED);
                gc2.setStroke(Color.RED);
                gc3.setStroke(Color.RED);
                p.draw(gc1, 1);
                p.draw(gc2, 2);
                p.draw(gc3, 3);
                gc1.setStroke(Color.BLACK);
                gc2.setStroke(Color.BLACK);
                gc3.setStroke(Color.BLACK);
            } else {
                p.draw(gc1, 1);
                p.draw(gc2, 2);
                p.draw(gc3, 3);
            }

        desenhaRegua();
    }

    private void fechaPolilyne(){
        this.polyline.add(new Poligono(this.verIrregular2,this.arrIrregular2));
        this.polyline.get(this.polyline.size()-1).calcCentroid();
        this.verIrregular2=new ArrayList<>();
        this.arrIrregular2=new ArrayList<>();
    }

    private void desenhaRegua(){
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

    public void buttonselect(){
        canvas1.setOnMouseClicked(this::select);
        canvas2.setOnMouseClicked(this::select);
        canvas3.setOnMouseClicked(this::select);
        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }

    private void select(MouseEvent e){
        Vertice v = new Vertice();
        boolean foundpolig=false;
        boolean foundpolie=false;
        boolean foundpoly=false;
        int lado=0;
        if(e.getSource()==canvas1){
            System.out.println("canvas1");
            v = new Vertice(e.getX(),e.getY(),0);
            lado=1;
        }else if(e.getSource()==canvas2){
            System.out.println("canvas2");
            v = new Vertice(0,e.getY(),e.getX());
            lado=2;
        }else if(e.getSource()==canvas3){
            System.out.println("canvas3");
            v = new Vertice(e.getX(),0,e.getY());
            lado=3;
        }
       //System.out.print("F "+lado);
        for (Poliedro poli : this.poliedros) {
            for (Poligono p : poli.faces) {
                for (Aresta a : p.arestas) {
                    //System.out.println("ds");
                    //System.out.println(p.isselected(v, lados));
                    //System.out.print("F "+lado);
                    if (p.isselected(v, lado)) {
                        this.poliselected = poli;
                        foundpolie = true;
                        ///System.out.println(this.poliedros.indexOf(this.poliselected));
                        break;

                    }
                }
            }
            if (foundpolie) {
                if(selected!=null) {
                    this.selected = null;
                }
                break;
            }
        }

        for (Poligono p : this.poligonos) {
            for (Aresta a : p.arestas) {
                if (a.selected(v, lado)) {
                    this.selected = a.pai;
                    foundpolig = true;
                    break;
                }
            }
            if (foundpolig) {
                if(poliselected!=null){
                    this.poliselected=null;
                }
                break;
            }

        }
        for (Poligono p : this.polyline) {
            for (Aresta a : p.arestas) {
                if (a.selected(v, lado)) {
                    this.selected = a.pai;
                    foundpolig = true;
                    break;
                }
            }
            if (foundpolig) {
                if(poliselected!=null){
                    this.poliselected=null;
                }
                break;
            }
        }
        if(!foundpolie&&foundpolig){
            this.poliselected=null;
        }else if(foundpolie&&!foundpolig){
            this.selected=null;
        }else if(!foundpolie&&!foundpolig){
            this.selected=null;
            this.poliselected=null;
        }
        drawall();
    }
    public void buuttonDelete(){
        if(selected!=null) {
            this.poligonos.remove(selected);
            this.polyline.remove(selected);
            this.selected=null;
        }else if(poliselected!=null) {
            this.poliedros.remove(poliselected);
            this.poliselected=null;

        }
        drawall();
    }
    public void buttonRotaciona(){
        canvas1.setOnMousePressed(this::setClick);
        canvas1.setOnMouseDragged(this::rotaciona);
        canvas1.setOnMouseReleased(this::clearCanvasSet);

        canvas2.setOnMousePressed(this::setClick);
        canvas2.setOnMouseDragged(this::rotaciona);
        canvas2.setOnMouseReleased(this::clearCanvasSet);

        canvas3.setOnMousePressed(this::setClick);
        canvas3.setOnMouseDragged(this::rotaciona);
        canvas3.setOnMouseReleased(this::clearCanvasSet);

        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }
    public void clearCanvasSet(MouseEvent e){
        canvas1.setOnMousePressed(null);
        canvas1.setOnMouseClicked(null);
        canvas1.setOnMouseDragEntered(null);
        canvas1.setOnMouseReleased(null);
        canvas2.setOnMousePressed(null);
        canvas2.setOnMouseClicked(null);
        canvas2.setOnMouseDragEntered(null);
        canvas2.setOnMouseReleased(null);
        canvas3.setOnMousePressed(null);
        canvas3.setOnMouseClicked(null);
        canvas3.setOnMouseDragEntered(null);
        canvas3.setOnMouseReleased(null);
        this.clique=null;
    }
    private void setClick(MouseEvent e){
        this.clique=new Vertice(e.getX(),e.getY(),0);
    }


    private void rotaciona(MouseEvent e){
        if(clique!=null){
            if((e.getX()!=this.clique.x)||(e.getY()!=clique.y)) {
                if(clique!=null) {
                    if((e.getX()!=this.clique.x)||(e.getY()!=clique.y)) {
                        if (e.getSource() == canvas1) {
                            if (selected != null) {
                                double x=selected.Central.x;
                                double y=selected.Central.y;
                                double z=selected.Central.z;

                                selected.rotaciona((e.getX() - clique.x)*0.005,3);
                                selected.rotaciona((e.getY() - clique.y)*0.005,2);

                                selected.translada(new Vertice(
                                        x-selected.Central.x,
                                        y-selected.Central.y,
                                        z-selected.Central.z
                                ));

                                selected.Central.x=x;
                                selected.Central.y=y;
                                selected.Central.z=z;
                            } else if (this.poliselected != null) {
                                //poliselected.translada(new Vertice(e.getX() - clique.x, e.getY() - clique.y, 0));
                                double x=this.poliselected.Central.x;
                                double y=this.poliselected.Central.y;
                                double z=this.poliselected.Central.z;

                                this.poliselected.rotaciona((e.getX() - clique.x)*0.005,3);
                                this.poliselected.rotaciona((e.getY() - clique.y)*0.005,2);

                                this.poliselected.translada(new Vertice(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));

                                this.poliselected.Central.x=x;
                                this.poliselected.Central.y=y;
                                this.poliselected.Central.z=z;
                            }
                        } else if (e.getSource() == canvas2) {
                            if (selected != null) {

                                double x=selected.Central.x;
                                double y=selected.Central.y;
                                double z=selected.Central.z;

                                selected.rotaciona((e.getX() - clique.x)*0.005,3);
                                selected.rotaciona((e.getY() - clique.y)*0.005,1);

                                selected.translada(new Vertice(
                                        x-selected.Central.x,
                                        y-selected.Central.y,
                                        z-selected.Central.z
                                ));

                                selected.Central.x=x;
                                selected.Central.y=y;
                                selected.Central.z=z;

                            } else if (poliselected != null) {
                                double x=this.poliselected.Central.x;
                                double y=this.poliselected.Central.y;
                                double z=this.poliselected.Central.z;

                                this.poliselected.rotaciona((e.getX() - clique.x)*0.005,3);
                                this.poliselected.rotaciona((e.getY() - clique.y)*0.005,1);

                                this.poliselected.translada(new Vertice(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));

                                this.poliselected.Central.x=x;
                                this.poliselected.Central.y=y;
                                this.poliselected.Central.z=z;
                            }
                        } else if (e.getSource() == canvas3) {
                            if (selected != null) {

                                double x=selected.Central.x;
                                double y=selected.Central.y;
                                double z=selected.Central.z;

                                selected.rotaciona((e.getX() - clique.x)*0.005,1);
                                selected.rotaciona((e.getY() - clique.y)*0.005,2);

                                selected.translada(new Vertice(
                                        x-selected.Central.x,
                                        y-selected.Central.y,
                                        z-selected.Central.z
                                ));

                                selected.Central.x=x;
                                selected.Central.y=y;
                                selected.Central.z=z;

                            } else if (poliselected != null) {
                                double x=this.poliselected.Central.x;
                                double y=this.poliselected.Central.y;
                                double z=this.poliselected.Central.z;

                                this.poliselected.rotaciona((e.getX() - clique.x)*0.005,1);
                                this.poliselected.rotaciona((e.getY() - clique.y)*0.005,2);

                                this.poliselected.translada(new Vertice(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));

                                this.poliselected.Central.x=x;
                                this.poliselected.Central.y=y;
                                this.poliselected.Central.z=z;
                            }
                        }
                        clique.x = e.getX();
                        clique.y = e.getY();
                        drawall();
                    }
                }
            }
            this.clique.x=e.getX();
            this.clique.y=e.getY();
        }
    }
    public void buttontranslada(){
        canvas1.setOnMousePressed(this::setClick);
        canvas1.setOnMouseDragged(this::translada);
        canvas1.setOnMouseReleased(this::clearCanvasSet);

        canvas2.setOnMousePressed(this::setClick);
        canvas2.setOnMouseDragged(this::translada);
        canvas2.setOnMouseReleased(this::clearCanvasSet);

        canvas3.setOnMousePressed(this::setClick);
        canvas3.setOnMouseDragged(this::translada);
        canvas3.setOnMouseReleased(this::clearCanvasSet);
        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }
    private void translada(MouseEvent e){
        if(clique!=null) {
            if((e.getX()!=this.clique.x)||(e.getY()!=clique.y)) {
                if (e.getSource() == canvas1) {
                    if (selected != null) {
                        selected.translada(new Vertice(e.getX() - clique.x, e.getY() - clique.y, 0));
//                        selected.Central.x+=e.getX() - clique.x;
//                        selected.Central.y+=e.getY() - clique.y;

                    } else if (poliselected != null) {
                        poliselected.translada(new Vertice(e.getX() - clique.x, e.getY() - clique.y, 0));
                    }
                } else if (e.getSource() == canvas2) {
                    if (selected != null) {
                        selected.translada(new Vertice(0, e.getY() - clique.y, e.getX() - clique.x));
//                        selected.Central.z+=e.getX() - clique.x;
//                        selected.Central.y+=e.getY() - clique.y;

                    } else if (poliselected != null) {
                        poliselected.translada(new Vertice(0, e.getY() - clique.y, e.getX() - clique.x));
                    }
                } else if (e.getSource() == canvas3) {
                    if (selected != null) {
                        selected.translada(new Vertice(e.getX() - clique.x, 0, e.getY() - clique.y));
//                        selected.Central.x+=e.getX() - clique.x;
//                        selected.Central.z+=e.getY() - clique.y;

                    } else if (poliselected != null) {
                        poliselected.translada(new Vertice(e.getX() - clique.x, 0, e.getY() - clique.y));
                    }
                }
                clique.x = e.getX();
                clique.y = e.getY();
                drawall();
            }
        }
    }

    public void buttonRevoluciona(){
        if(selected!=null){
//            JTextField segmentos = new JTextField();
//            JTextField eixo = new JTextField();
//            JTextField angulo = new JTextField();
//            Object[] campos = {
//                "Segmentos", segmentos,
//                "Eixo", eixo,
//                "Angulo", angulo
//            };
//
//            JOptionPane.showConfirmDialog(null, campos);
            //1 - z, 2 - x, 3 - y
            System.out.println(toRadians(360));
            this.poliedros.add(new Poliedro(selected,100,3,180));
            this.poligonos.remove(selected);
            this.polyline .remove(selected);
            selected=null;
            drawall();
        }
    }

    public void ctest(){
        this.poligonos.add(new Poligono(new Vertice(300,150,0),3,null,1));
        drawall();
    }
    public void rvDebug(){
        selected.calcCentroid();
        selected.rotaciona(0.1,2);
        drawall();
    }
    public void trDebug(){
        double x=selected.Central.x;
        double y=selected.Central.y;
        double z=selected.Central.z;
        System.out.println(x+" "+y+" "+z);


        double seno = Math.sin(0.1);
        double cose = Math.cos(0.1);
        double ante=0;
        ante=(selected.Central.z*cose)-(selected.Central.y*seno);
        selected.Central.y=(selected.Central.z*seno)+(selected.Central.y*cose);
        selected.Central.z=ante;

        //selected.calcCentroid();
        selected.translada(new Vertice(
        x-selected.Central.x,
        y-selected.Central.y,
        z-selected.Central.z
        ));
        selected.Central.x=x;
        selected.Central.y=y;
        selected.Central.z=z;

        drawall();
    }

    public void buttonSave(){
        FileChooser chooser = new FileChooser();
        FileChooser filter = new FileChooser();
        FileChooser.ExtensionFilter extFiler = new FileChooser.ExtensionFilter("POLIGON Files (*.out)", "*.out");
        chooser.getExtensionFilters().add(extFiler);
        chooser.setTitle("Salvar Cena");
        String savef = chooser.showSaveDialog(canvas1.getScene().getWindow()).toString();
        save(savef);

    }

    public void save(String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(this.poligonos);
            oos.writeObject(this.poliedros);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonLoad() {
        FileChooser chooser = new FileChooser();
        FileChooser filter = new FileChooser();
        FileChooser.ExtensionFilter extFiler = new FileChooser.ExtensionFilter("POLIGON Files (*.out)", "*.out");
        chooser.getExtensionFilters().add(extFiler);
        chooser.setTitle("Abrir Cena");
        String openf = chooser.showOpenDialog(canvas1.getScene().getWindow()).toString();
        load(openf);
    }

    public void load(String fileName) {
        try {
            FileInputStream in = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(in);
            this.poligonos = (ArrayList<Poligono>) (ois.readObject());
            this.poliedros = (ArrayList<Poliedro>) (ois.readObject());
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
        System.out.println("este é o arquivo");
        clear();
        drawall();
        //gc1.clearRect(0, 0, drawingArea1.getWidth(), drawingArea1.getHeight());
        // this.drawall();
        //return null;
    }
    public void buttonScala(){
        canvas1.setOnMousePressed(this::setClick);
        canvas1.setOnMouseDragged(this::scala);
        canvas1.setOnMouseReleased(this::clearCanvasSet);

        canvas2.setOnMousePressed(this::setClick);
        canvas2.setOnMouseDragged(this::scala);
        canvas2.setOnMouseReleased(this::clearCanvasSet);

        canvas3.setOnMousePressed(this::setClick);
        canvas3.setOnMouseDragged(this::scala);
        canvas3.setOnMouseReleased(this::clearCanvasSet);

        if(arrIrregular2.size()>0){
            fechaPolilyne();
        }
    }
    private void scala(MouseEvent e){
        if(clique!=null){
            if((e.getX()!=this.clique.x)||(e.getY()!=clique.y)) {
                if(clique!=null) {
                    if((e.getX()!=this.clique.x)||(e.getY()!=clique.y)) {
                        if (e.getSource() == canvas1) {
                            if (selected != null) {
                                double x=selected.Central.x;
                                double y=selected.Central.y;
                                double z=selected.Central.z;

                                if((e.getX() - clique.x)>0){
                                    selected.scala(1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    selected.scala(0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    selected.scala(1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    selected.scala(0.99,2);
                                }
                                selected.translada(new Vertice(
                                        x-selected.Central.x,
                                        y-selected.Central.y,
                                        z-selected.Central.z
                                ));

                                selected.Central.x=x;
                                selected.Central.y=y;
                                selected.Central.z=z;
                            } else if (this.poliselected != null) {
                                //poliselected.translada(new Vertice(e.getX() - clique.x, e.getY() - clique.y, 0));
                                double x=this.poliselected.Central.x;
                                double y=this.poliselected.Central.y;
                                double z=this.poliselected.Central.z;

                                if((e.getX() - clique.x)>0){
                                    this.poliselected.scala(1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    this.poliselected.scala(0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    this.poliselected.scala(1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    this.poliselected.scala(0.99,2);
                                }

                                this.poliselected.translada(new Vertice(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));

                                this.poliselected.Central.x=x;
                                this.poliselected.Central.y=y;
                                this.poliselected.Central.z=z;
                            }
                        } else if (e.getSource() == canvas2) {
                            if (selected != null) {

                                double x=selected.Central.x;
                                double y=selected.Central.y;
                                double z=selected.Central.z;

                                if((e.getX() - clique.x)>0){
                                    selected.scala(1.01,3);
                                }else if((e.getX() - clique.x)<0){
                                    selected.scala(0.99,3);
                                }
                                if ((e.getY() - clique.y)<0){
                                    selected.scala(1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    selected.scala(0.99,2);
                                }

                                selected.translada(new Vertice(
                                        x-selected.Central.x,
                                        y-selected.Central.y,
                                        z-selected.Central.z
                                ));

                                selected.Central.x=x;
                                selected.Central.y=y;
                                selected.Central.z=z;

                            } else if (poliselected != null) {
                                double x=this.poliselected.Central.x;
                                double y=this.poliselected.Central.y;
                                double z=this.poliselected.Central.z;

                                if((e.getX() - clique.x)>0){
                                    this.poliselected.scala(1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    this.poliselected.scala(0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    this.poliselected.scala(1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    this.poliselected.scala(0.99,2);
                                }

                                this.poliselected.translada(new Vertice(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));

                                this.poliselected.Central.x=x;
                                this.poliselected.Central.y=y;
                                this.poliselected.Central.z=z;
                            }
                        } else if (e.getSource() == canvas3) {
                            if (selected != null) {

                                double x=selected.Central.x;
                                double y=selected.Central.y;
                                double z=selected.Central.z;

                                selected.rotaciona((e.getX() - clique.x)*0.005,1);
                                selected.rotaciona((e.getY() - clique.y)*0.005,3);

                                if((e.getX() - clique.x)>0){
                                    selected.scala(1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    selected.scala(0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    selected.scala(1.01,3);
                                }else if((e.getY() - clique.y)>0){
                                    selected.scala(0.99,3);
                                }

                                selected.translada(new Vertice(
                                        x-selected.Central.x,
                                        y-selected.Central.y,
                                        z-selected.Central.z
                                ));

                                selected.Central.x=x;
                                selected.Central.y=y;
                                selected.Central.z=z;

                            } else if (poliselected != null) {
                                double x=this.poliselected.Central.x;
                                double y=this.poliselected.Central.y;
                                double z=this.poliselected.Central.z;

                                if((e.getX() - clique.x)>0){
                                    this.poliselected.scala(1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    this.poliselected.scala(0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    this.poliselected.scala(1.01,3);
                                }else if((e.getY() - clique.y)>0){
                                    this.poliselected.scala(0.99,3);
                                }

                                this.poliselected.translada(new Vertice(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));

                                this.poliselected.Central.x=x;
                                this.poliselected.Central.y=y;
                                this.poliselected.Central.z=z;
                            }
                        }
                        clique.x = e.getX();
                        clique.y = e.getY();
                        drawall();
                    }
                }
            }
            this.clique.x=e.getX();
            this.clique.y=e.getY();
        }
    }

}
