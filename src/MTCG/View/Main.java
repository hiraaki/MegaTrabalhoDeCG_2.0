package View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent p= FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene scene= new Scene(p);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trabalho de CG");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
