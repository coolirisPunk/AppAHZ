package com.punkmkt.formula1.models;

/**
 * Created by germanpunk on 21/09/15.
 */
public class GalleryItem {
    private Integer id;
    private String thumbnail;
    private String image;
    private String name;
    public GalleryItem(){

    }
    public GalleryItem(int id, String thumbnail, String image, String name){
        this.id = id;
        this.thumbnail = thumbnail;
        this.image = image;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
