package chatroom.view;

import chatroom.ClientListener;
import chatroom.MainApp;
import chatroom.Sender;
import chatroom.model.Consts;
import chatroom.model.Massage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Date;

public class LoginController {
    @FXML
    private TextField nameField;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField serverAddress;
    @FXML
    private TextField localPort;
    public static MainApp mainApp;

    public static boolean noError = true;

    public void setMainApp(MainApp mainapp) {
        mainApp = mainapp;
    }

    public LoginController() {

    }
    //点击登陆按钮时向服务器发送地址和用户名消息
    @FXML
    private void handleSignIn() throws IOException, InterruptedException {
        String name = nameField.getText();
        String addr = serverAddress.getText();
        String port = localPort.getText();
        Consts.serverAddress = new String[]{addr, Integer.toString(42052)};
        Consts.localAddress = new String[]{"localhost", port};
        ClientListener clientListener = new ClientListener(Consts.localAddress);//启动监听
        clientListener.start();

        Massage massage = new Massage("UserLogin", new Date(),
                "null", Consts.getLocalAddress(), name, "null");
        String jsonMassage = massage.getMassageString();
        Sender sender = new Sender(Consts.serverAddress, jsonMassage.getBytes());
        sender.start();
        Thread.sleep(2000);
        if (noError) {
            MainApp.you = name;
            mainApp.showMainPage();
        }
    }
    //点击注册按钮时向服务器发送地址和用户名消息，
    @FXML
    private void handleSignUp() throws IOException, InterruptedException {
        String name = nameField.getText();
        String addr = serverAddress.getText();
        String port = localPort.getText();
        Consts.serverAddress = new String[]{addr, Integer.toString(42052)};
        Consts.localAddress = new String[]{"localhost", port};
        ClientListener clientListener = new ClientListener(Consts.localAddress);//启动监听
        clientListener.start();
        Massage massage = new Massage("UserSignUp", new Date(),
                "null", Consts.getLocalAddress(), name, "null");
        String jsonMassage = massage.getMassageString();
        System.out.println(jsonMassage);
        Sender sender = new Sender(Consts.serverAddress, jsonMassage.getBytes());
        sender.start();
        Thread.sleep(2000);
        if (noError) {
            MainApp.you = name;
            //显示主页
            mainApp.showMainPage();
        }
    }
}
