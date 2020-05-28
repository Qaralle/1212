package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import packet.CommandA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

public class Controller {

    @FXML
    private TextField PortLabel;

    @FXML
    public Button PortButton;

    @FXML
    private TextField LoginLabel;

    @FXML
    private PasswordField PasswordLabel;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button LoginButton;


    public volatile String flag="НЕОПРЕД";

    public String login;
    byte[] sendData;
    private OnExitThread thread;
    private final java.lang.Object Object = new Object();

    //protected receiver res;


    CommandA sentence;
    String key = new String();

    public void setFlag(String flag_){
        this.flag=flag_;
    }

    public String getFlag(){
        return flag;
    }

    public void initialize() {
        Customer loginDeliver = new Customer();
        thread = new OnExitThread();
        Runtime.getRuntime().addShutdownHook(thread);
        Validator3000 terminal = new Validator3000(thread,this,Object);
        System.out.println("main thread is alive");
        Thread testtask = new Thread(terminal);
        testtask.start();
        LoginButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "login";
                CommandA sendCommand = new CommandA(key, LoginLabel.getText(), PasswordLabel.getText(), Validator3000.ACCESS);
                login = LoginLabel.getText();
//                            thread.setLogin(login);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
           // while (Validator3000.ACCESS.equals("DEFAULT")){
              //  System.out.println("I can't belive you done this");
            //}

            task.start();
            synchronized (Object) {
                try {
                    Object.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            //переделать
            /*class Test extends Task<Void> {
                public Test(Controller cntrl) {
                    this.controller = cntrl;
                }

                private Controller controller;

                @Override
                protected Void call() throws Exception {
                    synchronized (Object) {
                        Object.wait();
                        return null;
                    }
                }

                }

                    Thread task_loop = new Thread(new Test(this));
                    task_loop.start();
                    try {
                        task_loop.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            */
            if (flag.equals("LOGIN")) {
                        thread.setLogin(login);
                        loginDeliver.accept(login);
                        LoginButton.getScene().getWindow().hide();
                        openMain(loginDeliver);
                    }
            else  if (flag.equals("НЕОПРЕД")){
                showAlert("Сервер умер, заплотите 5 шекелей");
            }

        });
        SignUpButton.setOnAction(event -> {
            Thread task = new Thread(() -> {
                key = "sign_up";
                CommandA sendCommand = new CommandA(key, LoginLabel.getText(), PasswordLabel.getText(), Validator3000.ACCESS);
                login = LoginLabel.getText();
//                            thread.setLogin(login);
                try {
                    sendSmth(sendCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            task.start();

            synchronized (Object) {
                try {
                    Object.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (flag.equals("LOGIN")) {
                thread.setLogin(login);
                loginDeliver.accept(login);
                SignUpButton.getScene().getWindow().hide();
                openMain(loginDeliver);
            }
            else  if (flag.equals("НЕОПРЕД")){
                showAlert("Сервер умер, заплотите 5 шекелей");
            }
        });
        PortButton.setOnAction(event -> {
            try {
                String sb = PortLabel.getText();
                if (!sb.equals("")) {
                    terminal.setPort(Integer.parseInt(sb));

                    //terminal.interactiveMod("sda",Integer.parseInt(sb));
                }
            } catch (Exception ex) {
                showAlert("sosi");

            }
        });

    }
    private void openMain(Customer deliver){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/main.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        MainWindowConroller controller = loader.getController();
        controller.initData(deliver);
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        stage.showAndWait();

    }
    public void showAlert(String str) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Here...");
            alert.setHeaderText(null);
            alert.setContentText(str);
            alert.showAndWait();
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
}
