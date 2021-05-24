package com.example.myapp.folders;


import java.util.ArrayList;

public class ChapterDir {
    private String chapterName;
    private String chapterSeries;
    private String chapterVolume;
    private int chapterIndex;
    ArrayList<Image> imageDirs = new ArrayList<Image>();

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterSeries() {
        return chapterSeries;
    }

    public void setChapterSeries(String chapterSeries) {
        this.chapterSeries = chapterSeries;
    }

    public String getChapterVolume() {
        return chapterVolume;
    }

    public void setChapterVolume(String chapterVolume) {
        this.chapterVolume = chapterVolume;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public ArrayList<Image> getImageDirs() {
        return imageDirs;
    }

    public void setImageDirs(ArrayList<Image> imageDirs) {
        this.imageDirs = imageDirs;
    }
    public void addToImageDirs(Image addToImageDir){
        this.imageDirs.add(addToImageDir);
    }

    public ChapterDir(String chapterName, String chapterSeries, String chapterVolume, int chapterIndex) {
        this.chapterName = chapterName;
        this.chapterSeries = chapterSeries;
        this.chapterVolume = chapterVolume;
        this.chapterIndex = chapterIndex;
    }
}
