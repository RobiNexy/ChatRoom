package chatroom;

import chatroom.model.Consts;
import chatroom.model.Massage;
import chatroom.view.LoginController;
import chatroom.view.MainPageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.SocketException;
import java.util.Base64;
import java.util.Date;


public class MainApp extends Application {
    private static Stage primaryStage;
    public AnchorPane login;
    public SplitPane MainPage;
    public Scene loginScene;
    public Scene mainPageScene;


    public static String you;

    public MainApp() throws IOException {
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("ChatRoom");
        //关闭窗口时
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (you != null) {
                    Massage massage = new Massage("UserOffline", new Date(),
                            you, "null", you, "null");
                    String jsonMassage = massage.getMassageString();
                    Sender sender = null;
                    try {
                        sender = new Sender(Consts.serverAddress, jsonMassage.getBytes());
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    sender.start();
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            }
        });
        showLogin();
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
            login = loader.load();
            loginScene = new Scene(login);
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainPage.fxml"));
            MainPage = loader.load();
            MainPageController controller = loader.getController();
            mainPageScene = new Scene(MainPage);
            primaryStage.setScene(mainPageScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
