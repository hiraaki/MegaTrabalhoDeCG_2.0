package Models;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {

//    public ResizableCanvas() {
//        // Redraw canvas when size changes.
//        widthProperty().addListener(evt -> draw());
//        heightProperty().addListener(evt -> draw());
//    }
//
//    private void draw() {
//        double width = getWidth();
//        double height = getHeight();
//
//        GraphicsContext gc = getGraphicsContext2D();
//        gc.clearRect(0, 0, width, height);
//
//        gc.setStroke(Color.RED);
//        gc.strokeLine(0, 0, width, height);
//        gc.strokeLine(0, height, width, 0);
//    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    @Override
    public double minHeight(double width)
    {
        return 64;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double minWidth(double height)
    {
        return 0;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width);
        super.setHeight(height);
        //draw();
    }

}
