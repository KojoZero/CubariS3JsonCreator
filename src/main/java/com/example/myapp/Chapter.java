package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private String chapterName;
    private String chapterID;
    private int chapterNum;
    private ArrayList<String> imageStringArray;

    public Chapter(int chapterNum, String chapterName, String chapterID, ArrayList<String>imageStringArray) {
        this.chapterNum = chapterNum;
        this.chapterName = chapterName;
        this.chapterID = chapterID;
        this.imageStringArray = imageStringArray;
    }

    public int getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(int chapterNum) {
        this.chapterNum = chapterNum;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }



    public ArrayList<String> getImageStringArray() {
        return imageStringArray;
    }

    public void setImageStringArray(ArrayList<String> imageStringArray) {
        this.imageStringArray = imageStringArray;
    }

}
