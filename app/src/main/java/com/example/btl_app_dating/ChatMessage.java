package com.example.btl_app_dating;

public class ChatMessage {
    private String sender;
    private String mestxt;
    private String time;
    private String sender_id;
    private int avt_mes_Id;

    public ChatMessage() {
    }

    public ChatMessage(String sender_id,int avt_mes_Id , String sender, String mestxt, String time){
        this.sender_id =sender_id;
        this.sender=sender;
        this.mestxt=mestxt;
        this.time=time;
        this.avt_mes_Id =avt_mes_Id;
    }
    public String getSender(){
        return sender;
    }
    public String getMestxt(){
        return mestxt;
    }

    public String getTime() {
        return time;
    }

    public int getAvt_mes_Id() {
        return avt_mes_Id;
    }

    public String getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setAvt_mes_Id(int avt_mes_Id) {
        this.avt_mes_Id = avt_mes_Id;
    }

    public void setSender(String sender){
        this.sender=sender;
    }

    public void setMestxt(String mestxt){
        this.mestxt=mestxt;
    }

    public void setTime(String time){
        this.time=time;
    }
}
