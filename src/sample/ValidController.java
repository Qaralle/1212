package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import packet.*;

public class ValidController {

    @FXML
    private TextField nameLable;

    @FXML
    private TextField hieghtLabel;

    @FXML
    private TextField yLabel;

    @FXML
    private ComboBox<Color> hairColorBox;

    @FXML
    private ComboBox<Color> eyrColorBox;

    @FXML
    private ComboBox<Country> nationalityBox;

    @FXML
    private TextField xLabel;

    @FXML
    private TextField locationLabel;

    @FXML
    private TextField x1Label;

    @FXML
    private TextField y1Label;

    @FXML
    private Button addButton;

    @FXML
    private Button addIfMinButton;

    public void initialize() {
        hairColorBox.setItems( FXCollections.observableArrayList(Color.values()));
        eyrColorBox.setItems(FXCollections.observableArrayList(Color.values()));
        nationalityBox.setItems(FXCollections.observableArrayList(Country.values()));

        addButton.setOnAction(event -> {
            c
        });
    }


        private final javafx.event.EventHandler<WindowEvent> closeEventHandler = new javafx.event.EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);

        }
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }

}
