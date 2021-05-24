package com.example.myapp;
import java.util.ArrayList;

public class Volume {
    private int volNum;
    private String volId;
    private ArrayList<Chapter> Chapters = new ArrayList<Chapter>();
    public String getVolId() {
        return volId;
    }

    public void setVolId(String volId) {
        this.volId = volId;
    }


    public Volume(int volNum, String volId ){
        setVolNum(volNum);
        setVolId(volId);
    }

    public int getVolNum() {
        return volNum;
    }

    public ArrayList<Chapter> getChapters() {
        return Chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        Chapters = chapters;
    }

    public void setVolNum(int volNum) {
        this.volNum = volNum;
    }
}
