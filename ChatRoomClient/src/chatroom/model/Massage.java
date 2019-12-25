package chatroom.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Massage {
    private static SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
    private String title;
    private Date date;
    private String content;
    private String fileName;
    private String sendUser;
    private String receiveUser;
    private String time;

    public Massage(String title, Date date, String content,
                   String fileName, String sendUser, String receiveUser) {
        this.title = title;
        this.date = date;
        this.time = sdf.format(date);
        this.content = content;
        this.fileName = fileName;
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    public String getTitle() {
        return title;
    }

    public Massage setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Massage setDate(Date date) {
        this.date = date;
        return this;
    }


    public String getContent() {
        return content;
    }

    public Massage setContent(String content) {
        this.content = content;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Massage setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getSendUser() {
        return sendUser;
    }

    public Massage setSendUser(String sendUser) {
        this.sendUser = sendUser;
        return this;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public Massage setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
        return this;
    }
    //将massage的内容转换为字符串，
    // 使用汉字分割是为了防止内容或标题中出现分割符使解析失败
    public String getMassageString() {
        if (content == null) {
            return "奰" + title + "奰" + time + "奰" +
                    "" + "奰" + fileName + "奰" + sendUser + "奰" + receiveUser + "奰";
        } else {
            return "奰" + title + "奰" + time + "奰" +
                    content + "奰" + fileName + "奰" + sendUser + "奰" + receiveUser + "奰";
        }
    }
    //将字符串内容解析成为massage类
    public static Massage getMassage(String mass) throws ParseException {
        String[] s = mass.split("奰");
        return new Massage(s[1], sdf.parse(s[2]), s[3], s[4], s[5], s[6]);
    }
}
