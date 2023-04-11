package com.example.btl_app_dating;

import android.media.Image;
import android.net.Uri;

import javax.xml.transform.Source;


public class Viewpage extends User {
    private String img_view ;
    public Viewpage() {
        super();
    }

    public Viewpage(String img_view) {
        this.img_view =img_view;
    }

    public Viewpage(Uri avt_img, String name, int age, String birth, String relationship, String word, String gender,String img_view )
    {
        super(String.valueOf(avt_img), name, age, birth, relationship, word, gender);
        this.img_view =img_view;
    }

    public String getimg_view() {
        return img_view;
    }

    public void setImg_view(String img_view) {
        this.img_view = img_view;
    }
}
