package com.example.btl_app_dating;

public class conversation {
    private String last_message_timestamp;
    private String last_message;
    private String name_receiver;
    private String userid1;
    private String userid2;
    private int img_receiver;

    public conversation() {
    }

    public conversation(String last_message_timestamp, String last_message, String name_receiver, String userid1, String userid2, int img_receiver) {
        this.last_message_timestamp = last_message_timestamp;
        this.last_message = last_message;
        this.name_receiver = name_receiver;
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.img_receiver = img_receiver;
    }

    public String getlast_message_timestamp() {
        return last_message_timestamp;
    }

    public void setLast_message_timestamp(String last_message_timestamp) {
        this.last_message_timestamp = last_message_timestamp;
    }

    public String getlast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getname_receiver() {
        return name_receiver;
    }

    public void setName_receiver(String name_receiver) {
        this.name_receiver = name_receiver;
    }

    public String getuserid1() {
        return userid1;
    }

    public void setUserid1(String userid1) {
        this.userid1 = userid1;
    }

    public String getuserid2() {
        return userid2;
    }

    public void setUserid2(String userid2) {
        this.userid2 = userid2;
    }

    public int getimg_receiver() {
        return img_receiver;
    }

    public void setImg_receiver(int img_receiver) {
        this.img_receiver = img_receiver;
    }
}
