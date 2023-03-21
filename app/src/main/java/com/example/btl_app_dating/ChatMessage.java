package com.example.btl_app_dating;

public class ChatMessage {
    private String sender;
    private String mestxt;
    private String time;

    public ChatMessage(String sender,String mestxt,String time){
        this.sender=sender;
        this.mestxt=mestxt;
        this.time=time;
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
