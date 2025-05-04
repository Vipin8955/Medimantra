package com.example.medimantra;

public class Exercise {
    private String name;
    private int imageResId;
    private String link;

    public Exercise(String name, int imageResId, String link) {
        this.name = name;
        this.imageResId = imageResId;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getLink() {
        return link;
    }
}


