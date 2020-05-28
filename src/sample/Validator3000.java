package sample;

import javafx.concurrent.Task;

import java.net.*;
import java.util.Map;


/**
 * Класс, выполняющий функци инвокера
 * @author Maxim Antonov and Andrey Lyubkin
 */
public class Validator3000 extends Task<Void> {

    private final Controller cntrl;
    private final Object obj;

    public Validator3000(OnExitThread thread, Controller controller, Object obj_){
        this.thread=thread;
        this.port=12345;
        this.cntrl=controller;
        this.obj=obj_;
    }
    //private boolean WORK=true;
    static String ACCESS;




    private Integer port;
    static byte[] finalReceiveData;
    static DatagramSocket clientSocket;
    private OnExitThread thread;
    protected Map<String, String> bufferMap;
    protected String[] bufferStringForArgs;

    {
        ACCESS="DEFAULT";
    }

    /**
     * Старт работы
     */

    @Override
    protected Void call() throws Exception {
        clientSocket = new DatagramSocket(port);
        Thread receiver = new Thread(new Receiver(cntrl,obj));
        //Thread Sanders = new Thread(new Sender(thread));
        synchronized (ACCESS) {
            receiver.start();
            receiver.join();
            //Sanders.start();
            //Sanders.join();
        }
        return null;
    }

    public void setPort(Integer port_){
        this.port=port_;
    }
}
