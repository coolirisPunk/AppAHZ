package com.punkmkt.formula1.models;

/**
 * Created by germanpunk on 14/09/15.
 */
public class Premio {

    private Integer id;
    private String name;
    private String bandera;
    private String imagen_categoria;


    public String getName() {
        return name;
    }

    public String getImagen() {
        return bandera;
    }

    public void setImage(String image) {
        this.bandera = image;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagenCategoria() {
        return imagen_categoria;
    }

    public void setImageCategoria(String image) {
        this.imagen_categoria = image;
    }
}
