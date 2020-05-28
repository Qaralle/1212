package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("trash");
        primaryStage.setScene(new Scene(root, 540, 400));
        primaryStage.show();
        Controller controller = loader.getController();

        primaryStage.setOnCloseRequest(controller.getCloseEventHandler());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
