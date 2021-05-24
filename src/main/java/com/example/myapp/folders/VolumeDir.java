package com.example.myapp.folders;

import java.util.ArrayList;

public class VolumeDir {
    private String volumeName;
    private String volumeSeries;

    public String getVolumeSeries() {
        return volumeSeries;
    }

    public void setVolumeSeries(String volumeSeries) {
        this.volumeSeries = volumeSeries;
    }

    public VolumeDir(String volumeName, String volumeSeries, int volumeIndex) {
        this.volumeName = volumeName;
        this.volumeSeries = volumeSeries;
        this.volumeIndex = volumeIndex;
    }

    private int volumeIndex;
    ArrayList<ChapterDir> chapterDirs = new ArrayList<ChapterDir>();

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public int getVolumeIndex() {
        return volumeIndex;
    }

    public void setVolumeIndex(int volumeIndex) {
        this.volumeIndex = volumeIndex;
    }

    public ArrayList<ChapterDir> getChapterDirs() {
        return chapterDirs;
    }

    public void setChapterDirs(ArrayList<ChapterDir> chapterDirs) {
        this.chapterDirs = chapterDirs;
    }
    public void addToChapterDirs(ChapterDir addToChapterDirs) {
        this.chapterDirs.add(addToChapterDirs);
    }
}
