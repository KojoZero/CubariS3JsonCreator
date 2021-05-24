package com.example.myapp.folders;

public class Image {
    private String seriesName;
    private String volumeName;
    private String chapterName;
    private String imageName;
    private String imageExtension;

    public Image(String seriesName, String volumeName, String chapterName, String imageName) {
        this.seriesName = seriesName;
        this.volumeName = volumeName;
        this.chapterName = chapterName;
        this.imageName = imageName;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
