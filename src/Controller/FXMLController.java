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

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author gabriela
 */
public class FXMLController implements Initializable {

    @FXML
    private Canvas canvas1;
    
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

    private ArrayList<Aresta> arrIrregular;

    private ArrayList<Poligono> poligonos;

    private Vertice clique;


    //mantenha setando a bosta até ele clicar denovo na porra do botão ou em qualquer outro
    private ArrayList<Vertice> linha;


    private ArrayList<Poliedro> poliedros;

    GraphicsContext gc1;
    GraphicsContext gc2;
    GraphicsContext gc3;
    GraphicsContext gc4;

    int lados;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.arrIrregular= new ArrayList<>();
        this.verIrregular= new ArrayList<>();
        this.poligonos = new ArrayList<>();
        this.clique=new Vertice();
        this.linhas=new ArrayList<>();
        this.poliedros=new ArrayList<>();
        canvas1.widthProperty().addListener(evt -> drawall());
        canvas1.heightProperty().addListener(evt -> drawall());
        gc1= canvas1.getGraphicsContext2D();
        gc2= canvas2.getGraphicsContext2D();
        gc3= canvas3.getGraphicsContext2D();
        gc4= canvas4.getGraphicsContext2D();
        this.cliques=0;
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

    //FAZ A POLYLINE
    public void ButtonPonto(){
        canvas1.setOnMouseClicked(this::Irregular);
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
        canvas1.setOnMouseClicked(this::Regular);
        String resposta = JOptionPane.showInputDialog(null, "Quantos lados possui o polígono?");

        lados = Integer.parseInt(resposta);
        System.out.println(lados);
    }

    public void Regular(MouseEvent e){

        this.poligonos.add(new Poligono(new Vertice(e.getX(),e.getY(),0),lados,null));
        drawall();


    }

    public void drawall(){
//        for(Aresta a: this.arrIrregular){
//            a.draw(gc1,1);
//        }
        for(Poligono p:this.poligonos){
            p.draw(gc1,1);
        }

        desenhaRegua();
    }

    public void desenhaRegua(){
        for (int i=0;i<=canvas1.getHeight();i+=10){
            gc1.strokeLine(0,i,6,i);
            if(i%50==0){
                Font f = new Font("Comic Sans MS", 9);
                gc1.strokeText(String.valueOf(i), 7,i);
            }

        }
    }
}
