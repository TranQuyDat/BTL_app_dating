package com.example.btl_app_dating;

public class conversation {
    private String last_message_timestamp;
    private String last_message;
    private String name_user1;
    private String userid1;
    private String userid2;
    private int img_user1;
    private int img_user2;
    private  String Key_conv;

    private String name_user2;

    public conversation() {
    }

    public conversation(String last_message_timestamp, String last_message, String name_user1,String name_user2, String userid1, String userid2) {
        this.last_message_timestamp = last_message_timestamp;
        this.last_message = last_message;
        this.name_user1 = name_user1;
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.name_user2 = name_user2;
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

    public String getname_user1() {
        return name_user1;
    }

    public void setName_user1(String name_user1) {
        this.name_user1 = name_user1;
    }

    public String getname_user2() {
        return name_user2;
    }

    public void setName_user2(String name_user2) {
        this.name_user2 = name_user2;
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

    public int getimg_user1() {
        return img_user1;
    }

    public void setImg_user1(int img_user1) {
        this.img_user1 = img_user1;
    }

    public int getimg_user2() {
        return img_user2;
    }

    public void setImg_user2(int img_user2) {
        this.img_user2 = img_user2;
    }

    public String getKey_conv() {
        return Key_conv;
    }

    public void setKey_conv(String key_conv) {
        Key_conv = key_conv;
    }


}
