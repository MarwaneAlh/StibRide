package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author Marwa
 */
public class MainApp extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
       VBox root = FXMLLoader.load(getClass().getResource("/fxml/"
               + "presentation.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("HE2B ESI - STIB Planner");
       stage.show();
    }
}
