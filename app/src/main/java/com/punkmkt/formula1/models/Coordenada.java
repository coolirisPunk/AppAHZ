package com.punkmkt.formula1.models;

/**
 * Created by germanpunk on 09/09/15.
 */

public class Coordenada {
    private Double Latitud;
    private Double Longitud;
    private String Titulo;
    public Coordenada(Double latitud, Double longitud, String titulo){
        this.Latitud = latitud;
        this.Longitud = longitud;
        this.Titulo = titulo;
    }
    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }


    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String ciudad) {
        Titulo = ciudad;
    }
}