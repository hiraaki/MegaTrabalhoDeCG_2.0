/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javax.swing.*;

import static java.lang.Math.*;


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

    private ArrayList<Vertex> verIrregular;

    private ArrayList<Vertex> verIrregular2;

    private ArrayList<Edge> arrIrregular;

    private ArrayList<Edge> arrIrregular2;

    private ArrayList<Polygon> poligonos;

    private ArrayList<Polygon> polylines;

    private Polygon selected;

    private Polyhedron poliselected;

    private Vertex clique;

    private ArrayList<Vertex> linhas;

    private ArrayList<Polyhedron> poliedros;

    GraphicsContext gc1;
    GraphicsContext gc2;
    GraphicsContext gc3;
    GraphicsContext gc4;

    int lados, polylineAtiva, canvas;

    private PolygonController polygonController;

    private PolyhedronController polyhedronController;

    private VertexBasedOperations vertexBasedOperations;

    @FXML
    private ChoiceBox<String> comboBox;

    ObservableList<String> observableList;

    @FXML
    private TextField textAngulo;

    @FXML
    private TextField textSeg;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.arrIrregular= new ArrayList<>();
        this.arrIrregular2= new ArrayList<>();
        this.verIrregular= new ArrayList<>();
        this.verIrregular2 = new ArrayList<>();
        this.poligonos = new ArrayList<>();
        this.polygonController = new PolygonController();
        this.polyhedronController = new PolyhedronController();
        this.vertexBasedOperations = new VertexBasedOperations();

        this.polylines = new ArrayList<>();
        this.clique=new Vertex();

        this.clique=null;


        carregaComboBox();


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
        //this.eixos.setItems(eixo);
        desenhaRegua();
    }

    public void carregaComboBox(){
        observableList = FXCollections.observableArrayList("X", "Y", "Z");
        comboBox.setItems(observableList);
        comboBox.setValue(observableList.get(0));
//        textAngulo.setText("0");
//        textSeg.setText("0");
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

                if(verIrregular.get(0).distancia(new Vertex(e.getX(),e.getY(),0))<5) {


                    this.arrIrregular.add(new Edge(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0),null));
                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    //System.out.println("saiu!!!!");

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc1, 1);

                    this.poligonos.add(new Polygon(this.verIrregular,this.arrIrregular));

                    this.verIrregular=new ArrayList<>();
                    this.arrIrregular=new ArrayList<>();

                }else {

                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    verIrregular.add(new Vertex(e.getX(), e.getY(), 0));
                    arrIrregular.add(new Edge(this.verIrregular.get(this.verIrregular.size() - 2),
                            this.verIrregular.get(this.verIrregular.size() - 1),
                            null));

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc1, 1);
                }
            }else {

                verIrregular.add(new Vertex(e.getX(), e.getY(), 0));

            }
        }else if(e.getSource()==canvas2){
            if(verIrregular.size()>0){

                if(verIrregular.get(0).distancia(new Vertex(0,e.getY(),e.getX()))<5) {


                    this.arrIrregular.add(new Edge(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0),null));
                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    //System.out.println("saiu!!!!");

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc2, 2);

                    this.poligonos.add(new Polygon(this.verIrregular,this.arrIrregular));

                    this.verIrregular=new ArrayList<>();
                    this.arrIrregular=new ArrayList<>();

                }else {

                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    verIrregular.add(new Vertex(0, e.getY(), e.getX()));
                    arrIrregular.add(new Edge(this.verIrregular.get(this.verIrregular.size() - 2),
                            this.verIrregular.get(this.verIrregular.size() - 1),
                            null));

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc2, 2);
                }
            }else {

                verIrregular.add(new Vertex(e.getX(), e.getY(), 0));

            }
        }else if(e.getSource()==canvas3){
            if(verIrregular.size()>0){

                if(verIrregular.get(0).distancia(new Vertex(e.getX(),0,e.getY()))<5) {


                    this.arrIrregular.add(new Edge(this.verIrregular.get(this.verIrregular.size() - 1), this.verIrregular.get(0),null));
                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    //System.out.println("saiu!!!!");

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc3, 3);

                    this.poligonos.add(new Polygon(this.verIrregular,this.arrIrregular));

                    this.verIrregular=new ArrayList<>();
                    this.arrIrregular=new ArrayList<>();

                }else {

                    //System.out.println(verIrregular.get(verIrregular.size()-1).distancia(new Vertice(e.getX(),e.getY(),0)));
                    verIrregular.add(new Vertex(e.getX(), 0, e.getY()));
                    arrIrregular.add(new Edge(this.verIrregular.get(this.verIrregular.size() - 2),
                            this.verIrregular.get(this.verIrregular.size() - 1),
                            null));

                    this.arrIrregular.get(this.arrIrregular.size() - 1).draw(gc3, 3);
                }
            }else {

                verIrregular.add(new Vertex(e.getX(), 0, e.getY()));

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
        Polygon polygon;
        if(e.getSource()==canvas1){
            polygon = polygonController.createRegularPolygon(new Vertex(e.getX(), e.getY(), 0), lados, null,1);
            polygon.draw(gc1,1);
            this.poligonos.add(polygon);
        }else if(e.getSource()==canvas2){
            polygon = polygonController.createRegularPolygon(new Vertex(0, e.getY(), e.getX()), lados, null,3);
            polygon.draw(gc2,2);
            this.poligonos.add(polygon);
        }else if(e.getSource()==canvas3){
            polygon = polygonController.createRegularPolygon(new Vertex(e.getX(), 0, e.getY()), lados, null,2);
            polygon.draw(gc3,3);
            this.poligonos.add(polygon);
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
                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertex(e.getX(),e.getY(),0)));
                verIrregular2.add(new Vertex(e.getX(), e.getY(), 0));
                arrIrregular2.add(new Edge(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1),
                        null));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc1, 1);
            }else {
                verIrregular2.add(new Vertex(e.getX(), e.getY(), 0));
                polylineAtiva=1;
            }
        }else if(e.getSource()==canvas2){
            if(verIrregular2.size()>0){
                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertex(0,e.getY(),e.getX())));
                verIrregular2.add(new Vertex(0, e.getY(), (e.getX())));
                arrIrregular2.add(new Edge(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1),
                        null));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc2, 2);
            }else {
                verIrregular2.add(new Vertex(0, e.getY(), e.getX()));
                polylineAtiva=1;
            }

        }else if(e.getSource()==canvas3){
            if(verIrregular2.size()>0){
                System.out.println(verIrregular2.get(verIrregular2.size()-1).distancia(new Vertex(e.getX(), 0,e.getY())));
                verIrregular2.add(new Vertex(e.getX(), 0, e.getY()));
                arrIrregular2.add(new Edge(this.verIrregular2.get(this.verIrregular2.size() - 2),
                        this.verIrregular2.get(this.verIrregular2.size() - 1),
                        null));

                this.arrIrregular2.get(this.arrIrregular2.size() - 1).draw(gc3, 3);
            }else {
                verIrregular2.add(new Vertex(e.getX(), 0, e.getY()));
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
        Polygon polygon;
        polygon=polygonController.createRegularPolygon(new Vertex(e.getX(), e.getY(), 0), 500, null,1);
        polygon.draw(gc1,1);
        this.poligonos.add(polygon);
    }

    private void Circulo2(MouseEvent e){
        Polygon polygon;
        polygon=polygonController.createRegularPolygon(new Vertex(0, e.getY(), e.getX()), 500, null,3);
        polygon.draw(gc2,2);
        this.poligonos.add(polygon);
    }

    private void Circulo3(MouseEvent e){
        Polygon polygon;
        polygon=polygonController.createRegularPolygon(new Vertex(e.getX(), 0, e.getY()), 500, null,2);
        polygon.draw(gc3,3);
        this.poligonos.add(polygon);
    }

    private void clear() {
        gc1.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
        gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        gc3.clearRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
        gc4.clearRect(0, 0, canvas4.getWidth(), canvas4.getHeight());
    }

    private void drawall(){
        clear();
        ArrayList<Polygon> poligonos = this.poligonos;
        poligonos.addAll(this.polylines);
        for(Polygon p: poligonos){
            if(p==this.selected){
                drawselected(p,null);
            }else {
                p.draw(gc1,1);
                p.draw(gc2,2);
                p.draw(gc3,3);
            }

        }
        for(Polyhedron p:this.poliedros)
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

    private void drawselected(Polygon poligono, Polyhedron poliedro){
        gc1.setStroke(Color.RED);
        gc2.setStroke(Color.RED);
        gc3.setStroke(Color.RED);
        gc1.setLineWidth(3.0);
        gc2.setLineWidth(3.0);
        gc3.setLineWidth(3.0);
        //System.out.println("selected");
        if(poligono!=null){
            poligono.draw(gc1,1);
            poligono.draw(gc2,2);
            poligono.draw(gc3,3);
        }else {
            poliedro.draw(gc1,1);
            poliedro.draw(gc2,2);
            poliedro.draw(gc3,3);
        }
        gc1.setLineWidth(1.0);
        gc2.setLineWidth(1.0);
        gc3.setLineWidth(1.0);
        gc1.setStroke(Color.BLACK);
        gc2.setStroke(Color.BLACK);
        gc3.setStroke(Color.BLACK);
    }

    private void fechaPolilyne(){
        Polygon polygon = new Polygon(this.verIrregular2,this.arrIrregular2);
        this.polylines.add(polygon);
        polygon.Central = polygonController.calcCentroid(polygon.vertices);
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
        Vertex v = new Vertex();
        boolean foundpolig=false;
        boolean foundpolie=false;
        boolean foundpoly=false;
        int lado=0;
        if(e.getSource()==canvas1){
            System.out.println("canvas1");
            v = new Vertex(e.getX(),e.getY(),0);
            lado=1;
        }else if(e.getSource()==canvas2){
            System.out.println("canvas2");
            v = new Vertex(0,e.getY(),e.getX());
            lado=2;
        }else if(e.getSource()==canvas3){
            System.out.println("canvas3");
            v = new Vertex(e.getX(),0,e.getY());
            lado=3;
        }
       //System.out.print("F "+lado);
        for (Polyhedron poli : this.poliedros) {
            for (Polygon p : poli.faces) {
                for (Edge a : p.edges) {
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

        for (Polygon p : this.poligonos) {
            for (Edge a : p.edges) {
                if (a.selected(v, lado)) {
                    this.selected = a.father;
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
        for (Polygon p : this.polylines) {
            for (Edge a : p.edges) {
                if (a.selected(v, lado)) {
                    this.selected = a.father;
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
            this.polylines.remove(selected);
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
        this.clique=new Vertex(e.getX(),e.getY(),0);
    }

    /*
    * Podia ter colocado num ArrayList do qual está selecionado separado
    *  dae tira as Redundância de codigo
    *  Mas vou
    *  Num vou
    *  Agr já terminei esta merda
    * */
    private void rotaciona(MouseEvent e){
        double x;
        double y;
        double z;

        if (clique != null) {
            if ((e.getX() != this.clique.x) || (e.getY() != clique.y)) {
                if (e.getSource() == canvas1) {
                    if (selected != null) {
                        x = selected.Central.x;
                        y = selected.Central.y;
                        z = selected.Central.z;
                        vertexBasedOperations.rotaciona(selected.vertices
                                , selected.Central, (e.getX() - clique.x) * 0.005, 3);

                        vertexBasedOperations.rotaciona(selected.vertices,
                                selected.Central, (e.getY() - clique.y) * 0.005, 2);

                        vertexBasedOperations.translada(selected.vertices, selected.Central, new Vertex(
                                x - selected.Central.x,
                                y - selected.Central.y,
                                z - selected.Central.z
                        ));
                    }else if(poliselected!=null){
                        x=poliselected.Central.x;
                        y=poliselected.Central.y;
                        z=poliselected.Central.z;
                        vertexBasedOperations.rotaciona(poliselected.vertices
                                ,poliselected.Central,(e.getX() - clique.x)*0.005,3);

                        vertexBasedOperations.rotaciona(poliselected.vertices,
                                poliselected.Central,(e.getY() - clique.y)*0.005,2);

                        vertexBasedOperations.translada(poliselected.vertices,poliselected.Central,new Vertex(
                                x-poliselected.Central.x,
                                y-poliselected.Central.y,
                                z-poliselected.Central.z
                        ));
                    }
                }else if (e.getSource() == canvas2) {
                    if (selected != null) {
                        x=selected.Central.x;
                        y=selected.Central.y;
                        z=selected.Central.z;
                        vertexBasedOperations.rotaciona(selected.vertices,selected.Central,(e.getX() - clique.x)*0.005,3);
                        vertexBasedOperations.rotaciona(selected.vertices,selected.Central,(e.getY() - clique.y)*0.005,1);
                        vertexBasedOperations.translada(selected.vertices,selected.Central,new Vertex(
                                x-selected.Central.x,
                                y-selected.Central.y,
                                z-selected.Central.z
                        ));
                        selected.Central.x=x;
                        selected.Central.y=y;
                        selected.Central.z=z;

                    } else if (poliselected != null) {
                        x=this.poliselected.Central.x;
                        y=this.poliselected.Central.y;
                        z=this.poliselected.Central.z;
                        vertexBasedOperations.rotaciona(poliselected.vertices,poliselected.Central,(e.getX() - clique.x)*0.005,3);
                        vertexBasedOperations.rotaciona(poliselected.vertices,poliselected.Central,(e.getY() - clique.y)*0.005,1);
                        vertexBasedOperations.translada(poliselected.vertices,poliselected.Central, new Vertex(
                                        x-this.poliselected.Central.x,
                                        y-this.poliselected.Central.y,
                                        z-this.poliselected.Central.z
                                ));
                        this.poliselected.Central.x=x;
                        this.poliselected.Central.y=y;
                        this.poliselected.Central.z=z;
                    }
                }else if (e.getSource() == canvas3) {
                    if (selected != null) {
                        x=selected.Central.x;
                        y=selected.Central.y;
                        z=selected.Central.z;
                        vertexBasedOperations.rotaciona(selected.vertices,selected.Central,(e.getX() - clique.x)*0.005,1);
                        vertexBasedOperations.rotaciona(selected.vertices,selected.Central,(e.getY() - clique.y)*0.005,2);
                        vertexBasedOperations.translada(selected.vertices,selected.Central,new Vertex(
                                x-selected.Central.x,
                                y-selected.Central.y,
                                z-selected.Central.z
                        ));

                        selected.Central.x=x;
                        selected.Central.y=y;
                        selected.Central.z=z;

                    } else if (poliselected != null) {
                        x=this.poliselected.Central.x;
                        y=this.poliselected.Central.y;
                        z=this.poliselected.Central.z;

                        vertexBasedOperations.rotaciona(poliselected.vertices,poliselected.Central,(e.getX() - clique.x)*0.005,1);
                        vertexBasedOperations.rotaciona(poliselected.vertices,poliselected.Central,(e.getY() - clique.y)*0.005,2);
                        vertexBasedOperations.translada(poliselected.vertices,poliselected.Central,new Vertex(
                                x-this.poliselected.Central.x,
                                y-this.poliselected.Central.y,
                                z-this.poliselected.Central.z
                        ));

                        this.poliselected.Central.x=x;
                        this.poliselected.Central.y=y;
                        this.poliselected.Central.z=z;
                    }
                }

            }
            this.clique.x=e.getX();
            this.clique.y=e.getY();
            drawall();
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
                        vertexBasedOperations.translada(selected.vertices,selected.Central,
                                new Vertex(e.getX() - clique.x, e.getY() - clique.y, 0));
//                        selected.Central.x+=e.getX() - clique.x;
//                        selected.Central.y+=e.getY() - clique.y;

                    } else if (poliselected != null) {
                        vertexBasedOperations.translada(poliselected.vertices,poliselected.Central
                                ,new Vertex(e.getX() - clique.x, e.getY() - clique.y, 0));
                    }
                } else if (e.getSource() == canvas2) {
                    if (selected != null) {
                        vertexBasedOperations.translada(selected.vertices,selected.Central,
                                new Vertex(0, e.getY() - clique.y, e.getX() - clique.x));
//                        selected.Central.z+=e.getX() - clique.x;
//                        selected.Central.y+=e.getY() - clique.y;

                    } else if (poliselected != null) {
                        vertexBasedOperations.translada(poliselected.vertices,poliselected.Central,
                                new Vertex(0, e.getY() - clique.y, e.getX() - clique.x));
                    }
                } else if (e.getSource() == canvas3) {
                    if (selected != null) {
                        vertexBasedOperations.translada(selected.vertices,selected.Central,
                                new Vertex(e.getX() - clique.x, 0, e.getY() - clique.y));
//                        selected.Central.x+=e.getX() - clique.x;
//                        selected.Central.z+=e.getY() - clique.y;

                    } else if (poliselected != null) {
                        vertexBasedOperations.translada(poliselected.vertices,poliselected.Central,
                                new Vertex(e.getX() - clique.x, 0, e.getY() - clique.y));
                    }
                }
                clique.x = e.getX();
                clique.y = e.getY();
                drawall();
            }
        }
    }

    public void buttonRevoluciona(){
        System.out.println("111111111111111111111");
        if(selected!=null){

            //1 - z, 2 - x, 3 - y
            int eixo=0, segmentos, angulo;

            if(comboBox.getValue()=="X"){
                eixo=2;
            }else if(comboBox.getValue()=="Y"){
                eixo=3;
            }else if(comboBox.getValue()=="Z"){
                eixo=1;
            }



            segmentos= Integer.parseInt(textSeg.getText());
            angulo = Integer.parseInt(textAngulo.getText());

            System.out.println("sei lá "+angulo+"\n");

            System.out.println(toRadians(360));
            this.poliedros.add(polyhedronController.criateNewPolyhedronRegular(selected,segmentos,eixo,angulo));
            this.poligonos.remove(selected);
            this.polylines.remove(selected);
            selected=null;
            drawall();
        }
    }

//    public void ctest(){
//        this.poligonos.add(new Polygon(new Vertex(300,150,0),3,null,1));
//        drawall();
//    }
    public void rvDebug(){
        selected.Central = polygonController.calcCentroid(selected.vertices);
        vertexBasedOperations.rotaciona(selected.vertices,selected.Central,0.1,2);
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
        //selected.calcCentroid();
        ante=(selected.Central.z*cose)-(selected.Central.y*seno);
        selected.Central.y=(selected.Central.z*seno)+(selected.Central.y*cose);
        selected.Central.z=ante;

        vertexBasedOperations.translada(selected.vertices,selected.Central,new Vertex(
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
            this.poligonos = (ArrayList<Polygon>) (ois.readObject());
            this.poliedros = (ArrayList<Polyhedron>) (ois.readObject());
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
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,0.99,2);
                                }
                                vertexBasedOperations.translada(selected.vertices,selected.Central,new Vertex(
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
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            0.99,2);
                                }
                                vertexBasedOperations.translada(this.poliselected.vertices,this.poliselected.Central,
                                        new Vertex(
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
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            1.01,3);
                                }else if((e.getX() - clique.x)<0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            0.99,3);
                                }
                                if ((e.getY() - clique.y)<0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            0.99,2);
                                }

                                vertexBasedOperations.translada(selected.vertices,selected.Central,
                                        new Vertex(
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
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            1.01,2);
                                }else if((e.getY() - clique.y)>0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            0.99,2);
                                }

                                vertexBasedOperations.translada(this.poliselected.vertices,this.poliselected.Central,new Vertex(
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

                                if((e.getX() - clique.x)>0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            1.01,3);
                                }else if((e.getY() - clique.y)>0){
                                    vertexBasedOperations.scala(selected.vertices,selected.Central,
                                            0.99,3);
                                }

                                vertexBasedOperations.translada(selected.vertices,selected.Central,
                                        new Vertex(
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
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            1.01,1);
                                }else if((e.getX() - clique.x)<0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            0.99,1);
                                }
                                if ((e.getY() - clique.y)<0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            1.01,3);
                                }else if((e.getY() - clique.y)>0){
                                    vertexBasedOperations.scala(this.poliselected.vertices,this.poliselected.Central,
                                            0.99,3);
                                }

                                vertexBasedOperations.translada(this.poliselected.vertices,this.poliselected.Central,
                                        new Vertex(
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
