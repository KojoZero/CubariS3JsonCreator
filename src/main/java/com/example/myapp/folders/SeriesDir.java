package com.example.myapp.folders;

import java.util.ArrayList;

public class SeriesDir {
    private String seriesName;
    private int seriesIndex;
    ArrayList<VolumeDir> volumeDirs = new ArrayList<VolumeDir>();

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getSeriesIndex() {
        return seriesIndex;
    }

    public void setSeriesIndex(int seriesIndex) {
        this.seriesIndex = seriesIndex;
    }

    public ArrayList<VolumeDir> getVolumeDirs() {
        return volumeDirs;
    }
    public void addToVolumeDirs(VolumeDir addToVolumeDirs) {
        this.volumeDirs.add(addToVolumeDirs);
    }
    public void setVolumeDirs(ArrayList<VolumeDir> volumeDirs) {
        this.volumeDirs = volumeDirs;
    }

    public SeriesDir(String seriesName, int seriesIndex) {
        this.seriesName = seriesName;
        this.seriesIndex = seriesIndex;
    }
}
