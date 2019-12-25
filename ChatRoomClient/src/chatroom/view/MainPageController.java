package chatroom.view;

import chatroom.MainApp;
import chatroom.Sender;
import chatroom.model.Consts;
import chatroom.model.Massage;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

import javax.imageio.ImageIO;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;


public class MainPageController {
    @FXML
    private ListView<String> friendList = new ListView<>(Consts.getFriendData());
    @FXML
    private Button sendbutton;
    @FXML
    private TextField textField;
    @FXML
    private ListView<String> chatRecord = new ListView<>(Consts.chatMassage);
    @FXML
    private Label talkingToLabel;
    @FXML
    private Label youLabel;
    @FXML
    private ListView<String> fileList = new ListView<>(Consts.fileList);
    @FXML
    private ListView<String> groupList=new ListView<>(Consts.groupList);
    @FXML
    private ListView<String> imageList = new ListView<>(Consts.imageList);
    private String talkto;
    private String fileName;
    private String imageName;

    @FXML
    private void initialize() {
        friendList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        friendList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> talkingToLabel.setText(newSelection)
        );
        friendList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> talkto = newSelection
        );
        friendList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> Consts.updateGroupList(newSelection)
        );
        fileList.getSelectionModel().selectedItemProperty().addListener((obs,oldSelect,newSelect)->fileName=newSelect);
        imageList.getSelectionModel().selectedItemProperty().addListener((obs,oldSelect,newSelect)->imageName=newSelect);
        youLabel.setText(MainApp.you);
    }

    //发送聊天信息
    @FXML
    public void sendMassage() throws IOException {
        String sendMassage = textField.getText();
        Date date = new Date();
        Massage massage = new Massage("Massages", date, sendMassage,
                "null", MainApp.you, talkto.trim());
        String sMassage = massage.getMassageString();
        Sender sender = new Sender(Consts.serverAddress, sMassage.getBytes());
        sender.start();
        //显示聊天记录
        String line = Consts.setSendMassage(sendMassage, date.toString(), talkto.trim());
        Consts.chatMassage.add(line);
    }
    //发送群聊
    @FXML
    public void sendGroupMassage() throws IOException {
        String sendMassage = textField.getText();
        Date date = new Date();
        Massage massage = new Massage("GroupMassages", date, sendMassage,
                "null", MainApp.you, Arrays.toString(Consts.groupList.toArray(new String[0])));
        String sMassage = massage.getMassageString();
        Sender sender = new Sender(Consts.serverAddress, sMassage.getBytes());
        sender.start();
        //显示聊天记录
        String line = Consts.setSendMassage(sendMassage, date.toString(), "多人");
        Consts.chatMassage.add(line);
    }
    //刷新页面
    @FXML
    private void refreshList() throws SocketException, InterruptedException {
        Massage massage = new Massage("RefreshOnlineUsers", new Date(),
                "null",
                "null", MainApp.you, "null");
        String json = massage.getMassageString();
        Sender sender = new Sender(Consts.serverAddress, json.getBytes());
        sender.start();
        Thread.sleep(2000);
        //刷新在线用户列表和聊天信息
        friendList.setItems(null);
        friendList.setItems(Consts.getFriendData());
        chatRecord.setItems(null);
        chatRecord.setItems(Consts.chatMassage);
        setFile();
        //刷新文件和图片列表
        fileList.setItems(null);
        fileList.setItems(Consts.fileList);
        imageList.setItems(null);
        imageList.setItems(Consts.imageList);
        groupList.setItems(null);
        groupList.setItems(Consts.groupList);
    }
    //发送文件
    @FXML
    private void sendFile() throws IOException {
        String filePath="/home/robin/ChatroomFile/"+ MainApp.you +"'s File/"+fileName;
        File file = new File(filePath);
        //将文件转换为byte数组
        InputStream input=new FileInputStream(file);
        byte[] byfile=new byte[input.available()];
        input.read(byfile);
        input.close();
        //将文件的byte数组转为字符串来构建Massage类
        String s= new String(byfile);

        Date date = new Date();
        Massage massage = new Massage("File", date, s,
                fileName, MainApp.you, talkto.trim());
        String sMassage = massage.getMassageString();
        Sender sender = new Sender(Consts.serverAddress, sMassage.getBytes());
        sender.start();
        //显示聊天记录
        String line = Consts.setSendFile(fileName, date.toString(), talkto.trim());
        Consts.chatMassage.add(line);
    }

    private void setFile(){
        String filePath="/home/robin/ChatroomFile/"+ MainApp.you +"'s File";
        File myFile = new File(filePath);
        myFile.mkdir();
        File[] files= myFile.listFiles();
        assert files != null;
        //将文件分类，分为文件和图片
        for(File file:files) {
            if (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")
                    || file.getName().endsWith(".bmp")
                    || file.getName().endsWith(".gif")
                    || file.getName().endsWith(".png")){
                if(!Consts.imageList.contains(file.getName())){
                    Consts.imageList.add(file.getName());
                }else {
                    continue;
                }
            }else {
                if(!Consts.fileList.contains(file.getName())){
                    Consts.fileList.add(file.getName());
                }
            }
        }
    }
    //发送图片
    @FXML
    private void sendImage() throws IOException {
        String filePath="/home/robin/ChatroomFile/"+ MainApp.you +"'s File/"+imageName;
        String kind=filePath.substring(filePath.lastIndexOf(".")+1);
        File file = new File(filePath);
        BufferedImage bi= ImageIO.read(file);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(bi,kind,baos);
        baos.close();
        byte[] image=baos.toByteArray();
        //同文件一样，转为byte数组，但是在转为String之前用base64编码以确保格式
        Base64.Encoder encoder = Base64.getEncoder();
        String imageS=encoder.encodeToString(image);
        Date date = new Date();
        Massage massage = new Massage("Image", date, imageS,
                imageName, MainApp.you, talkto);
        String sMassage = massage.getMassageString();
        Sender sender = new Sender(Consts.serverAddress, sMassage.getBytes());
        sender.start();

        //显示聊天记录
        String line = Consts.setSendImage(imageName, date.toString(), talkto.trim());
        Consts.chatMassage.add(line);
    }
}