package com.animalprotect.hoyage.animalprotect;

import android.graphics.drawable.Drawable;

public class AnimalItem {

//    private Drawable iconDrawable ;
    private String imgURL;
    private String titleStr;
    private String kindCdStr;
    private String color;
    private String happenDt;
    private String address;

//    public void setIcon(Drawable icon) {
//        iconDrawable = icon ;
//    }
    public void setImgURL(String url)  { imgURL = url ; }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setKindCdStr(String desc) {
        kindCdStr = desc ;
    }
    public void setColor(String color) { this.color = color ; }
    public void setHappenDt(String happenDt) { this.happenDt = happenDt ; }
    public void setAddress(String address) { this.address = address ; }


//    public Drawable getIcon() {
//        return this.iconDrawable ;
//    }
    public String getImgURL() { return this.imgURL ; }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getKindCdStr() {
        return this.kindCdStr ;
    }
    public String getColor() { return this.color; }
    public String getHappenDt() { return this.happenDt; }
    public String getAddress() { return this.address; }

}
