package com.example.testdrawerlayout;

import org.parceler.Parcel;

@Parcel
public class DataModel{
    private String id;
    private String nombre;
    private String descripcion;
    private String url_imagen;
    private String director;
    public DataModel() {

    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String email) {
        this.descripcion = email;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String gender) {
        this.url_imagen = gender;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String status) {
        this.director = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }



}
