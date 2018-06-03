/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author gabriela
 */
public class FXMLController implements Initializable {

    @FXML
    private Canvas canvas1;
    
    @FXML
    private Canvas canvas2;
    
    @FXML
    private Canvas canvas3;
    
    @FXML
    private Canvas canvas4;
    
    @FXML
    private Button botaoSeleciona;
    
    @FXML
    private Button botaoExclui;
    
    @FXML
    private Button botaoPonto;
    
    @FXML
    private Button botaoCirculo;
    
    @FXML
    private Button botaoReta;
    
    @FXML
    private Button botaoPoligonoR;
    
    GraphicsContext gc1;
    GraphicsContext gc2;
    GraphicsContext gc3;
    GraphicsContext gc4;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc1= canvas1.getGraphicsContext2D();
        gc2= canvas2.getGraphicsContext2D();
        gc3= canvas3.getGraphicsContext2D();
        gc4= canvas4.getGraphicsContext2D();
    }    
    
}
