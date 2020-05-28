package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import packet.CommandA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

public class MainWindowConroller {


    @FXML
    private Text loginLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button removeHeadButton;

    @FXML
    private Button exitButton;

    public String login;
    byte[] sendData;


    //protected receiver res;


    CommandA sentence;
    String key = "";

    public void initialize() {
        infoButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "info";
                CommandA sendCommand = new CommandA(key, Validator3000.ACCESS);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            task.start();
        });
        helpButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "help";
                CommandA sendCommand = new CommandA(key, Validator3000.ACCESS);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            task.start();
        });

        clearButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "clear";
                CommandA sendCommand = new CommandA(key, Validator3000.ACCESS);
                sendCommand.setLogin(login);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            task.start();
        });
        historyButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "history";
                CommandA sendCommand = new CommandA(key, Validator3000.ACCESS);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            task.start();
        });

        removeHeadButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "remove_head";
                CommandA sendCommand = new CommandA(key, Validator3000.ACCESS);
                sendCommand.setLogin(login);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            task.start();
        });

        addButton.setOnAction(event -> {
       /*     Thread task = new Thread(() -> {
                key = "add";
                CommandA com = new CommandA(key, Validator3000.ACCESS);
                com.setFields(new Scanner(System.in));
                com.setLogin(login);
                try {
                    sendSmth(com);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            task.start();*/
            openValid();
        });
    }

    public void initData(Customer customer){
        loginLabel.setText(customer.getLogin());
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
    private byte[] serialaze(CommandA ob){

        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ob);
            oos.close();
            byte[] obj = baos.toByteArray();
            baos.close();
            return obj;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    private void sendSmth(CommandA data) throws IOException {
        InetAddress IPAddress = InetAddress.getByName(null);
        sentence = data;
        sendData = serialaze(data);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1229);
        Validator3000.clientSocket.send(sendPacket);

    }

    private void openValid(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/valid.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        ValidController controller = loader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        stage.showAndWait();

    }
}
